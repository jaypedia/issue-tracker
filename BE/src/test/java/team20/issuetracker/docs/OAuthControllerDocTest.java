package team20.issuetracker.docs;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.documentationConfiguration;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.get;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.post;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.requestFields;
import static org.springframework.restdocs.payload.PayloadDocumentation.responseFields;
import static org.springframework.restdocs.request.RequestDocumentation.parameterWithName;
import static org.springframework.restdocs.request.RequestDocumentation.requestParameters;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.restdocs.RestDocumentationContextProvider;
import org.springframework.restdocs.RestDocumentationExtension;
import org.springframework.restdocs.mockmvc.RestDocumentationResultHandler;
import org.springframework.restdocs.payload.JsonFieldType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.filter.CharacterEncodingFilter;

import team20.issuetracker.controller.OauthController;
import team20.issuetracker.docs.config.RestDocsConfig;
import team20.issuetracker.login.oauth.Role;
import team20.issuetracker.login.oauth.dto.request.RequestMaintainDto;
import team20.issuetracker.login.oauth.dto.request.RequestRefreshDto;
import team20.issuetracker.login.oauth.dto.response.ResponseLoginDto;
import team20.issuetracker.service.OauthService;

@DisplayName("Spring Docs - OAuth")
@ExtendWith(RestDocumentationExtension.class)
@WebMvcTest(OauthController.class)
@Import(RestDocsConfig.class)
@ContextConfiguration(classes = {OauthController.class})
class OAuthControllerDocTest {

    @Autowired
    private RestDocumentationResultHandler restDoc;
    @MockBean
    private OauthService oauthService;

    private MockMvc mockMvc;
    private ObjectMapper mapper;

    public OAuthControllerDocTest() {
        this.mapper = new ObjectMapper();
        mapper.registerModule(new JavaTimeModule());
    }

    @BeforeEach
    public void setUp(WebApplicationContext webApplicationContext, RestDocumentationContextProvider restDocumentationContextProvider) {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext)
                .apply(documentationConfiguration(restDocumentationContextProvider)
                        .uris()
                        .withScheme("https")
                        .withHost("api.rarus-be.com")
                        .withPort(443))
                .alwaysDo(print())
                .alwaysDo(restDoc)
                .addFilters(new CharacterEncodingFilter("UTF-8", true))
                .build();
    }

    @DisplayName("[Controller][GET] 로그인 테스트 - 정상 호출")
    @Test
    void 로그인_성공() throws Exception {
        String code = "code";
        ResponseLoginDto responseLoginDto = createResponseLoginDto();
        //given
        given(oauthService.signup(code)).willReturn(responseLoginDto);

        mockMvc.perform(get("/api/login/oauth/github")
                        .queryParam("code", code)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andDo(
                        restDoc.document(
                                requestParameters(
                                        parameterWithName("code").description("OAuth 쪽에서 제공해주는 코드")
                                ),
                                responseFields(
                                        fieldWithPath("id").type(JsonFieldType.NUMBER).description("로그인 유저 아이디"),
                                        fieldWithPath("name").type(JsonFieldType.STRING).description("로그인 유저 이름"),
                                        fieldWithPath("email").type(JsonFieldType.STRING).description("로그인 유저 이메일"),
                                        fieldWithPath("profileImageUrl").type(JsonFieldType.STRING).description("로그인 유저 프로필 Url"),
                                        fieldWithPath("role").type(JsonFieldType.STRING).description("로그인 유저 등급"),
                                        fieldWithPath("tokenType").type(JsonFieldType.STRING).description("JWT 타입"),
                                        fieldWithPath("accessToken").type(JsonFieldType.STRING).description("인가 검증을 할때 사용하는 액세스 토큰"),
                                        fieldWithPath("refreshToken").type(JsonFieldType.STRING).description("액세스 토큰 만료 시 재발행을 위한 리프레시 토큰")
                                )
                        )
                );

        //when & then
        then(oauthService).should().signup(code);
    }

    @DisplayName("[Controller][POST] 토큰 재발급 테스트 - 정상 호출")
    @Test
    void 토큰_재발급_성공() throws Exception {
        //given
        RequestRefreshDto requestRefreshDto = createRequestRefreshDto();
        String newAccessToken = "acToken";
        given(oauthService.checkRefreshToken(any(RequestRefreshDto.class))).willReturn(newAccessToken);

        //when & then
        mockMvc.perform(post("/api/login/oauth/github/refresh")
                        .content(mapper.writeValueAsString(requestRefreshDto))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andDo(
                        restDoc.document(
                                requestFields(
                                        fieldWithPath("id").type(JsonFieldType.NUMBER).description("토큰을 재발급 받으려는 유저의 아이디"),
                                        fieldWithPath("refreshToken").type(JsonFieldType.STRING).description("액세스 토큰 만료 시 재발행을 위한 리프레시 토큰")
                                )
                        )
                );

        then(oauthService).should().checkRefreshToken(any(RequestRefreshDto.class));
    }

    @DisplayName("[Controller][POST] Maintain 테스트 - 정상 호출")
    @Test
    void Maintain_동작_성공() throws Exception {
        //given
        String refreshToken = "rfToken";
        RequestMaintainDto requestMaintainDto = createMaintainDto();
        ResponseLoginDto responseLoginDto = createResponseLoginDto();
        given(oauthService.getMaintainUserInfo(refreshToken)).willReturn(responseLoginDto);

        //when & then
        mockMvc.perform(post("/api/login/oauth/github/maintain")
                        .content(mapper.writeValueAsString(requestMaintainDto))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andDo(
                        restDoc.document(
                                requestFields(
                                        fieldWithPath("refreshToken").type(JsonFieldType.STRING).description("Maintain 인가를 검증하기 위한 리프레시 토큰")
                                ),
                                responseFields(
                                        fieldWithPath("id").type(JsonFieldType.NUMBER).description("로그인 유저 아이디"),
                                        fieldWithPath("name").type(JsonFieldType.STRING).description("로그인 유저 이름"),
                                        fieldWithPath("email").type(JsonFieldType.STRING).description("로그인 유저 이메일"),
                                        fieldWithPath("profileImageUrl").type(JsonFieldType.STRING).description("로그인 유저 프로필 Url"),
                                        fieldWithPath("role").type(JsonFieldType.STRING).description("로그인 유저 등급"),
                                        fieldWithPath("tokenType").type(JsonFieldType.STRING).description("JWT 타입"),
                                        fieldWithPath("accessToken").type(JsonFieldType.STRING).description("인가 검증을 할때 사용하는 액세스 토큰"),
                                        fieldWithPath("refreshToken").type(JsonFieldType.STRING).description("액세스 토큰 만료 시 재발행을 위한 리프레시 토큰")
                                )
                        )
                );

        then(oauthService).should().getMaintainUserInfo(refreshToken);
    }

    private RequestMaintainDto createMaintainDto() {
        return RequestMaintainDto.builder()
                .refreshToken("rfToken")
                .build();
    }

    private RequestRefreshDto createRequestRefreshDto() {
        return RequestRefreshDto.builder()
                .id(1L)
                .refreshToken("rfToken")
                .build();
    }

    private ResponseLoginDto createResponseLoginDto() {
        return ResponseLoginDto.builder()
                .id(1L)
                .name("name")
                .email("email@email.com")
                .profileImageUrl("ImageUrl")
                .role(Role.GUEST)
                .tokenType("type")
                .accessToken("acToken")
                .refreshToken("rfToken")
                .build();
    }
}
