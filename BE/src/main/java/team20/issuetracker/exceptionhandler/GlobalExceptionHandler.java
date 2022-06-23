package team20.issuetracker.exceptionhandler;

import io.jsonwebtoken.JwtException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(JwtException.class)
    public ErrorResponse JwtExceptionHandler() {
        String message = "유효하지 않은 Access Token 입니다.";

        return new ErrorResponse(message);
    }
}
