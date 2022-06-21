package team20.issuetracker.login.oauth;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import lombok.extern.slf4j.Slf4j;
import team20.issuetracker.login.oauth.repository.InMemoryProviderRepository;

import java.util.HashMap;
import java.util.Map;

@Configuration
@EnableConfigurationProperties(OauthProperties.class)
@RequiredArgsConstructor
@Slf4j
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
