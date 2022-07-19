package team20.issuetracker.exceptionhandler;

import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.util.UriBuilder;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

import team20.issuetracker.exception.MyJwtException;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(MyJwtException.class)
    public ResponseEntity<ErrorResponse> expiredJwtException(MyJwtException myJwtException) {

        return new ResponseEntity<>(ErrorResponse.create(myJwtException.getErrorMessage()), myJwtException.getHttpStatus());
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, String>> labelValidException(MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult()
                .getAllErrors()
                .forEach(c -> errors.put(((FieldError) c)
                        .getField(), c.getDefaultMessage()));

        return ResponseEntity.badRequest().body(errors);
    }
}
