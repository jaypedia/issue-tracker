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

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "milestone_id")
    private Milestone milestone;

    @OneToMany(fetch = FetchType.LAZY)
    @JoinColumn(name = "assignee_id")
    private List<Assignee> assignees = new ArrayList<>();

    @OneToMany(fetch = FetchType.LAZY)
    @JoinColumn(name = "comment_id")
    private List<Comment> comments = new ArrayList<>();

    @OneToMany(fetch = FetchType.LAZY)
    @JoinColumn(name = "label_id")
    private List<Label> labels = new ArrayList<>();
}
