package team20.issuetracker.service.dto.request;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class RequestUpdateIssueRelatedDto {

    private Long id;

    @JsonCreator
    public RequestUpdateIssueRelatedDto(
        @JsonProperty("id") Long id) {

        this.id = id;
    }
}
