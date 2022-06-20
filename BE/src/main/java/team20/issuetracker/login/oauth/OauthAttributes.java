package team20.issuetracker.login.oauth;

import team20.issuetracker.login.oauth.dto.UserProfile;

import java.util.Arrays;
import java.util.Map;

public enum OauthAttributes {

    GITHUB("github") {

        @Override
        public UserProfile of(Map<String, Object> attributes) {
            return UserProfile.builder()
                    .oauthId(String.valueOf(attributes.get("id")))
                    .email((String) attributes.get("email"))
                    .name((String) attributes.get("name"))
                    .imageUrl((String) attributes.get("avatar_url"))
                    .build();
        }
    };

    private final String providerName;

    OauthAttributes(String name) {
        this.providerName = name;
    }

    public static UserProfile extract(String providerName, Map<String, Object> attributes) {
        return Arrays.stream(values())
                .filter(provider -> providerName.equals(provider.providerName))
                .findFirst()
                .orElseThrow(IllegalArgumentException::new)
                .of(attributes);
    }

    public abstract UserProfile of(Map<String, Object> attributes);
}
