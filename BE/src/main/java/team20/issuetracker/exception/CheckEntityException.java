package team20.issuetracker.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public class CheckEntityException extends RuntimeException {

    private final String errorMessage;
    private final HttpStatus httpStatus;

    @Override
    public String getMessage() {
        return errorMessage;
    }
}
