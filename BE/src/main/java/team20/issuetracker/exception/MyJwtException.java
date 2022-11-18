package team20.issuetracker.exception;

import org.springframework.http.HttpStatus;

import lombok.Getter;

@Getter
public class MyJwtException extends RuntimeException {

    private final String errorMessage;
    private final HttpStatus httpStatus;

    public MyJwtException(String errorMessage, HttpStatus httpStatus) {
        this.errorMessage = errorMessage;
        this.httpStatus = httpStatus;
    }

    @Override
    public String getMessage() {
        return errorMessage;
    }
}
