package team20.issuetracker.domain.issue;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import team20.issuetracker.domain.assignee.Assignee;
import team20.issuetracker.domain.comment.Comment;
import team20.issuetracker.domain.label.Label;
import team20.issuetracker.domain.member.Member;
import team20.issuetracker.domain.milestone.Milestone;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.*;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class Issue {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;
    private String content;
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

    @Builder
    public Issue(String title, String content, String author, IssueStatus status, LocalDateTime createdAt, Member member, Milestone milestone, List<Assignee> assignees, List<Comment> comments, List<Label> labels) {
        this.title = title;
        this.content = content;
        this.author = author;
        this.status = status;
        this.createdAt = createdAt;
        this.member = member;
        this.milestone = milestone;
        this.assignees = assignees;
        this.comments = comments;
        this.labels = labels;
    }
}
