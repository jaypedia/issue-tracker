package team20.issuetracker.domain.issue;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@NoArgsConstructor
public class SaveIssueDto {

    private String title;
    private String content;
    private String author;
    private List<Long> assigneeIds;
    private List<Long> labelIds;
    private Long milestoneId;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createdAt;
}
