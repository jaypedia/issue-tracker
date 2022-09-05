package team20.issuetracker.login.oauth.config;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.HashMap;
import java.util.Map;

import lombok.Getter;
import org.springframework.boot.context.properties.ConstructorBinding;

@Getter
@ConstructorBinding
@ConfigurationProperties(prefix = "oauth2")
public class OauthProperties {

    private final Map<String, User> user = new HashMap<>();
    private final Map<String, Provider> provider = new HashMap<>();

    @Getter
    @RequiredArgsConstructor
    public final static class User {
        private final String clientId;
        private final String clientSecret;
        private final String redirectUri;
    }

    @Getter
    @RequiredArgsConstructor
    public final static class Provider {
        private final String tokenUri;
        private final String userInfoUri;
        private final String userNameAttribute;
        private final String loginUri;
    }
}
