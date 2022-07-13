package team20.issuetracker.domain.milestone;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.NoArgsConstructor;
import team20.issuetracker.domain.issue.Issue;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@NoArgsConstructor
public class ReadMilestoneDto {

    private String title;
    private String description;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime startDate;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime endDate;

    private List<Issue> issues;

    public Milestone toEntity() {
        return Milestone.builder()
                .description(description)
                .issues(issues)
                .endDate(endDate)
                .startDate(startDate)
                .title(title)
                .build();
    }
}
