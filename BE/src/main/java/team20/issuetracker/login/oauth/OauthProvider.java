package team20.issuetracker.login.oauth;

import lombok.AllArgsConstructor;
import lombok.Getter;
import team20.issuetracker.login.oauth.config.OauthProperties;

@Getter
@AllArgsConstructor
public class OauthProvider {
    private final String clientId;
    private final String clientSecret;
    private final String redirectUrl;
    private final String tokenUrl;
    private final String userInfoUrl;
    private final String loginUri;

    public OauthProvider(OauthProperties properties) {
        this.clientId = properties.getClientId();
        this.clientSecret = properties.getClientSecret();
        this.redirectUrl = properties.getRedirectUri();
        this.tokenUrl = properties.getTokenUri();
        this.userInfoUrl = properties.getUserInfoUri();
        this.loginUri = properties.getLoginUri();
    }

    public static OauthProvider of(String clientId, String clientSecret, String redirectUrl, String tokenUrl, String userInfoUrl, String loginUri) {
        return new OauthProvider(clientId, clientSecret, redirectUrl, tokenUrl, userInfoUrl, loginUri);
    }
}
