package roomescape.exception.custom;

import roomescape.exception.ErrorCode;

public class CookieNotFoundException extends RuntimeException {

    private final ErrorCode errorCode;

    public CookieNotFoundException(ErrorCode errorCode, String message){
        super(message);
        this.errorCode = errorCode;
    }

    public ErrorCode getErrorCode() {
        return errorCode;
    }
}
