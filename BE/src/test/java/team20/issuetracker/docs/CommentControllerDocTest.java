package team20.issuetracker.docs;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.mockito.BDDMockito.willDoNothing;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.documentationConfiguration;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.delete;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.post;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.requestFields;
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

import team20.issuetracker.controller.CommentController;
import team20.issuetracker.docs.config.RestDocsConfig;
import team20.issuetracker.service.CommentService;
import team20.issuetracker.service.dto.request.RequestCommentDto;

@DisplayName("Spring Docs - 댓글")
@ExtendWith(RestDocumentationExtension.class)
@WebMvcTest(CommentController.class)
@Import(RestDocsConfig.class)
@ContextConfiguration(classes = CommentController.class)
class CommentControllerDocTest {

    @Autowired
    private RestDocumentationResultHandler restDocs;
    @MockBean
    private CommentService commentService;

    private MockMvc mockMvc;
    private ObjectMapper mapper;

    public CommentControllerDocTest() {
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

    @DisplayName("[Controller][POST] 새로운 코멘트 작성 - 정상 작동")
    @Test
    void 새로운_코멘트_작성_성공() throws Exception {
        // given
        Long commentId = 1L;
        String oauthId = "oauth";
        RequestCommentDto requestCommentDto = createRequestCommentDto();
        given(commentService.save(any(RequestCommentDto.class), eq(oauthId))).willReturn(commentId);

        // when & then
        mockMvc.perform(post("/api/comments")
                        .requestAttr("oauthId", oauthId)
                        .content(mapper.writeValueAsString(requestCommentDto))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().string(String.valueOf(commentId)))
                .andDo(
                        restDocs.document(
                                requestFields(
                                        fieldWithPath("issueId").type(JsonFieldType.NUMBER).description("해당 댓글을 작성할 이슈의 아이디"),
                                        fieldWithPath("content").type(JsonFieldType.STRING).description("댓글 내용")
                                )
                        )
                );

        then(commentService).should().save(any(RequestCommentDto.class), eq(oauthId));
    }

    @DisplayName("[Controller][POST] 코멘트 업데이트 - 정상 작동")
    @Test
    void 코멘트_업데이트_성공() throws Exception {
        // given
        Long commentId = 1L;
        RequestCommentDto requestCommentDto = createRequestCommentDto();
        given(commentService.update(any(RequestCommentDto.class), eq(commentId))).willReturn(commentId);

        // when & then
        mockMvc.perform(post("/api/comments/{commentId}", commentId)
                        .content(mapper.writeValueAsString(requestCommentDto))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().string(String.valueOf(commentId)))
                .andDo(
                        restDocs.document(
                                pathParameters(
                                        parameterWithName("commentId").description("수정할 댓글의 아이디")
                                ),
                                requestFields(
                                        fieldWithPath("issueId").type(JsonFieldType.NUMBER).description("해당 댓글이 작성되어 있는 이슈의 아이디"),
                                        fieldWithPath("content").type(JsonFieldType.STRING).description("수정할 댓글 내용")
                                )
                        )
                );

        then(commentService).should().update(any(RequestCommentDto.class), eq(commentId));
    }

    @DisplayName("[Controller][POST] 코멘트 삭제 - 정상 작동")
    @Test
    void 코멘트_삭제_성공() throws Exception {
        // given
        Long commentId = 1L;
        willDoNothing().given(commentService).delete(commentId);

        // when & then
        mockMvc.perform(delete("/api/comments/{commentId}", commentId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andDo(
                        restDocs.document(
                                pathParameters(
                                        parameterWithName("commentId").description("삭제할 댓글의 아이디")
                                )
                        )
                );

        then(commentService).should().delete(commentId);
    }

    private RequestCommentDto createRequestCommentDto() {
        Long issueId = 1L;
        String comment = "comment";
        return RequestCommentDto.of(issueId, comment);
    }
}
