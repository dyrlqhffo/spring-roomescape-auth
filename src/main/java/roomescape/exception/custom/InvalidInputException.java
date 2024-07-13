package roomescape.exception.custom;

import roomescape.exception.ErrorCode;

public class InvalidInputException extends RuntimeException{

    private final ErrorCode errorCode;

    public InvalidInputException(ErrorCode errorCode, String message) {
        super(message);
        this.errorCode = errorCode;
    }

    public ErrorCode getErrorCode() {
        return errorCode;
    }
}
