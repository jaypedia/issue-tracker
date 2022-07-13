package team20.issuetracker.domain.issue;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import team20.issuetracker.domain.assignee.Assignee;
import team20.issuetracker.domain.comment.Comment;
import team20.issuetracker.domain.label.Label;
import team20.issuetracker.domain.milestone.Milestone;

import java.nio.file.Files;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
@NoArgsConstructor
public class SaveIssueDto {

    private String title;
    private String content;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createdAt;

    private String author;
    private IssueStatus issueStatus;
//    private Files files;
    private List<Assignee> assignees = new ArrayList<>();
    private List<Label> labels = new ArrayList<>();
    private Milestone milestone;

    @Builder
    public SaveIssueDto(String title, String content, LocalDateTime createdAt, String author, IssueStatus issueStatus, Files files, List<Assignee> assignees, List<Label> labels, Milestone milestone) {
        this.title = title;
        this.content = content;
        this.createdAt = createdAt;
        this.author = author;
        this.issueStatus = issueStatus;
//        this.files = files;
        this.assignees = assignees;
        this.labels = labels;
        this.milestone = milestone;
    }

    public Issue toEntity() {
        return Issue.builder()
                .title(title)
                .content(content)
                .assignees(assignees)
                .author(author)
                .createdAt(createdAt)
                .labels(labels)
                .milestone(milestone)
                .status(issueStatus)
                .build();
    }
}
