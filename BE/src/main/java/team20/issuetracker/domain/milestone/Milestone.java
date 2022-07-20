package team20.issuetracker.domain.milestone;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import team20.issuetracker.domain.milestone.request.UpdateMilestoneDto;

import java.time.LocalDateTime;

import javax.persistence.*;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Entity
public class Milestone {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String title;
    private LocalDateTime startDate;
    private LocalDateTime endDate;
    private String description;

    @Enumerated(EnumType.STRING)
    private MilestoneStatus milestoneStatus;

    private Milestone(String title, LocalDateTime startDate, LocalDateTime endDate, String description) {
        this.title = title;
        this.startDate = startDate;
        this.endDate = endDate;
        this.description = description;
        this.milestoneStatus = MilestoneStatus.OPEN;
    }

    public static Milestone of(String title, LocalDateTime startDate, LocalDateTime endDate, String description) {
        return new Milestone(title, startDate, endDate, description);
    }

    public void update(UpdateMilestoneDto updateMilestoneDto) {
        this.title = updateMilestoneDto.getTitle() != null ? updateMilestoneDto.getTitle() : title;
        this.description = updateMilestoneDto.getDescription() != null ? updateMilestoneDto.getDescription() : description;
        this.startDate = updateMilestoneDto.getStartDate() != null ? updateMilestoneDto.getStartDate() : startDate;
        this.endDate = updateMilestoneDto.getEndDate() != null ? updateMilestoneDto.getEndDate() : endDate;
        this.milestoneStatus = updateMilestoneDto.getMilestoneStatus() != null ? updateMilestoneDto.getMilestoneStatus() : milestoneStatus;
    }
}
