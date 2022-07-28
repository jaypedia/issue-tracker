package team20.issuetracker.service.dto.request;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

@Getter
@NoArgsConstructor
public class RequestUpdateIssueTitleDto {

    @NotEmpty
    @Size(max = 50, message = "Issue 의 제목은 50글자를 넘을 수 없습니다.")
    private String title;

    @JsonCreator
    public RequestUpdateIssueTitleDto(
            @JsonProperty("title") String title) {

        this.title = title;
    }
}
