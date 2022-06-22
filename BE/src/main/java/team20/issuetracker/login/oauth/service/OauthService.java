package team20.issuetracker.login.oauth.service;

import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.HashMap;
import java.util.Map;

import lombok.RequiredArgsConstructor;
import team20.issuetracker.login.domain.member.Member;
import team20.issuetracker.login.domain.member.MemberRepository;
import team20.issuetracker.login.jwt.JwtTokenProvider;
import team20.issuetracker.login.oauth.OauthProvider;
import team20.issuetracker.login.oauth.dto.LoginResponse;
import team20.issuetracker.login.oauth.dto.OauthTokenResponse;
import team20.issuetracker.login.oauth.dto.UserProfile;
import team20.issuetracker.login.oauth.repository.InMemoryProviderRepository;

@RequiredArgsConstructor
@Service
public class OauthService {

    private final InMemoryProviderRepository inMemoryProviderRepository;
    private final MemberRepository memberRepository;
    private final JwtTokenProvider jwtTokenProvider;

    public LoginResponse signup(String code) {

        // Github Provider 를 찾아온다.
        OauthProvider provider = inMemoryProviderRepository.getProvider();

        // 찾아낸 provider 와 Authorization Code 를 통해 GitHub 에서 제공하는 AccessToken 을 발급받는다.
        OauthTokenResponse tokenResponse = getToken(code, provider);

        // 받아온 GitHub Access Token, OauthProvider 으로 해당 유저의 정보를 가져온다.
        UserProfile userProfile = getUserProfile(provider, tokenResponse);

        // 유저 프로필을 인자로 넘겨 Member 를 저장한다.
        // DB 에 존재하지 않는 유저라면 새로 Save, 있었던 유저라면 정보를 Update 해야한다.
        Member member = saveOrUpdate(userProfile);

        // 인증 방법이 별도로 필요하기 떄문에 JWT 를 사용해 인증에 필요한 Access, Refresh Token 을 만든다.
        // JWT Access Token 을 만들 떄 Payload 값으로 셋팅하기 위해서 따로 member.getId 값을 인자로 넘겨준다.
        String accessToken = jwtTokenProvider.createAccessToken(String.valueOf(member.getId()));
        String refreshToken = jwtTokenProvider.createRefreshToken();

        // FE 쪽으로 유저 정보, JWT Token (Access, Refresh) 를 응답한다.
        return LoginResponse.builder()
                .id(member.getId())
                .name(member.getName())
                .email(member.getEmail())
                .avatarUrl(member.getAvatarUrl())
                .role(member.getRole())
                .tokenType("Bearer")
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .build();
    }

    // findByOauthId() 메서드는 Optional<Member> 타입이기 때문에 스트림을 사용해서 없는 멤버인지, 있던 멤버인지 파악한다.
    // stream map 을 통해 있는 Member 라면 Member Entity 자체에서 새로 저장하고, 그게 아니라면 UserProfile DTO 에서 Member Entity 로
    // 변환해서 findMember 변수에 담아준다.
    // 마지막으로 findMember 를 인자로 담아 Member Repository 로 보내 DB 에 저장한다.
    private Member saveOrUpdate(UserProfile userProfile) {
        Member findMember = memberRepository.findByOauthId(userProfile.getOauthId())
                .map(member -> member.update(userProfile.getEmail(), userProfile.getName(), userProfile.getAvatarUrl()))
                .orElseGet(userProfile::toMember);

        return memberRepository.save(findMember);
    }

    private UserProfile getUserProfile(OauthProvider provider, OauthTokenResponse tokenResponse) {

        return WebClient.create().get()
                .uri(provider.getUserInfoUrl())
                .header("Authorization", "token " + tokenResponse.getAccessToken())
                .retrieve()
                .bodyToMono(UserProfile.class)
                .block();
    }

    // 발급받을 떄 WebClient 를 사용해 GitHub 에서 제공하는 URL 로 Header, Body 에 값을 담아 POST 요청을 보낸다.
    private OauthTokenResponse getToken(String code, OauthProvider provider) {

        return WebClient.create().post()
                .uri(provider.getTokenUrl())
                .accept(MediaType.APPLICATION_JSON)
                .bodyValue(tokenRequest(code, provider))
                .retrieve()
                .bodyToMono(OauthTokenResponse.class)
                .block();
    }

    // GitHub Access Token 을 발급받을 때 Body 에 필요한 값들을 따로 메서드를 만들어 담아준다.
    private Map<String, String> tokenRequest(String code, OauthProvider provider) {

        Map<String, String> formData = new HashMap<>();
        formData.put("code", code);
        formData.put("client_id", provider.getClientId());
        formData.put("client_secret", provider.getClientSecret());

        return formData;
    }
}
