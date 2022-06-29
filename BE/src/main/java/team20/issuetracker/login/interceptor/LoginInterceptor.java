package team20.issuetracker.login.interceptor;

import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import team20.issuetracker.login.jwt.JwtTokenProvider;

@Component
public class LoginInterceptor implements HandlerInterceptor {

    private final JwtTokenProvider jwtTokenProvider;

    public LoginInterceptor(JwtTokenProvider jwtTokenProvider) {
        this.jwtTokenProvider = jwtTokenProvider;
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        String jwtAccessToken = request.getHeader(HttpHeaders.AUTHORIZATION);

        if (jwtAccessToken != null) {
            return jwtTokenProvider.validateToken(jwtAccessToken);
        }

        response.sendRedirect("/index.html");
        return false;
    }
}
