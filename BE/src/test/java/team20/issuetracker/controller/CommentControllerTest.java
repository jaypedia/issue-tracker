package team20.issuetracker.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.mockito.BDDMockito.willDoNothing;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
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

import team20.issuetracker.service.CommentService;
import team20.issuetracker.service.dto.request.RequestCommentDto;

@DisplayName("컨트롤러 - 코멘트")
@WebMvcTest(CommentController.class)
@ContextConfiguration(classes = CommentController.class)
class CommentControllerTest {

    private final MockMvc mvc;

    ObjectMapper mapper = new ObjectMapper();

    @MockBean
    private CommentService commentService;

    public CommentControllerTest(@Autowired MockMvc mvc) {
        this.mvc = mvc;
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
        mvc.perform(post("/api/comments")
                        .requestAttr("oauthId", oauthId)
                        .content(mapper.writeValueAsString(requestCommentDto))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(content().string(String.valueOf(commentId)));
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
        mvc.perform(post("/api/comments/" + commentId)
                        .content(mapper.writeValueAsString(requestCommentDto))
                        .queryParam("id", String.valueOf(commentId))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(content().string(String.valueOf(commentId)));
        then(commentService).should().update(any(RequestCommentDto.class), eq(commentId));
    }

    @DisplayName("[Controller][POST] 코멘트 삭제 - 정상 작동")
    @Test
    void 코멘트_삭제_성공() throws Exception {
        // given
        Long commentId = 1L;
        RequestCommentDto requestCommentDto = createRequestCommentDto();
        willDoNothing().given(commentService).delete(commentId);

        // when & then
        mvc.perform(delete("/api/comments/" + commentId)
                        .content(mapper.writeValueAsString(requestCommentDto))
                        .queryParam("id", String.valueOf(commentId))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andDo(print());
        then(commentService).should().delete(commentId);
    }

    private RequestCommentDto createRequestCommentDto() {
        Long issueId = 1L;
        String comment = "comment";
        return RequestCommentDto.of(issueId, comment);
    }
}
