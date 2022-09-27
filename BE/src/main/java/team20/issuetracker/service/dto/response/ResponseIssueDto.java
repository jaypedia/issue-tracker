package team20.issuetracker.service.dto.response;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import team20.issuetracker.domain.issue.Issue;
import team20.issuetracker.domain.issue.IssueAssignee;
import team20.issuetracker.domain.issue.IssueLabel;

@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class ResponseIssueDto {

    private Long id;
    private String title;
    private String author;
    private String image;
    private int commentCount;
    private String content;
    private LocalDate createdAt;
    private String issueStatus;
    private List<ResponseMilestoneDto> milestones;
    private List<ResponseCommentDto> comments;
    private List<ResponseLabelDto> labels;
    private List<ResponseAssigneeDto> assignees;

    public static ResponseIssueDto of(Issue issue) {
        List<ResponseMilestoneDto> milestones = new ArrayList<>();

        if (issue.getMilestone() != null) {
            milestones = List.of(ResponseMilestoneDto.from(issue.getMilestone()));
        }

        return new ResponseIssueDto(
                issue.getId(),
                issue.getTitle(),
                issue.getMember().getName(),
                issue.getMember().getProfileImageUrl(),
                issue.getComments().size(),
                issue.getContent(),
                issue.getCreatedAt(),
                issue.getStatus().toString().toLowerCase(),
                milestones,
                issue.getComments().stream()
                        .map(ResponseCommentDto::from)
                        .collect(Collectors.toList()),
                issue.getIssueLabels().stream()
                        .map(IssueLabel::getLabel)
                        .map(ResponseLabelDto::from)
                        .collect(Collectors.toList()),
                issue.getIssueAssignees().stream()
                        .map(IssueAssignee::getAssignee)
                        .map(ResponseAssigneeDto::from)
                        .collect(Collectors.toList()));
    }
}
