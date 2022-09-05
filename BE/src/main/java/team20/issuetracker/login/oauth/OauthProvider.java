package team20.issuetracker.login.oauth;

import team20.issuetracker.login.oauth.config.OauthProperties;

import lombok.Getter;

@Getter
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
}
