package team20.issuetracker.login.interceptor;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.web.servlet.HandlerInterceptor;
import team20.issuetracker.exception.MyJwtException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class TokenInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        String jwtAccessToken = request.getHeader(HttpHeaders.AUTHORIZATION);
        String[] tokenSplit = jwtAccessToken.split(" ");

        if (tokenSplit.length != 2) {
            throw new MyJwtException("토큰이 존재하지 않습니다.", HttpStatus.BAD_REQUEST);
        }

        return true;
    }
}
