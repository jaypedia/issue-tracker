package team20.issuetracker.domain.milestone;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import team20.issuetracker.domain.AuditingFields;
import team20.issuetracker.domain.issue.Issue;
import team20.issuetracker.service.dto.request.RequestUpdateMilestoneDto;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Entity
public class Milestone extends AuditingFields {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false, length = 50)
    private String title;

    @Column(nullable = false)
    private LocalDate dueDate;

    @Column(length = 800)
    private String description;

    @Enumerated(EnumType.STRING)
    private MilestoneStatus milestoneStatus;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "milestone")
    private List<Issue> issues = new ArrayList<>();

    private Milestone(Long id, String title, LocalDate dueDate, String description) {
        this.id = id;
        this.title = title;
        this.dueDate = dueDate;
        this.description = description;
        this.milestoneStatus = MilestoneStatus.OPEN;
    }

    private Milestone(Long id, String title, LocalDate dueDate, String description, MilestoneStatus milestoneStatus) {
        this.id = id;
        this.title = title;
        this.dueDate = dueDate;
        this.description = description;
        this.milestoneStatus = milestoneStatus;
    }

    private Milestone(String title, LocalDate dueDate, String description, MilestoneStatus milestoneStatus) {
        this.title = title;
        this.dueDate = dueDate;
        this.description = description;
        this.milestoneStatus = milestoneStatus;
    }

    private Milestone(String title, LocalDate dueDate, String description) {
        this.title = title;
        this.dueDate = dueDate;
        this.description = description;
        this.milestoneStatus = MilestoneStatus.OPEN;
    }

    public static Milestone of(String title, LocalDate dueDate, String description) {
        return new Milestone(title, dueDate, description);
    }

    public static Milestone of(String title, LocalDate dueDate, String description, MilestoneStatus milestoneStatus) {
        return new Milestone(title, dueDate, description, milestoneStatus);
    }

    public static Milestone of(Long id, String title, LocalDate dueDate, String description) {
        return new Milestone(id, title, dueDate, description);
    }

    public static Milestone of(Long id, String title, LocalDate dueDate, String description, MilestoneStatus milestoneStatus) {
        return new Milestone(id, title, dueDate, description, milestoneStatus);
    }

    public void updateIssue(Issue issue) {
        issues.add(issue);
    }

    public void update(RequestUpdateMilestoneDto requestUpdateMilestoneDto) {
        this.title = requestUpdateMilestoneDto.getTitle();
        this.description = requestUpdateMilestoneDto.getDescription();
        this.dueDate = requestUpdateMilestoneDto.getDueDate();
        this.milestoneStatus = requestUpdateMilestoneDto.getMilestoneStatus();
    }
}
