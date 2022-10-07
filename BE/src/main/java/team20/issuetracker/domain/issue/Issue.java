package team20.issuetracker.domain.issue;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import org.springframework.http.HttpStatus;

import team20.issuetracker.domain.AuditingFields;
import team20.issuetracker.domain.assginee.Assignee;
import team20.issuetracker.domain.comment.Comment;
import team20.issuetracker.domain.label.Label;
import team20.issuetracker.domain.member.Member;
import team20.issuetracker.domain.milestone.Milestone;
import team20.issuetracker.exception.CheckEntityException;
import team20.issuetracker.service.dto.request.RequestUpdateIssueContentDto;
import team20.issuetracker.service.dto.request.RequestUpdateIssueTitleWithContentDto;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class Issue extends AuditingFields {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;
    private String content;

    @Enumerated(value = EnumType.STRING)
    private IssueStatus status = IssueStatus.OPEN;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "milestone_id")
    private Milestone milestone;

    @OneToMany(mappedBy = "issue", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    private List<IssueAssignee> issueAssignees = new ArrayList<>();

    @OneToMany(mappedBy = "issue", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Comment> comments = new ArrayList<>();

    @OneToMany(mappedBy = "issue", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    private List<IssueLabel> issueLabels = new ArrayList<>();

    private Issue(String title, String content, Milestone milestone) {
        this.title = title;
        this.content = content;
        this.milestone = milestone;
    }

    private Issue(Long id, String title, String content, Milestone milestone) {
        this.id = id;
        this.title = title;
        this.content = content;
        this.milestone = milestone;
    }

    public static Issue of(String title, String content, Milestone milestone) {
        return new Issue(title, content, milestone);
    }

    public static Issue of(Long id, String title, String content, Milestone milestone) {
        return new Issue(id, title, content, milestone);
    }

    public void updateTitleWithContent(RequestUpdateIssueTitleWithContentDto requestUpdateIssueTitleWithContentDto) {
        this.title = requestUpdateIssueTitleWithContentDto.getTitle();
        this.content = requestUpdateIssueTitleWithContentDto.getContent();
    }

    public void updateMilestone(Milestone findMilestone) {
        if (this.milestone != null && this.milestone.equals(findMilestone)) {
            this.milestone = null;
        } else {
            this.milestone = findMilestone;
        }
    }

    public void updateLabels(Label findLabel) {
        Long labelId = findLabel.getId();

        if (issueLabels.stream().anyMatch(issueLabel -> issueLabel.getLabel().getId().equals(labelId))) {
            issueLabels.remove(
                issueLabels.stream().filter(issueLabel -> issueLabel.getLabel().getId().equals(labelId))
                    .findAny()
                    .orElseThrow(() -> new CheckEntityException("해당 Label 은 존재하지 않습니다.", HttpStatus.BAD_REQUEST))
            );

        } else {
            addLabels(List.of(findLabel));
        }
    }

    public void updateAssignees(Assignee findAssignee) {
        Long assigneeId = findAssignee.getId();

        if (issueAssignees.stream().anyMatch(issueAssignee -> issueAssignee.getAssignee().getId().equals(assigneeId))) {
            issueAssignees.remove(
                issueAssignees.stream().filter(issueAssignee -> issueAssignee.getAssignee().getId().equals(assigneeId))
                    .findAny()
                    .orElseThrow(() -> new CheckEntityException("해당 Assignee 는 존재하지 않습니다.", HttpStatus.BAD_REQUEST))
            );

        } else {
            addAssignees(List.of(findAssignee));
        }
    }

    public void addAssignees(List<Assignee> assignees) {
        for (Assignee assignee : assignees) {
            this.issueAssignees.add(IssueAssignee.of(this, assignee));
        }
    }

    public void addLabels(List<Label> labels) {
        for (Label label : labels) {
            this.issueLabels.add(IssueLabel.of(this, label));
        }
    }

    public void addMember(Member findMember) {
        this.member = findMember;
    }
}
