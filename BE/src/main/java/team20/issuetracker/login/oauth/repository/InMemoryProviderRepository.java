package team20.issuetracker.login.oauth.repository;

import team20.issuetracker.login.oauth.OauthProvider;

public class InMemoryProviderRepository {

    private final OauthProvider provider;

    public InMemoryProviderRepository(OauthProvider provider) {
        this.provider = provider;
    }

    public OauthProvider getProvider() {
        return provider;
    }
}
