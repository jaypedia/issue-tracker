package team20.issuetracker.integration.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;

import team20.issuetracker.config.DatabaseCleanup;
import team20.issuetracker.domain.member.Member;
import team20.issuetracker.domain.member.MemberRepository;
import team20.issuetracker.login.oauth.Role;
import team20.issuetracker.service.CommentService;
import team20.issuetracker.service.IssueService;
import team20.issuetracker.service.dto.request.RequestCommentDto;
import team20.issuetracker.service.dto.request.RequestSaveIssueDto;

@DisplayName("컨트롤러 - 코멘트 통합 테스트")
@Transactional
@AutoConfigureMockMvc
@SpringBootTest
class CommentControllerTest {

    private final MockMvc mvc;
    private final ObjectMapper mapper = new ObjectMapper();

    public CommentControllerTest(@Autowired MockMvc mvc) {
        this.mvc = mvc;
    }

    @Autowired
    private DatabaseCleanup databaseCleanup;

    @Autowired
    private CommentService commentService;

    @Autowired
    private IssueService issueService;

    @Autowired
    MemberRepository memberRepository;

    @BeforeEach
    public void setUp() {
        databaseCleanup.execute();
    }

    @DisplayName("모든 코멘트 API 호출 시 올바르지 않은 Access Token 을 받는다면 Status 로 302 Redirect 응답이 발생한다.")
    @Test
    void 리다이렉트_302_응답_발생() throws Exception {
        // when & then
        mvc.perform(get("/api/milestones")
                .header("Authorization", "None Access Token"))
            .andDo(print())
            .andExpect(status().is(302));
    }

    @DisplayName("[Controller][POST] 댓글 작성 - 정상 호출")
    @Test
    void 댓글_저장() throws Exception {
        // given
        Member member = createMember();
        memberRepository.save(member);

        RequestSaveIssueDto saveIssueDto = saveIssueDto();
        Long saveIssueId = issueService.save(saveIssueDto);

        RequestCommentDto saveCommentDto = saveCommentDto(saveIssueId);

        // when & then
        mvc.perform(post("/api/comments")
                .header("Authorization", "Test Access Token")
                .content(mapper.writeValueAsString(saveCommentDto))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
            .andDo(print())
            .andExpect(status().isOk());
    }

    @DisplayName("[Controller][POST] 댓글 수정 - 정상 호출")
    @Test
    void 댓글_수정() throws Exception {
        // given
        Member member = createMember();
        memberRepository.save(member);

        RequestSaveIssueDto saveIssueDto = saveIssueDto();
        Long saveIssueId = issueService.save(saveIssueDto);

        RequestCommentDto saveCommentDto = saveCommentDto(saveIssueId);
        commentService.save(saveCommentDto, "oauthId");

        RequestCommentDto updateCommentDto = RequestCommentDto.of(saveIssueId, "Update Comment Content");

        // when & then
        mvc.perform(post("/api/comments/" + saveIssueId)
                .header("Authorization", "Test Access Token")
                .content(mapper.writeValueAsString(updateCommentDto))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
            .andDo(print())
            .andExpect(status().isOk());
    }

    @DisplayName("[Controller][DELETE] 댓글 삭제 - 정상 호출")
    @Test
    void 댓글_삭제() throws Exception {
        // given
        Member member = createMember();
        memberRepository.save(member);

        RequestSaveIssueDto saveIssueDto = saveIssueDto();
        Long saveIssueId = issueService.save(saveIssueDto);

        RequestCommentDto saveCommentDto = saveCommentDto(saveIssueId);
        commentService.save(saveCommentDto, "oauthId");

        // when & then
        mvc.perform(delete("/api/comments/" + saveIssueId)
                .header("Authorization", "Test Access Token"))
            .andDo(print())
            .andExpect(status().isOk());
    }

    private Member createMember() {
        return Member.builder()
            .id(1L)
            .oauthId("oauthId")
            .name("geombong")
            .email("123@123.com")
            .profileImageUrl("profileUrl")
            .role(Role.GUEST)
            .build();
    }

    private RequestCommentDto saveCommentDto(Long saveIssueId) {
        String CommentContent = "Comment Content";

        return RequestCommentDto.of(saveIssueId, CommentContent);
    }

    private RequestSaveIssueDto saveIssueDto() {
        String issueTitle = "Issue Title";
        String issueContent = "Issue Content";

        return RequestSaveIssueDto.of(issueTitle, issueContent, new ArrayList<>(), new ArrayList<>(), new ArrayList<>());
    }
}
