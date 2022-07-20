package team20.issuetracker.service.dto.request;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class RequestSaveIssueDto {

    private String title;
    private String content;
    private String author;
    private List<Long> assigneeIds;
    private List<Long> labelIds;
    private Long milestoneId;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createdAt;
}
