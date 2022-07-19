package team20.issuetracker.domain.milestone.request;

import lombok.Getter;
import team20.issuetracker.domain.milestone.MilestoneStatus;

import java.time.LocalDateTime;

@Getter
public class UpdateMilestoneDto {

    private String title;
    private LocalDateTime startDate;
    private LocalDateTime endDate;
    private String description;
    private MilestoneStatus milestoneStatus;
}
