package team20.issuetracker.config;

import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.data.domain.AuditorAware;

import java.util.Optional;
import java.util.UUID;

@TestConfiguration
public class AuditingConfig implements AuditorAware<String> {

    @Override
    public Optional<String> getCurrentAuditor() {
        Optional<String> s = Optional.of(UUID.randomUUID().toString());
        return Optional.of(UUID.randomUUID().toString());
    }
}
