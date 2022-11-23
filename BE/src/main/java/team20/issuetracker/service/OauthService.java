package team20.issuetracker.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import team20.issuetracker.domain.member.Member;
import team20.issuetracker.domain.member.MemberRepository;
import team20.issuetracker.exception.CheckEntityException;
import team20.issuetracker.exception.MyJwtException;
import team20.issuetracker.login.jwt.JwtTokenProvider;
import team20.issuetracker.login.oauth.OauthProvider;
import team20.issuetracker.login.oauth.dto.request.RequestRefreshDto;
import team20.issuetracker.login.oauth.dto.request.ResponseUserDto;
import team20.issuetracker.login.oauth.dto.response.ResponseLoginDto;
import team20.issuetracker.login.oauth.dto.response.ResponseOauthTokenDto;
import team20.issuetracker.login.oauth.repository.InMemoryProviderRepository;

@Service
public class OauthService {

    private final InMemoryProviderRepository inMemoryProviderRepository;
    private final MemberRepository memberRepository;
    private final JwtTokenProvider jwtTokenProvider;
    private final RedisTemplate<String, String> redisTemplate;
    private WebClient webClient;

    private OauthService(InMemoryProviderRepository inMemoryProviderRepository,
                         MemberRepository memberRepository,
                         JwtTokenProvider jwtTokenProvider,
                         RedisTemplate<String, String> redisTemplate,
                         String baseUrl) {
        this.inMemoryProviderRepository = inMemoryProviderRepository;
        this.memberRepository = memberRepository;
        this.jwtTokenProvider = jwtTokenProvider;
        this.redisTemplate = redisTemplate;
        this.webClient = WebClient.create(baseUrl);
    }

    @Autowired
    public OauthService(InMemoryProviderRepository inMemoryProviderRepository,
                        MemberRepository memberRepository,
                        JwtTokenProvider jwtTokenProvider,
                        RedisTemplate<String, String> redisTemplate) {
        this.inMemoryProviderRepository = inMemoryProviderRepository;
        this.memberRepository = memberRepository;
        this.jwtTokenProvider = jwtTokenProvider;
        this.redisTemplate = redisTemplate;
        this.webClient = WebClient.create();
    }

    public static OauthService byTest(InMemoryProviderRepository inMemoryProviderRepository,
                                      MemberRepository memberRepository,
                                      JwtTokenProvider jwtTokenProvider,
                                      RedisTemplate<String, String> redisTemplate,
                                      String baseUrl) {
        return new OauthService(inMemoryProviderRepository, memberRepository, jwtTokenProvider, redisTemplate, baseUrl);
    }

    public ResponseLoginDto signup(String code) {
        OauthProvider provider = inMemoryProviderRepository.getProvider();
        ResponseOauthTokenDto tokenResponse = requestToken(code, provider);
        ResponseUserDto responseUserDto = getUserProfile(provider, tokenResponse);
        Member member = saveOrUpdate(responseUserDto);
        String accessToken = jwtTokenProvider.getAccessToken(member);
        String refreshToken = jwtTokenProvider.getRefreshToken(member);

        redisTemplate
                .opsForValue()
                .set(String.valueOf(member.getOauthId()), refreshToken, jwtTokenProvider.getRefreshTokenValidityInMilliseconds(), TimeUnit.MILLISECONDS);
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

    private Member saveOrUpdate(ResponseUserDto responseUserDto) {
        Member findMember = memberRepository.findByOauthId(responseUserDto.getOauthId())
                .map(member -> member.update(responseUserDto.getName(), responseUserDto.getEmail(), responseUserDto.getProfileImageUrl()))
                .orElseGet(responseUserDto::toMember);

        return memberRepository.save(findMember);
    }

    private ResponseUserDto getUserProfile(OauthProvider provider, ResponseOauthTokenDto tokenResponse) {

        return webClient.get()
                .uri(provider.getUserInfoUrl())
                .header("Authorization", "token " + tokenResponse.getAccessToken())
                .retrieve()
                .bodyToMono(ResponseUserDto.class)
                .block();
    }

    private ResponseOauthTokenDto requestToken(String code, OauthProvider provider) {

        return webClient.post()
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

    public String checkRefreshToken(RequestRefreshDto requestRefreshDto) {
        String id = requestRefreshDto.getOauthId();
        String fromClientRefreshToken = requestRefreshDto.getRefreshToken();
        String storedToken = redisTemplate.opsForValue().get(id);
        if (fromClientRefreshToken.equals(storedToken)) {
            return jwtTokenProvider.getAccessToken(fromClientRefreshToken);
        }
        throw new MyJwtException("유효하지 않은 refreshToken 입니다.", HttpStatus.SEE_OTHER);
    }

    public ResponseLoginDto getMaintainUserInfo(String refreshToken) {
        String refreshTokenOauthId = jwtTokenProvider.decodingRefreshToken(refreshToken);
        String storedToken = redisTemplate.opsForValue().get(refreshTokenOauthId);

        if (refreshToken.equals(storedToken)) {
            Member findMember = memberRepository.findByOauthId(refreshTokenOauthId)
                    .orElseThrow(() -> new CheckEntityException("해당 멤버는 존재하지 않습니다.", HttpStatus.BAD_REQUEST));
            String accessToken = jwtTokenProvider.getAccessToken(refreshToken);

            return ResponseLoginDto.builder()
                    .id(findMember.getId())
                    .email(findMember.getEmail())
                    .name(findMember.getName())
                    .profileImageUrl(findMember.getProfileImageUrl())
                    .role(findMember.getRole())
                    .tokenType("Bearer")
                    .accessToken(accessToken)
                    .refreshToken(refreshToken)
                    .build();
        }

        throw new MyJwtException("유효하지 않은 refreshToken 입니다.", HttpStatus.SEE_OTHER);
    }
}
