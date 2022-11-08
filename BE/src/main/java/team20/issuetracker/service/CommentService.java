package team20.issuetracker.service;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;
import team20.issuetracker.domain.comment.Comment;
import team20.issuetracker.domain.comment.CommentRepository;
import team20.issuetracker.domain.issue.Issue;
import team20.issuetracker.domain.issue.IssueRepository;
import team20.issuetracker.domain.member.Member;
import team20.issuetracker.domain.member.MemberRepository;
import team20.issuetracker.exception.CheckEntityException;
import team20.issuetracker.service.dto.request.RequestCommentDto;

@RequiredArgsConstructor
@Transactional(readOnly = true)
@Service
public class CommentService {

    private final CommentRepository commentRepository;
    private final MemberRepository memberRepository;
    private final IssueRepository issueRepository;

    @Transactional
    public Long save(RequestCommentDto requestCommentDto, String oauthId) {
        Member member = memberRepository.findByOauthId(oauthId).orElseThrow(() -> {
            throw new CheckEntityException("존재하지 않는 회원 입니다.", HttpStatus.BAD_REQUEST);
        });
        String memberName = member.getName();
        String profileImageUrl = member.getProfileImageUrl();
        Issue savedIssue = issueRepository.getReferenceById(requestCommentDto.getIssueId());
        String content = requestCommentDto.getContent();
        return commentRepository.save(RequestCommentDto.toEntity(memberName, profileImageUrl, content, savedIssue)).getId();
    }

    @Transactional
    public Long update(RequestCommentDto requestCommentDto, Long commentId) {
        Comment savedComment = commentRepository.findById(commentId).orElseThrow(() -> {
            throw new CheckEntityException("존재하지 않는 댓글 아이디 입니다.", HttpStatus.BAD_REQUEST);
        });
        return savedComment.update(requestCommentDto);
    }

    @Transactional
    public void delete(Long id) {
        commentRepository.findById(id).orElseThrow((() -> {
            throw new CheckEntityException("존재하지 않는 댓글 아이디입니다.", HttpStatus.BAD_REQUEST);
        }));

        commentRepository.deleteById(id);
    }
}
