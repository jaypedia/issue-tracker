package team20.issuetracker.service.dto.response;

import java.time.LocalDate;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import team20.issuetracker.domain.issue.IssueStatus;
import team20.issuetracker.domain.milestone.Milestone;

@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class ResponseMilestoneDto {

    private Long id;
    private String title;
    private LocalDate dueDate;
    private String description;
    private String milestoneStatus;
    private long openIssueCount;
    private long closeIssueCount;

    public static ResponseMilestoneDto from(Milestone milestone) {
        return new ResponseMilestoneDto(
                milestone.getId(),
                milestone.getTitle(),
                milestone.getDueDate(),
                milestone.getDescription(),
                milestone.getMilestoneStatus().toString().toLowerCase(),
                milestone.getIssues().stream().filter(issue -> issue.getStatus().equals(IssueStatus.OPEN)).count(),
                milestone.getIssues().stream().filter(issue -> issue.getStatus().equals(IssueStatus.CLOSED)).count());
    }
}
