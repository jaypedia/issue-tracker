package team20.issuetracker.config;

import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.data.domain.AuditorAware;

import java.util.Optional;

@TestConfiguration
public class AuditingConfig implements AuditorAware<String> {

    @Override
    public Optional<String> getCurrentAuditor() {
        return Optional.of("oauthId");
    }
}
