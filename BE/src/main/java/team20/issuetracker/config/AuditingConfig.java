package team20.issuetracker.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.AuditorAware;
import org.springframework.http.HttpHeaders;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.util.Optional;

import javax.servlet.http.HttpServletRequest;

import team20.issuetracker.util.JwtUtils;

@Configuration
public class AuditingConfig implements AuditorAware<String> {

    @Value(value = "${jwt.secretKey}")
    private String key;

    @Override
    public Optional<String> getCurrentAuditor() {
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest();
        String jwtToken = JwtUtils.tokenExtraction(request, HttpHeaders.AUTHORIZATION);
        String id = JwtUtils.decodingToken(jwtToken, key).getId();
        return Optional.of(id);
    }
}
