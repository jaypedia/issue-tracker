package team20.issuetracker.service.dto.response;

import java.time.LocalDate;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import team20.issuetracker.domain.comment.Comment;

@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class ResponseCommentDto {

    private Long id;
    private String author;
    private String image;
    private String content;
    private LocalDate createdAt;

    public static ResponseCommentDto of(Long id, String author, String image, String content, LocalDate createdAt) {
        return new ResponseCommentDto(id, author, image, content, createdAt);
    }

    public static ResponseCommentDto from(Comment comment) {
        return new ResponseCommentDto(
                comment.getId(),
                comment.getAuthor(),
                comment.getImage(),
                comment.getContent(),
                comment.getCreatedAt()
        );
    }
}
