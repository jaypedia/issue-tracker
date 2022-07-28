package team20.issuetracker.service.dto.request;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class RequestUpdateIssueTitleDto {

    private String title;

    @JsonCreator
    public RequestUpdateIssueTitleDto(
            @JsonProperty("title") String title) {

        this.title = title;
    }
}
