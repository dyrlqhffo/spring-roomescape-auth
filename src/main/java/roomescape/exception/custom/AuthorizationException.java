package roomescape.exception.custom;

import roomescape.exception.ErrorCode;

public class AuthorizationException extends RuntimeException{

    private final ErrorCode errorCode;

    public AuthorizationException(ErrorCode errorCode, String message) {
        super(message);
        this.errorCode = errorCode;
    }

    public ErrorCode getErrorCode() {
        return errorCode;
    }
}
