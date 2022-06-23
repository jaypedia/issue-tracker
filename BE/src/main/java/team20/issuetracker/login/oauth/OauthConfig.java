package team20.issuetracker.login.oauth;

import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.Map;

import lombok.RequiredArgsConstructor;
import team20.issuetracker.login.oauth.repository.InMemoryProviderRepository;

@Configuration
@EnableConfigurationProperties(OauthProperties.class)
@RequiredArgsConstructor
public class OauthConfig {

    private final OauthProperties properties;

    @Bean
    public InMemoryProviderRepository inMemoryProviderRepository() {
        Map<String, OauthProvider> oauthProvider = new HashMap<>();

        properties.getUser()
                .forEach((key, value) -> oauthProvider.put(key, new OauthProvider(value, properties.getProvider().get(key))));

        OauthProvider provider = oauthProvider.get("github");

        return new InMemoryProviderRepository(provider);
    }
}
