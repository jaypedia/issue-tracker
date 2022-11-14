package team20.issuetracker.controller;

import static org.hamcrest.CoreMatchers.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;

import team20.issuetracker.login.oauth.Role;
import team20.issuetracker.login.oauth.dto.request.RequestMaintainDto;
import team20.issuetracker.login.oauth.dto.request.RequestRefreshDto;
import team20.issuetracker.login.oauth.dto.response.ResponseLoginDto;
import team20.issuetracker.service.OauthService;

@DisplayName("컨트롤러 - OAuth")
@WebMvcTest(OauthController.class)
@ContextConfiguration(classes = OauthController.class)
class OauthControllerTest {

    private final MockMvc mvc;

    private ObjectMapper mapper = new ObjectMapper();

    @MockBean
    private OauthService oauthService;

    public OauthControllerTest(@Autowired MockMvc mvc) {
        this.mvc = mvc;
    }

    @DisplayName("[Controller][GET] 로그인 테스트 - 정상 호출")
    @Test
    void 로그인_성공() throws Exception {
        String code = "code";
        ResponseLoginDto responseLoginDto = createResponseLoginDto();
        //given
        given(oauthService.signup(code)).willReturn(responseLoginDto);

        mvc.perform(get("/api/login/oauth/github")
                        .queryParam("code", code)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id", is(responseLoginDto.getId().intValue())))
                .andDo(print());

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
        mvc.perform(post("/api/login/oauth/github/refresh")
                        .content(mapper.writeValueAsString(requestRefreshDto))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON));

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
        mvc.perform(post("/api/login/oauth/github/maintain")
                        .content(mapper.writeValueAsString(requestMaintainDto))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(jsonPath("$.id", is(responseLoginDto.getId().intValue())))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON));

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
