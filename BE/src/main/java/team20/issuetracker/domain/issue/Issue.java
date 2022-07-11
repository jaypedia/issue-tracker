package team20.issuetracker.domain.issue;

import team20.issuetracker.domain.assignee.Assignee;
import team20.issuetracker.domain.comment.Comment;
import team20.issuetracker.domain.label.Label;
import team20.issuetracker.domain.member.Member;
import team20.issuetracker.domain.milestone.Milestone;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.*;

@Entity
public class Issue {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;
    private String author;

    @Enumerated(value = EnumType.STRING)
    private IssueStatus status;

    private LocalDateTime createdAt;

    // TODO : 회원과 이슈 매핑 (이슈 다 : 회원 일)
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    // TODO : 마일스톤과 이슈 매핑 (이슈 다 : 마일스톤 일)
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "milestone_id")
    private Milestone milestone;

    // TODO : 담당자와 이슈 매핑 (이슈 일 : 담당자 다)
    @OneToMany(fetch = FetchType.LAZY)
    @JoinColumn(name = "assignee_id")
    private List<Assignee> assignees = new ArrayList<>();

    // TODO : 댓글과 이슈 매핑 (이슈 일 : 댓글 다)
    @OneToMany(fetch = FetchType.LAZY)
    @JoinColumn(name = "comment_id")
    private List<Comment> comments = new ArrayList<>();

    // TODO : 레이블과 이슈 매핑 (이슈 일 : 레이블 다)
    @OneToMany(fetch = FetchType.LAZY)
    @JoinColumn(name = "label_id")
    private List<Label> labels = new ArrayList<>();
}
