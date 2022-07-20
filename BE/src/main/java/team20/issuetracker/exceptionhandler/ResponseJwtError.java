package team20.issuetracker.exceptionhandler;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class ResponseJwtError {

    private final String errorMessage;

    static ResponseJwtError create(String errorMessage) {
        return new ResponseJwtError(errorMessage);
    }
}
