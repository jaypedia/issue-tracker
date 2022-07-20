package team20.issuetracker.exceptionhandler;

import lombok.Getter;

import java.util.Map;

@Getter
public class ResponseErrorMap {

    private final Map<String, String> validationErrors;

    private ResponseErrorMap(Map<String, String> validationErrors) {
        this.validationErrors = validationErrors;
    }

    public static ResponseErrorMap from(Map<String, String> validationErrors) {
        return new ResponseErrorMap(validationErrors);
    }
}
