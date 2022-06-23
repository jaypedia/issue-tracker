package team20.issuetracker.login.interceptor;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.web.servlet.HandlerInterceptor;
import team20.issuetracker.login.jwt.JwtTokenProvider;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Slf4j
public class LoginInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        log.info("LoginInterceptor preHandle");

        String jwtAccessToken = request.getHeader(HttpHeaders.AUTHORIZATION);
        JwtTokenProvider jwtTokenProvider = new JwtTokenProvider();

        if (jwtAccessToken.length() != 0) {
            return jwtTokenProvider.validateToken(jwtAccessToken);
        }

        response.sendRedirect("/login");
        return false;
    }
}
