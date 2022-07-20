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
import team20.issuetracker.login.oauth.dto.response.ResponseLoginDto;
import team20.issuetracker.login.oauth.dto.response.ResponseOauthTokenDto;
import team20.issuetracker.login.oauth.dto.request.RequestRefreshDto;
import team20.issuetracker.login.oauth.dto.request.RequestUserDto;
import team20.issuetracker.login.oauth.repository.InMemoryProviderRepository;

@RequiredArgsConstructor
@Service
public class OauthService {

    private final InMemoryProviderRepository inMemoryProviderRepository;
    private final MemberRepository memberRepository;
    private final JwtTokenProvider jwtTokenProvider;
    private final RedisTemplate<String, String> redisTemplate;

    public ResponseLoginDto signup(String code) {
        OauthProvider provider = inMemoryProviderRepository.getProvider();
        ResponseOauthTokenDto tokenResponse = requestToken(code, provider);
        RequestUserDto requestUserDto = getUserProfile(provider, tokenResponse);
        Member member = saveOrUpdate(requestUserDto);

        String accessToken = jwtTokenProvider.createAccessToken(String.valueOf(member.getId()));
        String refreshToken = jwtTokenProvider.createRefreshToken();
        redisTemplate.opsForValue().set(String.valueOf(member.getId()), refreshToken, jwtTokenProvider.getRefreshTokenValidityInMilliseconds(), TimeUnit.MILLISECONDS);
        // FE 쪽으로 유저 정보, JWT Token (Access, Refresh) 를 응답한다.
        return ResponseLoginDto.builder()
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

    private Member saveOrUpdate(RequestUserDto requestUserDto) {
        Member findMember = memberRepository.findByOauthId(requestUserDto.getOauthId())
                .map(member -> member.update(requestUserDto.getEmail(), requestUserDto.getName(), requestUserDto.getProfileImageUrl()))
                .orElseGet(requestUserDto::toMember);

        return memberRepository.save(findMember);
    }

    private RequestUserDto getUserProfile(OauthProvider provider, ResponseOauthTokenDto tokenResponse) {

        return WebClient.create().get()
                .uri(provider.getUserInfoUrl())
                .header("Authorization", "token " + tokenResponse.getAccessToken())
                .retrieve()
                .bodyToMono(RequestUserDto.class)
                .block();
    }

    private ResponseOauthTokenDto requestToken(String code, OauthProvider provider) {

        return WebClient.create().post()
                .uri(provider.getTokenUrl())
                .accept(MediaType.APPLICATION_JSON)
                .bodyValue(tokenRequest(code, provider))
                .retrieve()
                .bodyToMono(ResponseOauthTokenDto.class)
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
