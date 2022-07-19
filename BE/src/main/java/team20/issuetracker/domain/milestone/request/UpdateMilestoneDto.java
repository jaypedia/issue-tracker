package team20.issuetracker.domain.milestone.request;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import team20.issuetracker.domain.milestone.MilestoneStatus;

import java.time.LocalDateTime;

@Getter
public class UpdateMilestoneDto {

    private String title;
    private String description;
    private MilestoneStatus milestoneStatus;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime startDate;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime endDate;
}
