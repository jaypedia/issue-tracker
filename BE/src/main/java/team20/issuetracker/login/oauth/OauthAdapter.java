package team20.issuetracker.login.oauth;

import java.util.HashMap;
import java.util.Map;

public class OauthAdapter {

    private OauthAdapter() {}

    public static Map<String, OauthProvider> getOauthProviders(OauthProperties oauthProperties) {
        Map<String, OauthProvider> oauthProvider = new HashMap<>();

        oauthProperties.getUser()
                .forEach((key, value) -> oauthProvider.put(key, new OauthProvider(value, oauthProperties.getProvider().get(key))));
        return oauthProvider;
    }
}
