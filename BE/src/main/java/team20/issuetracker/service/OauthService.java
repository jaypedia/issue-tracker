package team20.issuetracker.service;

import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

import lombok.RequiredArgsConstructor;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.view.RedirectView;

import team20.issuetracker.domain.member.Member;
import team20.issuetracker.domain.member.MemberRepository;
import team20.issuetracker.exception.MyJwtException;
import team20.issuetracker.login.jwt.JwtTokenProvider;
import team20.issuetracker.login.oauth.OauthProvider;
import team20.issuetracker.login.oauth.dto.LoginResponse;
import team20.issuetracker.login.oauth.dto.OauthTokenResponse;
import team20.issuetracker.login.oauth.dto.RequestRefreshDto;
import team20.issuetracker.login.oauth.dto.UserProfile;
import team20.issuetracker.login.oauth.repository.InMemoryProviderRepository;

@RequiredArgsConstructor
@Service
public class OauthService {

    private final InMemoryProviderRepository inMemoryProviderRepository;
    private final MemberRepository memberRepository;
    private final JwtTokenProvider jwtTokenProvider;
    private final RedisTemplate<String, String> redisTemplate;

    public LoginResponse signup(String code) {
        OauthProvider provider = inMemoryProviderRepository.getProvider();
        OauthTokenResponse tokenResponse = requestToken(code, provider);
        UserProfile userProfile = getUserProfile(provider, tokenResponse);
        Member member = saveOrUpdate(userProfile);

        String accessToken = jwtTokenProvider.createAccessToken(String.valueOf(member.getId()));
        String refreshToken = jwtTokenProvider.createRefreshToken();
        redisTemplate.opsForValue().set(String.valueOf(member.getId()), refreshToken, jwtTokenProvider.getRefreshTokenValidityInMilliseconds(), TimeUnit.MILLISECONDS);
        // FE 쪽으로 유저 정보, JWT Token (Access, Refresh) 를 응답한다.
        return LoginResponse.builder()
                .id(member.getId())
                .name(member.getName())
                .email(member.getEmail())
                .profileImageUrl(member.getProfileImageUrl())
                .role(member.getRole())
                .tokenType("Bearer")
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .build();
    }

    private Member saveOrUpdate(UserProfile userProfile) {
        Member findMember = memberRepository.findByOauthId(userProfile.getOauthId())
                .map(member -> member.update(userProfile.getEmail(), userProfile.getName(), userProfile.getProfileImageUrl()))
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

    private OauthTokenResponse requestToken(String code, OauthProvider provider) {

        return WebClient.create().post()
                .uri(provider.getTokenUrl())
                .accept(MediaType.APPLICATION_JSON)
                .bodyValue(tokenRequest(code, provider))
                .retrieve()
                .bodyToMono(OauthTokenResponse.class)
                .block();
    }

    private Map<String, String> tokenRequest(String code, OauthProvider provider) {

        Map<String, String> formData = new HashMap<>();
        formData.put("code", code);
        formData.put("client_id", provider.getClientId());
        formData.put("client_secret", provider.getClientSecret());

        return formData;
    }

    public RedirectView getToken(RedirectAttributes redirectAttributes) {

        OauthProvider provider = inMemoryProviderRepository.getProvider();

        redirectAttributes.addAttribute("client_id", provider.getClientId());
        redirectAttributes.addAttribute("redirect_url", provider.getRedirectUrl());
        redirectAttributes.addAttribute("state", UUID.randomUUID().toString());

        return new RedirectView(provider.getLoginUri());
    }

    public String checkRefreshToken(RequestRefreshDto requestRefreshDto) {
        String memberId = requestRefreshDto.getId();
        String fromClientRefreshToken = requestRefreshDto.getRefreshToken();
        String storedToken = redisTemplate.opsForValue().get(memberId);
        if (fromClientRefreshToken.equals(storedToken)) {
            return jwtTokenProvider.createAccessToken(memberId);
        }
        throw new MyJwtException("유효하지 않은 refreshToken 입니다.", HttpStatus.SEE_OTHER);
    }
}
