package team20.issuetracker.login.oauth.dto.request;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Getter;

@Getter
public class RequestMaintainDto {

    private String refreshToken;

    @Builder
    @JsonCreator
    public RequestMaintainDto(
            @JsonProperty("refreshToken") String refreshToken) {
        this.refreshToken = refreshToken;
    }
}
