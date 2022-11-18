package team20.issuetracker.exceptionhandler;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class ResponseErrorMassage {

    private final String errorMessage;

    static ResponseErrorMassage create(String errorMessage) {
        return new ResponseErrorMassage(errorMessage);
    }
}
