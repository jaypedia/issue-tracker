package team20.issuetracker.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.NoSuchElementException;

import lombok.RequiredArgsConstructor;
import team20.issuetracker.domain.comment.CommentRepository;
import team20.issuetracker.domain.issue.Issue;
import team20.issuetracker.domain.issue.IssueRepository;
import team20.issuetracker.domain.member.Member;
import team20.issuetracker.domain.member.MemberRepository;
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
            throw new NoSuchElementException("존재하지 않는 회원 입니다.");
        });
        String memberId = member.getName();
        String profileImageUrl = member.getProfileImageUrl();
        Issue savedIssue = issueRepository.getReferenceById(requestCommentDto.getIssueId());
        return commentRepository.save(requestCommentDto.toEntity(memberId, profileImageUrl, savedIssue)).getId();
    }
}
