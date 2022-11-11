package team20.issuetracker.config;

import io.jsonwebtoken.Claims;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import team20.issuetracker.login.jwt.JwtTokenProvider;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Component
public class TestLoginInterceptor implements HandlerInterceptor {

    private final JwtTokenProvider jwtTokenProvider;

    @Value(value = "${jwt.secretKey}")
    private String key;

    public TestLoginInterceptor(JwtTokenProvider jwtTokenProvider) {
        this.jwtTokenProvider = jwtTokenProvider;
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        String jwtAccessToken = request.getHeader(HttpHeaders.AUTHORIZATION);

        if (isPreFlightRequest(request)) return true;

        if (jwtAccessToken != null && jwtAccessToken.equals("Test Access Token")) {
            return true;
        }

        response.sendRedirect("https://issuetracker.r-e.kr/login");
        return false;
    }

    private static boolean isPreFlightRequest(HttpServletRequest request) {
        return HttpMethod.OPTIONS.matches(request.getMethod());
    }
}
