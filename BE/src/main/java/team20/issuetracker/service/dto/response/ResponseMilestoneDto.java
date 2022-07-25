package team20.issuetracker.service.dto.response;

import java.time.LocalDate;

import lombok.Getter;
import team20.issuetracker.domain.milestone.Milestone;
import team20.issuetracker.domain.milestone.MilestoneStatus;

@Getter
public class ResponseMilestoneDto {

    private Long id;
    private String title;
    private LocalDate startDate;
    private LocalDate endDate;
    private String description;
    private MilestoneStatus milestoneStatus;

    private ResponseMilestoneDto(Long id, String title, LocalDate startDate, LocalDate endDate, String description, MilestoneStatus milestoneStatus) {
        this.id = id;
        this.title = title;
        this.startDate = startDate;
        this.endDate = endDate;
        this.description = description;
        this.milestoneStatus = milestoneStatus;
    }

    public static ResponseMilestoneDto of(Milestone milestone) {
        return new ResponseMilestoneDto(milestone.getId(), milestone.getTitle(), milestone.getStartDate(), milestone.getEndDate(), milestone.getDescription(), milestone.getMilestoneStatus());
    }
}
