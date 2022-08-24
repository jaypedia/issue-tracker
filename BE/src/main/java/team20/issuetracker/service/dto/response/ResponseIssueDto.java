package team20.issuetracker.service.dto.response;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import team20.issuetracker.domain.issue.Issue;
import team20.issuetracker.domain.issue.IssueAssignee;
import team20.issuetracker.domain.issue.IssueLabel;
import team20.issuetracker.domain.member.Member;

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
    private Set<ResponseMilestoneDto> milestones;
    private Set<ResponseCommentDto> comments;
    private Set<ResponseLabelDto> labels;
    private Set<ResponseAssigneeDto> assignees;

    private ResponseIssueDto(Long id,
                             String title,
                             String author,
                             int commentCount,
                             String content,
                             LocalDate createdAt,
                             String issueStatus,
                             Set<ResponseMilestoneDto> milestones,
                             Set<ResponseCommentDto> comments,
                             Set<ResponseLabelDto> labels,
                             Set<ResponseAssigneeDto> assignees) {
        this.id = id;
        this.title = title;
        this.author = author;
        this.commentCount = commentCount;
        this.content = content;
        this.createdAt = createdAt;
        this.issueStatus = issueStatus;
        this.milestones = milestones;
        this.comments = comments;
        this.labels = labels;
        this.assignees = assignees;
    }

    public static ResponseIssueDto of(Issue issue, Member member) {
        Set<ResponseMilestoneDto> milestones = new HashSet<>();

        if (issue.getMilestone() != null) {
            milestones = Set.of(ResponseMilestoneDto.from(issue.getMilestone()));
        }

        return new ResponseIssueDto(
                issue.getId(),
                issue.getTitle(),
                issue.getAuthorId(),
                member.getProfileImageUrl(),
                issue.getComments().size(),
                issue.getContent(),
                issue.getCreatedAt(),
                issue.getStatus().toString().toLowerCase(),
                milestones,
                issue.getComments().stream()
                        .map(ResponseCommentDto::from)
                        .collect(Collectors.toSet()),
                issue.getIssueLabels().stream()
                        .map(IssueLabel::getLabel)
                        .map(ResponseLabelDto::from)
                        .collect(Collectors.toSet()),
                issue.getIssueAssignees().stream()
                        .map(IssueAssignee::getAssignee)
                        .map(ResponseAssigneeDto::from)
                        .collect(Collectors.toSet()));
    }

    public static ResponseIssueDto of(Issue issue) {
        Set<ResponseMilestoneDto> milestones = new HashSet<>();

        if (issue.getMilestone() != null) {
            milestones = Set.of(ResponseMilestoneDto.from(issue.getMilestone()));
        }

        return new ResponseIssueDto(
                issue.getId(),
                issue.getTitle(),
                issue.getAuthorId(),
                issue.getComments().size(),
                issue.getContent(),
                issue.getCreatedAt(),
                issue.getStatus().toString().toLowerCase(),
                milestones,
                issue.getComments().stream()
                        .map(ResponseCommentDto::from)
                        .collect(Collectors.toSet()),
                issue.getIssueLabels().stream()
                        .map(IssueLabel::getLabel)
                        .map(ResponseLabelDto::from)
                        .collect(Collectors.toSet()),
                issue.getIssueAssignees().stream()
                        .map(IssueAssignee::getAssignee)
                        .map(ResponseAssigneeDto::from)
                        .collect(Collectors.toSet()));
    }
}
