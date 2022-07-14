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

import javax.persistence.*;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class Issue {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String author;
    private String title;
    private String content;

    @Enumerated(value = EnumType.STRING)
    private IssueStatus status = IssueStatus.OPEN;

    private LocalDateTime createdAt;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "milestone_id")
    private Milestone milestone;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "assignes_id")
    private Assignee assignee;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "comment_id")
    private Comment comment;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "lebel_id")
    private Label label;

    @Builder
    public Issue(String title, String content, String author, LocalDateTime createdAt, Milestone milestone, Assignee assignee, Label label) {
        this.title = title;
        this.content = content;
        this.author = author;
        this.createdAt = createdAt;
        this.milestone = milestone;
        this.assignee = assignee;
        this.label = label;
    }

//    public void setMilestone(Milestone milestone) {
//        this.milestone = milestone;
//        milestone.getIssues().add(this);
//    }
}
