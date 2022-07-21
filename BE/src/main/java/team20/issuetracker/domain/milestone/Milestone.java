package team20.issuetracker.domain.milestone;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import team20.issuetracker.service.dto.request.RequestUpdateMilestoneDto;

import java.time.LocalDateTime;

import javax.persistence.*;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Entity
public class Milestone {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 50)
    private String title;

    @Column(nullable = false)
    private LocalDateTime startDate;

    @Column(nullable = false)
    private LocalDateTime endDate;

    @Column(length = 800)
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

    public void update(RequestUpdateMilestoneDto requestUpdateMilestoneDto) {
        this.title = requestUpdateMilestoneDto.getTitle() != null ? requestUpdateMilestoneDto.getTitle() : title;
        this.description = requestUpdateMilestoneDto.getDescription() != null ? requestUpdateMilestoneDto.getDescription() : description;
        this.startDate = requestUpdateMilestoneDto.getStartDate() != null ? requestUpdateMilestoneDto.getStartDate() : startDate;
        this.endDate = requestUpdateMilestoneDto.getEndDate() != null ? requestUpdateMilestoneDto.getEndDate() : endDate;
        this.milestoneStatus = requestUpdateMilestoneDto.getMilestoneStatus() != null ? requestUpdateMilestoneDto.getMilestoneStatus() : milestoneStatus;
    }
}
