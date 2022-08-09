package team20.issuetracker.service.dto.request;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import team20.issuetracker.domain.comment.Comment;
import team20.issuetracker.domain.issue.Issue;

@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class RequestCommentDto {

    private Long issueId;
    private String content;

    public static RequestCommentDto of(Long issueId, String content) {
        return new RequestCommentDto(issueId, content);
    }

    public static Comment toEntity(String memberName, String profileImageUrl, String content, Issue savedIssue) {
        return Comment.of(memberName, profileImageUrl, content, savedIssue);
    }
}
