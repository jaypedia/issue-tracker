package team20.issuetracker.login.interceptor;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;
import org.springframework.web.cors.reactive.CorsUtils;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import io.jsonwebtoken.Claims;
import team20.issuetracker.login.jwt.JwtTokenProvider;
import team20.issuetracker.util.JwtUtils;

@Component
public class LoginInterceptor implements HandlerInterceptor {

    private final JwtTokenProvider jwtTokenProvider;

    @Value(value = "${jwt.secretKey}")
    private String key;

    public LoginInterceptor(JwtTokenProvider jwtTokenProvider) {
        this.jwtTokenProvider = jwtTokenProvider;
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        String jwtAccessToken = request.getHeader(HttpHeaders.AUTHORIZATION);

        if (jwtAccessToken != null) {
            if (isPreFlightRequest((ServerHttpRequest) request)) return true;
            String token = jwtTokenProvider.validateTokeType(jwtAccessToken);
            jwtTokenProvider.validateToken(token);

            Claims claims = JwtUtils.decodingToken(token, key);
            String oauthId = claims.getId();
            request.setAttribute("oauthId", oauthId);
            return true;
        }

        response.sendRedirect("/index.html");
        return false;
    }

    private static boolean isPreFlightRequest(ServerHttpRequest request) {
        if (CorsUtils.isPreFlightRequest(request)) {
            return true;
        }
        return false;
    }
}
