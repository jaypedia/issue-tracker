package team20.issuetracker.docs;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.mockito.BDDMockito.willDoNothing;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.documentationConfiguration;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.delete;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.get;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.post;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.requestFields;
import static org.springframework.restdocs.payload.PayloadDocumentation.responseFields;
import static org.springframework.restdocs.request.RequestDocumentation.parameterWithName;
import static org.springframework.restdocs.request.RequestDocumentation.pathParameters;
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
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.filter.CharacterEncodingFilter;

import java.util.List;

import team20.issuetracker.controller.LabelController;
import team20.issuetracker.docs.config.RestDocsConfig;
import team20.issuetracker.domain.label.Label;
import team20.issuetracker.service.LabelService;
import team20.issuetracker.service.dto.request.RequestLabelDto;
import team20.issuetracker.service.dto.response.ResponseLabelDto;
import team20.issuetracker.service.dto.response.ResponseLabelsDto;

@DisplayName("Spring Docs - 레이블")
@ExtendWith(RestDocumentationExtension.class)
@WebMvcTest(LabelController.class)
@Import(RestDocsConfig.class)
@ContextConfiguration(classes = {LabelController.class})
class LabelControllerDocTest {

    @MockBean
    private LabelService labelService;
    @Autowired
    private RestDocumentationResultHandler restDocs;

    private ObjectMapper mapper;
    private MockMvc mockMvc;

    public LabelControllerDocTest() {
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
                .alwaysDo(MockMvcResultHandlers.print())
                .alwaysDo(restDocs)
                .addFilter(new CharacterEncodingFilter("UTF-8", true))
                .build();
    }

    @DisplayName("[Controller][GET] 레이블 리스트 전체 조회 - 정상 호출")
    @Test
    void 레이블_전체_조회_성공() throws Exception {
        //given
        Label label = createLabel();
        ResponseLabelDto responseLabelDto = createResponseLabelDto(label);
        ResponseLabelsDto responseLabelsDto = createResponseLabelsDto(responseLabelDto);
        given(labelService.findAll()).willReturn(responseLabelsDto);

        //when & then
        mockMvc.perform(get("/api/labels"))
                .andExpect(status().isOk())
                .andDo(
                        restDocs.document(
                                responseFields(
                                        fieldWithPath("labelCount").type(JsonFieldType.NUMBER).description("레이블의 총 개수"),
                                        fieldWithPath("labels.[].id").type(JsonFieldType.NUMBER).description("레이블 아이디"),
                                        fieldWithPath("labels.[].title").type(JsonFieldType.STRING).description("레이블 제목"),
                                        fieldWithPath("labels.[].description").type(JsonFieldType.STRING).description("레이블 설명"),
                                        fieldWithPath("labels.[].backgroundColor").type(JsonFieldType.STRING).description("레이블의 배경색 (헥스 코드)"),
                                        fieldWithPath("labels.[].textColor").type(JsonFieldType.STRING).description("레이블의 텍스트 색상 (헥스 코드)")
                                )
                        )
                )
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON));
        then(labelService).should().findAll();
    }

    @DisplayName("[Controller][POST] 새로운 레이블 작성 - 정상 작성")
    @Test
    void 새로운_레이블_생성_성공() throws Exception {
        //given
        RequestLabelDto requestLabelDto = createRequestLabelDto();
        given(labelService.save(any(RequestLabelDto.class))).willReturn(1L);

        //when & then
        mockMvc.perform(post("/api/labels")
                        .content(mapper.writeValueAsString(requestLabelDto))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andDo(
                        restDocs.document(
                                requestFields(
                                        fieldWithPath("title").type(JsonFieldType.STRING).description("레이블 제목"),
                                        fieldWithPath("description").type(JsonFieldType.STRING).description("레이블 설명"),
                                        fieldWithPath("backgroundColor").type(JsonFieldType.STRING).description("레이블의 배경색 (헥스 코드)"),
                                        fieldWithPath("textColor").type(JsonFieldType.STRING).description("레이블의 텍스트 색상 (헥스 코드)")
                                )
                        )
                );
        then(labelService).should().save(any(RequestLabelDto.class));
    }

    @DisplayName("[Controller][POST] 레이블 업데이트 - 정상 작동")
    @Test
    void 레이블_수정_성공() throws Exception {
        //given
        Long labelId = 1L;
        RequestLabelDto requestLabelDto = createRequestLabelDto();
        given(labelService.update(eq(labelId), any(RequestLabelDto.class))).willReturn(labelId);

        //when & then
        mockMvc.perform(post("/api/labels/{labelId}", labelId)
                        .content(mapper.writeValueAsString(requestLabelDto))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andDo(
                        restDocs.document(
                                pathParameters(
                                        parameterWithName("labelId").description("수정할 레이블의 아이디")
                                ),
                                requestFields(
                                        fieldWithPath("title").type(JsonFieldType.STRING).description("업데이트 레이블 제목"),
                                        fieldWithPath("description").type(JsonFieldType.STRING).description("업데이트 레이블 설명"),
                                        fieldWithPath("backgroundColor").type(JsonFieldType.STRING).description("레이블의 배경색 (헥스 코드)"),
                                        fieldWithPath("textColor").type(JsonFieldType.STRING).description("레이블의 텍스트 색상 (헥스 코드)")
                                )
                        )
                );
        then(labelService).should().update(eq(labelId), any(RequestLabelDto.class));
    }

    @DisplayName("[Controller][DELETE] 레이블 삭제 - 정상 작동")
    @Test
    void 레이블_삭제_성공() throws Exception {
        //given
        Long labelId = 1L;
        willDoNothing().given(labelService).delete(labelId);

        //when & then
        mockMvc.perform(delete("/api/labels/{labelId}", labelId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andDo(
                        restDocs.document(
                                pathParameters(
                                        parameterWithName("labelId").description("삭제할 레이블의 아이디")
                                )
                        )
                );

        then(labelService).should().delete(labelId);
    }

    private RequestLabelDto createRequestLabelDto() {
        return RequestLabelDto.of(
                "새로운 레이블 제목",
                "#121212",
                "#121212",
                "새로운 레이블 작성 테스트를 위한 레이블"
        );
    }

    private ResponseLabelsDto createResponseLabelsDto(ResponseLabelDto responseLabelDto) {
        return ResponseLabelsDto.from(List.of(responseLabelDto));
    }

    private ResponseLabelDto createResponseLabelDto(Label label) {
        return ResponseLabelDto.from(label);
    }

    private Label createLabel() {
        return Label.of(
                1L,
                "레이블 제목",
                "#123456",
                "#123456",
                "레이블 설명"
        );
    }
}
