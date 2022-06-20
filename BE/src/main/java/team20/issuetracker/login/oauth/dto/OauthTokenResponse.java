package team20.issuetracker.login.oauth.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Getter;

@Getter
public class OauthTokenResponse {

    @JsonProperty("access_token")
    private String accessToken;

    @JsonProperty("token_type")
    private String tokenType;

    private String scope;

    @Builder
    public OauthTokenResponse(String accessToken, String tokenType, String scope) {
        this.accessToken = accessToken;
        this.tokenType = tokenType;
        this.scope = scope;
    }
}
