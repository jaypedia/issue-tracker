package team20.issuetracker.service.dto.request;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class RequestUpdateIssueTitleWithContentDto {

    @NotEmpty
    @Size(max = 50, message = "Issue 의 제목은 50글자를 넘을 수 없습니다.")
    private String title;

    @NotEmpty(message = "Issue 의 본문은 공백일 수 없습니다.")
    @Size(max = 800, message = "Issue 의 본문은 800글자를 넘을 수 없습니다.")
    private String content;
}
