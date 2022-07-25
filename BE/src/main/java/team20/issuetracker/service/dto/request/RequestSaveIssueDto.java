package team20.issuetracker.service.dto.request;

import java.util.List;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class RequestSaveIssueDto {

    private String title;
    private String content;
    private List<Long> assigneeIds;
    private List<Long> labelIds;
    private Long milestoneId;
}
