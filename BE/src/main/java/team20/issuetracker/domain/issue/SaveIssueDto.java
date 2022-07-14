package team20.issuetracker.domain.issue;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.Getter;
import lombok.NoArgsConstructor;
import team20.issuetracker.domain.assignee.ReadAssigneeDto;
import team20.issuetracker.domain.label.ReadLabelDto;
import team20.issuetracker.domain.milestone.ReadMilestoneDto;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Getter
@NoArgsConstructor
public class SaveIssueDto {

    private String title;
    private String content;
    private String author;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createdAt;

    private List<ReadAssigneeDto> assignees;
    private List<ReadLabelDto> labels;
    private ReadMilestoneDto milestone;

    public Issue toEntity() {
        return Issue.builder()
                .title(title)
                .content(content)
                .author(author)
                .createdAt(createdAt)
                .assignees(assignees.stream().map(ReadAssigneeDto::toEntity).collect(Collectors.toList()))
                .labels(labels.stream().map(ReadLabelDto::toEntity).collect(Collectors.toList()))
                .milestone(milestone.toEntity())
                .build();
    }
}
