package team20.issuetracker.integration.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.transaction.annotation.Transactional;

import team20.issuetracker.config.DatabaseCleanup;
import team20.issuetracker.domain.comment.Comment;
import team20.issuetracker.domain.comment.CommentRepository;
import team20.issuetracker.domain.issue.Issue;
import team20.issuetracker.domain.issue.IssueRepository;
import team20.issuetracker.domain.member.Member;
import team20.issuetracker.domain.member.MemberRepository;
import team20.issuetracker.exception.CheckEntityException;
import team20.issuetracker.login.oauth.Role;
import team20.issuetracker.service.dto.request.RequestCommentDto;

@DisplayName("[통합 테스트] 코맨트 테스트")
@Transactional
@SpringBootTest
class CommentServiceTest {

    @Autowired
    private CommentRepository commentRepository;

    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private IssueRepository issueRepository;

    @Autowired
    private DatabaseCleanup databaseCleanup;

    @BeforeEach
    public void setUp() {
        databaseCleanup.execute();
    }

    @DisplayName("현재 멤버가 하나의 이슈에 코멘트 정보를 입력하면, 코멘트를 저장한다.")
    @Test
    void 댓글_작성() {
        // given
        Member member = createMember();
        Member saveMember = memberRepository.save(member);

        Issue issue = createIssue();
        Issue saveIssue = issueRepository.save(issue);

        RequestCommentDto dto = RequestCommentDto.of(saveIssue.getId(), "Comment Content");
        Comment comment = RequestCommentDto
            .toEntity(saveMember.getName(), saveMember.getProfileImageUrl(), dto.getContent(), saveIssue);

        // when
        Comment saveComment = commentRepository.save(comment);

        // then
        assertThat(saveComment.getId()).isEqualTo(comment.getId());
    }

    @DisplayName("댓글 저장 시 회원이 존재하지 않는다면, 예외를 발생시킨다.")
    @Test
    void 댓글_저장_실패() {
        // given
        String wrongOauthId = "000000";

        // when
        assertThatThrownBy(() -> memberRepository.findByOauthId(wrongOauthId)
            .orElseThrow(() -> new CheckEntityException("존재하지 않는 회원 입니다.", HttpStatus.BAD_REQUEST)))
            .isInstanceOf(CheckEntityException.class)
            .hasMessageContaining("존재하지 않는 회원 입니다.");
    }

    @DisplayName("코멘트 ID 를 찾아 해당 코멘트를 삭제한다.")
    @Test
    void 댓글_삭제() {
        // given
        Member member = createMember();
        Member saveMember = memberRepository.save(member);

        Issue issue = createIssue();
        Issue saveIssue = issueRepository.save(issue);

        RequestCommentDto dto = RequestCommentDto.of(saveIssue.getId(), "Comment Content");
        Comment comment = RequestCommentDto
            .toEntity(saveMember.getName(), saveMember.getProfileImageUrl(), dto.getContent(), saveIssue);
        Comment saveComment = commentRepository.save(comment);

        // when
        commentRepository.deleteById(saveComment.getId());
    }

    @DisplayName("코멘트 ID 를 찾아 해당 코멘트를 수정한다.")
    @Test
    void 댓글_수정() {
        // given
        Member member = createMember();
        Member saveMember = memberRepository.save(member);

        Issue issue = createIssue();
        Issue saveIssue = issueRepository.save(issue);

        RequestCommentDto dto = RequestCommentDto.of(saveIssue.getId(), "Comment Content");
        Comment comment = RequestCommentDto
            .toEntity(saveMember.getName(), saveMember.getProfileImageUrl(), dto.getContent(), saveIssue);
        commentRepository.save(comment);

        RequestCommentDto updateDto = RequestCommentDto.of(saveIssue.getId(), "Update Comment Content");

        // when
        comment.update(updateDto);

        // then
        assertThat(comment.getContent()).isEqualTo("Update Comment Content");
    }

    @DisplayName("수정 및 삭제하는 댓글의 ID 가 존재하지 않는다면, 예외를 발생시킨다.")
    @Test
    void 댓글_수정_실패() {
        // given
        Long wrongCommentId = 2L;

        // when
        assertThatThrownBy(() -> commentRepository.findById(wrongCommentId)
            .orElseThrow(() -> new CheckEntityException("존재하지 않는 댓글 아이디입니다.", HttpStatus.BAD_REQUEST)))
            .isInstanceOf(CheckEntityException.class)
            .hasMessageContaining("존재하지 않는 댓글 아이디입니다.");
    }


    private Issue createIssue() {
        Long IssueId = 1L;
        String IssueTitle = "IssueTitle";
        String IssueContent = "IssueContent";
        Issue issue = Issue.of(IssueId, IssueTitle, IssueContent, null);
        return issue;
    }

    private Member createMember() {
        return Member.builder()
            .id(1L)
            .oauthId("78953393")
            .email("shoy1415@gmail.com")
            .name("geombong")
            .profileImageUrl("https://avatars.githubusercontent.com/u/78953393?v=4")
            .role(Role.GUEST)
            .build();
    }
}
