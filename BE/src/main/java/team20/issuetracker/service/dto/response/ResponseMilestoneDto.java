package team20.issuetracker.service.dto.response;

import java.time.LocalDate;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import team20.issuetracker.domain.milestone.Milestone;

@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class ResponseMilestoneDto {

    private Long id;
    private String title;
    private LocalDate startDate;
    private LocalDate endDate;
    private String description;
    private String milestoneStatus;

    public static ResponseMilestoneDto of(Milestone milestone) {
        return new ResponseMilestoneDto(milestone.getId(), milestone.getTitle(), milestone.getStartDate(), milestone.getDueDate(), milestone.getDescription(), milestone.getMilestoneStatus().toString().toLowerCase());
    }
}
