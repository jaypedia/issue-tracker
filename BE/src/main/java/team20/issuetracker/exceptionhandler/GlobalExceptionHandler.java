package team20.issuetracker.exceptionhandler;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

import team20.issuetracker.exception.MyJwtException;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(MyJwtException.class)
    public ResponseEntity<ResponseJwtError> expiredJwtException(MyJwtException myJwtException) {

        return new ResponseEntity<>(ResponseJwtError.create(myJwtException.getErrorMessage()), myJwtException.getHttpStatus());
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ResponseErrorMap> mapValidException(MethodArgumentNotValidException exception) {

        Map<String, String> responseValidationErrors = new HashMap<>();

        for (FieldError error : exception.getFieldErrors()) {
            log.error("errorFiledName : {}", error.getField());
            log.error("errorMessage : {}", error.getDefaultMessage());

            ResponseDtoValidationError response = ResponseDtoValidationError.of(error.getField(), error.getDefaultMessage());

            responseValidationErrors.put(response.getErrorFiledName(), response.getErrorMessage());
        }

        ResponseErrorMap responseError = ResponseErrorMap.from(responseValidationErrors);

        return new ResponseEntity<>(responseError, HttpStatus.BAD_REQUEST);
    }
}
