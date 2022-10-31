package team20.issuetracker.service;


import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.mockito.BDDMockito.willDoNothing;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import java.time.LocalDate;
import java.util.NoSuchElementException;
import java.util.Optional;

import team20.issuetracker.domain.comment.Comment;
import team20.issuetracker.domain.comment.CommentRepository;
import team20.issuetracker.domain.issue.Issue;
import team20.issuetracker.domain.issue.IssueRepository;
import team20.issuetracker.domain.member.Member;
import team20.issuetracker.domain.member.MemberRepository;
import team20.issuetracker.domain.milestone.Milestone;
import team20.issuetracker.login.oauth.Role;
import team20.issuetracker.service.dto.request.RequestCommentDto;

@DisplayName("비즈니스 로직 - 댓글")
@ExtendWith(MockitoExtension.class)
class CommentServiceTest {

    @InjectMocks
    private CommentService sut;

    @Mock
    private CommentRepository commentRepository;

    @Mock
    private MemberRepository memberRepository;

    @Mock
    private IssueRepository issueRepository;

    @DisplayName("댓글 정보를 입력하면, 댓글을 저장한다.")
    @Test
    void 댓글_저장() throws Exception {
        //given
        Member member = createMember();
        Issue issue = createIssue();
        RequestCommentDto newRequestDto = createRequestCommentDto(1L, "댓글 내용");
        Comment comment = createComment(newRequestDto);
        given(commentRepository.save(any(Comment.class))).willReturn(comment);
        given(memberRepository.findByOauthId(member.getOauthId())).willReturn(Optional.of(member));
        given(issueRepository.getReferenceById(issue.getId())).willReturn(issue);

        //when
        sut.save(newRequestDto, member.getOauthId());

        //then
        then(commentRepository).should().save(any(Comment.class));
        assertThat(commentRepository.save(comment).getContent())
                .isNotNull()
                .isEqualTo(comment.getContent());
    }

    @DisplayName("댓글 정보를 입력하고 수정하는 댓글의 ID 가 존재한다면, 해당 댓글을 수정한다.")
    @Test
    void 댓글_수정() throws Exception {
        // given
        String oldContent = "댓글 내용";
        String newContent = "새로운 댓글 내용";
        RequestCommentDto previousDto = createRequestCommentDto(1L, oldContent);
        Comment previousComment = createComment(previousDto);

        RequestCommentDto updateRequestDto = createRequestCommentDto(1L, newContent);
        given(commentRepository.findById(previousComment.getId()))
                .willReturn(Optional.of(previousComment));

        // when
        sut.update(updateRequestDto, previousComment.getId());

        // then
        then(commentRepository).should().findById(previousComment.getId());
        assertThat(previousComment.getContent())
                .isNotEqualTo(oldContent)
                .isEqualTo(newContent);
    }

    @DisplayName("수정하는 댓글의 ID 가 존재하지 않는다면, 예외를 발생시킨다.")
    @Test
    void 댓글_수정_실패() throws Exception {
        // given
        Long wrongCommentId = 2L;
        String newContent = "새로운 댓글 내용";

        RequestCommentDto updateRequestDto = createRequestCommentDto(wrongCommentId, newContent);
        given(commentRepository.findById(wrongCommentId)).willThrow(new IllegalArgumentException("존재하지 않는 댓글 아이디입니다."));

        // when
        assertThatThrownBy(() -> sut.update(updateRequestDto, wrongCommentId)).isInstanceOf(IllegalArgumentException.class)
            .hasMessageContaining("존재하지 않는 댓글 아이디입니다.");

        // then
        then(commentRepository).should().findById(wrongCommentId);
    }

    @DisplayName("댓글 아이디를 입력하면, 해당 댓글을 삭제한다.")
    @Test
    void 댓글_삭제() throws Exception {
        // given
        Issue issue = createIssue();
        Comment comment = createComment(RequestCommentDto.of(issue.getId(), "Comment Content"));

        Long commentId = comment.getId();
        willDoNothing().given(commentRepository).deleteById(commentId);
        given(commentRepository.findById(commentId)).willReturn(Optional.of(comment));

        // when
        sut.delete(commentId);

        // then
        then(commentRepository).should().deleteById(commentId);
    }

    @DisplayName("삭제하는 댓글의 ID 가 존재하지 않는다면, 예외를 발생시킨다.")
    @Test
    void 댓글_삭제_실패() throws Exception {
        // given
        Long wrongCommentId = 2L;
        String newContent = "새로운 댓글 내용";
        createRequestCommentDto(wrongCommentId, newContent);
        given(commentRepository.findById(wrongCommentId)).willThrow(new NoSuchElementException("존재하지 않는 댓글 아이디입니다."));

        // when
        assertThatThrownBy(() -> sut.delete(wrongCommentId)).isInstanceOf(NoSuchElementException.class)
            .hasMessageContaining("존재하지 않는 댓글 아이디입니다.");

        // then
        then(commentRepository).should().findById(wrongCommentId);
    }

    private Issue createIssue() {
        return Issue.of(
                1L,
                "이슈 타이틀",
                "이슈 내용",
                createMilestone()
        );
    }

    private Milestone createMilestone() {
        return Milestone.of(
                "마일스톤 타이틀",
                LocalDate.now().plusDays(1),
                "마일스톤 설명"
        );
    }

    private Member createMember() {
        return Member.builder()
                .id(1L)
                .oauthId("123456")
                .name("geombong")
                .email("123@123.com")
                .profileImageUrl("profileUrl")
                .role(Role.GUEST)
                .build();
    }

    private Comment createComment(RequestCommentDto requestCommentDto) {
        return RequestCommentDto.toEntity(
                "geombong",
                "profileUrl",
                requestCommentDto.getContent(),
                issueRepository.getReferenceById(requestCommentDto.getIssueId())
        );
    }

    private RequestCommentDto createRequestCommentDto(Long id, String content) {
        return RequestCommentDto.of(id, content);
    }
}
