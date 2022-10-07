package team20.issuetracker.service.dto.request;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class RequestUpdateIssueContentDto {

    @NotEmpty(message = "Issue 의 본문은 공백일 수 없습니다.")
    @Size(max = 800, message = "Issue 의 본문은 800글자를 넘을 수 없습니다.")
    private String content;

    @JsonCreator
    public RequestUpdateIssueContentDto(
        @JsonProperty("content") String content) {

        this.content = content;
    }
}
