package team20.issuetracker.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.AuditorAware;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.util.Optional;

import javax.servlet.http.HttpServletRequest;

import io.jsonwebtoken.Jwts;

@Configuration
public class AuditingConfig implements AuditorAware<String> {

    @Override
    public Optional<String> getCurrentAuditor() {
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest();
        String jwtToken = request.getHeader("Authorization").split(" ")[1];
        String subject = Jwts.parser().setSigningKey("").parseClaimsJws(jwtToken).getBody().getSubject();
        return Optional.of(subject);
    }
}
