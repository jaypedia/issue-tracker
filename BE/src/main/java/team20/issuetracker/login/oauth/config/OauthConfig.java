package team20.issuetracker.login.oauth.config;

import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import lombok.RequiredArgsConstructor;
import team20.issuetracker.login.oauth.OauthProvider;
import team20.issuetracker.login.oauth.repository.InMemoryProviderRepository;

@Configuration
@EnableConfigurationProperties(OauthProperties.class)
@RequiredArgsConstructor
public class OauthConfig {

    private final OauthProperties properties;

    @Bean
    public InMemoryProviderRepository inMemoryProviderRepository() {
        OauthProvider provider = new OauthProvider(properties);

        return new InMemoryProviderRepository(provider);
    }
}
