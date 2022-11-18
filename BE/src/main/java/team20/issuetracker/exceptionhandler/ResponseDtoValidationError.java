package team20.issuetracker.exceptionhandler;

import lombok.Getter;

@Getter
public class ResponseDtoValidationError {

    private final String errorFiledName;
    private final String errorMessage;

    private ResponseDtoValidationError(String errorFiledName, String errorMessage) {
        this.errorFiledName = errorFiledName;
        this.errorMessage = errorMessage;
    }

    static ResponseDtoValidationError of(String errorFiledName, String errorMessage) {
        return new ResponseDtoValidationError(errorFiledName, errorMessage);
    }
}
