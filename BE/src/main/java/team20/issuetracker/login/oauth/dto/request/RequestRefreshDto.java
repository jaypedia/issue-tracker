package team20.issuetracker.login.oauth.dto.request;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Getter
public class RequestRefreshDto {

    private String id;
    private String refreshToken;

    @Builder
    public RequestRefreshDto(String id, String refreshToken) {
        this.id = id;
        this.refreshToken = refreshToken;
    }
}
