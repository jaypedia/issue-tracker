package team20.issuetracker.login.oauth.dto.request;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Getter
public class RequestRefreshDto {

    private String oauthId;
    private String refreshToken;

    @Builder
    public RequestRefreshDto(String oauthId, String refreshToken) {
        this.oauthId = oauthId;
        this.refreshToken = refreshToken;
    }
}
