package team20.issuetracker.service.dto.request;

import java.util.List;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

@Getter
@Setter
@NoArgsConstructor
public class RequestSaveIssueDto {

    @NotEmpty
    @Size(max = 50, message = "Issue 의 제목은 50글자를 넘을 수 없습니다.")
    private String title;

    @NotEmpty
    @Size(max = 800, message = "Issue 의 본문은 800글자를 넘을 수 없습니다.")
    private String content;

    private List<Long> assigneeIds;
    private List<Long> labelIds;
    private Long milestoneId;
}
