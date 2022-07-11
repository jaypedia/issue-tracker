package team20.issuetracker.domain.issue;

import lombok.Builder;
import lombok.Getter;
import team20.issuetracker.domain.assignee.Assignee;
import team20.issuetracker.domain.comment.Comment;
import team20.issuetracker.domain.label.Label;
import team20.issuetracker.domain.milestone.Milestone;

import java.nio.file.Files;
import java.time.LocalDateTime;
import java.util.List;

@Getter
public class SaveIssueDto {

    private final String title;
    private final String content;
    private final LocalDateTime createdAt;
    private final String author;
    private final IssueStatus issueStatus;
    private final Files files;
    private final List<Comment> comments;
    private final List<Assignee> assignees;
    private final List<Label> labels;
    private final Milestone milestone;

    @Builder
    public SaveIssueDto(String title, String content, LocalDateTime createdAt, String author, IssueStatus issueStatus, Files files, List<Comment> comments, List<Assignee> assignees, List<Label> labels, Milestone milestone) {
        this.title = title;
        this.content = content;
        this.createdAt = createdAt;
        this.author = author;
        this.issueStatus = issueStatus;
        this.files = files;
        this.comments = comments;
        this.assignees = assignees;
        this.labels = labels;
        this.milestone = milestone;
    }

    public Issue toEntity() {
        return Issue.builder()
                .title(title)
                .content(content)
                .comments(comments)
                .assignees(assignees)
                .author(author)
                .createdAt(createdAt)
                .labels(labels)
                .milestone(milestone)
                .status(issueStatus)
                .build();
    }
}