package team20.issuetracker.service.dto.request;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.Setter;
import team20.issuetracker.domain.milestone.MilestoneStatus;

import java.time.LocalDateTime;

@Getter
@Setter
public class RequestUpdateMilestoneDto {

    private String title;
    private String description;
    private MilestoneStatus milestoneStatus;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime startDate;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime endDate;
}
