package team20.issuetracker.exceptionhandler;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import team20.issuetracker.exception.MyJwtException;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(MyJwtException.class)
    public ResponseEntity<ErrorResponse> expiredJwtException(MyJwtException myJwtException) {

        return new ResponseEntity<>(ErrorResponse.create(myJwtException.getErrorMessage()), myJwtException.getHttpStatus());
    }
}
