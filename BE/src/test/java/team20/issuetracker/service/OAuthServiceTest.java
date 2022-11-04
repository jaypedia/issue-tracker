package team20.issuetracker.service;

import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;

import com.fasterxml.jackson.databind.ObjectMapper;

import org.assertj.core.api.SoftAssertions;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.http.MediaType;

import java.io.IOException;
import java.util.Optional;

import okhttp3.mockwebserver.MockResponse;
import okhttp3.mockwebserver.MockWebServer;
import team20.issuetracker.domain.member.Member;
import team20.issuetracker.domain.member.MemberRepository;
import team20.issuetracker.login.jwt.JwtTokenProvider;
import team20.issuetracker.login.oauth.OauthProvider;
import team20.issuetracker.login.oauth.dto.request.ResponseUserDto;
import team20.issuetracker.login.oauth.dto.response.ResponseLoginDto;
import team20.issuetracker.login.oauth.dto.response.ResponseOauthTokenDto;
import team20.issuetracker.login.oauth.repository.InMemoryProviderRepository;

@DisplayName("비즈니스 로직 - OAuth")
@ExtendWith(MockitoExtension.class)
class OAuthServiceTest {

    private static MockWebServer mockBackEnd;
    @Mock
    ValueOperations<String, String> valueOperations;
    private ObjectMapper mapper = new ObjectMapper();
    @InjectMocks
    private OauthService sut;
    @Mock
    private InMemoryProviderRepository inMemoryProviderRepository;
    @Mock
    private MemberRepository memberRepository;
    @Mock
    private JwtTokenProvider jwtTokenProvider;
    @Mock
    private RedisTemplate<String, String> redisTemplate;

    @BeforeAll
    static void setUp() throws IOException {
        mockBackEnd = new MockWebServer();
        mockBackEnd.start();
    }

    @AfterAll
    static void tearDown() throws IOException {
        mockBackEnd.shutdown();
    }

    void init() {
        String baseUrl = String.format("http://localhost:%s",
                mockBackEnd.getPort());
        sut = OauthService.byTest(inMemoryProviderRepository, memberRepository, jwtTokenProvider, redisTemplate, baseUrl);
    }

    @DisplayName("유저가 OAuth 회원가입 요청을 하면, 유저 정보를 반환해 준다.")
    @Test
    void 유저_회원가입_성공() throws Exception {
        init();
        String code = "code";
        OauthProvider oauthProvider = createOAuthProvider();
        ResponseOauthTokenDto responseOauthTokenDto = createResponseOAuthTokenDto();
        ResponseUserDto responseUserDto = createRequestUserDto();
        String responseOAuthTokenDtoToString = mapper.writeValueAsString(responseOauthTokenDto);
        String responseUserDtoToString = mapper.writeValueAsString(responseUserDto);
        Member member = responseUserDto.toMemberByTest();

        mockBackEnd.enqueue(
                new MockResponse()
                        .setBody(responseOAuthTokenDtoToString)
                        .addHeader("Content-Type", MediaType.APPLICATION_JSON)
        );
        mockBackEnd.enqueue(
                new MockResponse()
                        .setBody(responseUserDtoToString)
                        .addHeader("Content-Type", MediaType.APPLICATION_JSON)
        );

        given(inMemoryProviderRepository.getProvider()).willReturn(oauthProvider);
        given(memberRepository.findByOauthId(responseUserDto.getOauthId())).willReturn(Optional.of(member));
        given(jwtTokenProvider.getAccessToken(member)).willReturn("acToken");
        given(jwtTokenProvider.getRefreshToken(member)).willReturn("rfToken");
        given(jwtTokenProvider.getRefreshTokenValidityInMilliseconds()).willReturn(1L);
        given(redisTemplate.opsForValue()).willReturn(valueOperations);
        given(memberRepository.save(member)).willReturn(member);

        //when
        ResponseLoginDto loginDto = sut.signup(code);
        SoftAssertions.assertSoftly(softly -> {
            softly.assertThat(loginDto.getAccessToken())
                    .as("외부 API 요청을 통해 생성된 accessToken 과 로그인 결과 반환되는 accessToken 은 동일해야 한다.")
                    .isEqualTo(responseOauthTokenDto.getAccessToken());
            softly.assertThat(loginDto.getName())
                    .as("외부 API 요청을 통해 받아온 username 과 반환되는 username 은 동일해야 한다.")
                    .isEqualTo(responseUserDto.getName());
        });

        //then
        then(inMemoryProviderRepository).should().getProvider();
        then(memberRepository).should().findByOauthId(responseUserDto.getOauthId());
        then(jwtTokenProvider).should().getAccessToken(member);
        then(jwtTokenProvider).should().getRefreshToken(member);
        then(jwtTokenProvider).should().getRefreshTokenValidityInMilliseconds();
        then(redisTemplate).should().opsForValue();
        then(memberRepository).should().save(member);
    }

    private ResponseUserDto createRequestUserDto() {
        return ResponseUserDto.builder()
                .oauthId("oauthId")
                .email("email")
                .name("name")
                .profileImageUrl("profileImageUrl")
                .build();
    }

    private ResponseOauthTokenDto createResponseOAuthTokenDto() {
        return ResponseOauthTokenDto.builder()
                .accessToken("acToken")
                .scope("scope")
                .tokenType("type")
                .build();
    }

    private OauthProvider createOAuthProvider() {
        return OauthProvider.of(
                "clientId",
                "clientSecret",
                "redirectUrl",
                "tokenUrl",
                "userInfoUrl",
                "loginUrl"
        );
    }
}
