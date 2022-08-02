package team20.issuetracker.service.dto.request;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import team20.issuetracker.domain.comment.Comment;
import team20.issuetracker.domain.issue.Issue;

@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class RequestCommentDto {

    private Long issueId;
    private String content;


    public Comment toEntity(String memberName, String profileImageUrl, Issue savedIssue) {
        return Comment.of(memberName, profileImageUrl, content, savedIssue);
    }
}
