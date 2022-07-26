package team20.issuetracker.service.dto.response;

import lombok.*;
import team20.issuetracker.domain.assginee.Assignee;
import team20.issuetracker.domain.assginee.AssigneeRepository;
import team20.issuetracker.domain.issue.Issue;
import team20.issuetracker.domain.issue.IssueAssignee;
import team20.issuetracker.domain.issue.IssueLabel;
import team20.issuetracker.domain.issue.IssueStatus;
import team20.issuetracker.domain.label.Label;
import team20.issuetracker.domain.label.LabelRepository;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Getter
public class ResponseIssueDto {

    private Long id;
    private String title;
    private String author;
    private LocalDate createAt;
    private IssueStatus issueStatus;
    private String milestoneTitle;
    private List<ResponseLabelDto> labels;
    private List<ResponseAssigneeDto> assignees;

    private ResponseIssueDto(Long id, String title, String author, LocalDate createAt, IssueStatus issueStatus, String milestoneTitle, List<ResponseLabelDto> labels, List<ResponseAssigneeDto> assignees) {

        this.id = id;
        this.title = title;
        this.author = author;
        this.createAt = createAt;
        this.issueStatus = issueStatus;
        this.milestoneTitle = milestoneTitle;
        this.labels = labels;
        this.assignees = assignees;
    }

    public static ResponseIssueDto of(Issue issue, List<Label> label, List<Assignee> assignee) {
        return new ResponseIssueDto(
                issue.getId(),
                issue.getTitle(),
                issue.getAuthorId(),
                issue.getCreatedAt(),
                issue.getStatus(),
                issue.getMilestone().getTitle(),
                label.stream().map(ResponseLabelDto::from).collect(Collectors.toList()),
                assignee.stream().map(ResponseAssigneeDto::from).collect(Collectors.toList()));
    }
}
