package team20.issuetracker.domain.milestone.response;

import lombok.Getter;
import team20.issuetracker.domain.milestone.Milestone;
import team20.issuetracker.domain.milestone.MilestoneStatus;

import java.time.LocalDateTime;

@Getter
public class ReadMilestoneDto {

    private Long id;
    private String title;
    private LocalDateTime startDate;
    private LocalDateTime endDate;
    private String description;
    private MilestoneStatus milestoneStatus;

    private ReadMilestoneDto(Long id, String title, LocalDateTime startDate, LocalDateTime endDate, String description, MilestoneStatus milestoneStatus) {
        this.id = id;
        this.title = title;
        this.startDate = startDate;
        this.endDate = endDate;
        this.description = description;
        this.milestoneStatus = milestoneStatus;
    }

    public static ReadMilestoneDto of(Milestone milestone) {
        return new ReadMilestoneDto(milestone.getId(), milestone.getTitle(), milestone.getStartDate(), milestone.getEndDate(), milestone.getDescription(), milestone.getMilestoneStatus());
    }
}