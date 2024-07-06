package roomescape.exception.custom;

import roomescape.exception.ErrorCode;

public class ThemeNotFoundException extends RuntimeException {

    private final ErrorCode errorCode;

    public ThemeNotFoundException(ErrorCode errorCode, String message) {
        super(message);
        this.errorCode = errorCode;
    }

    public ErrorCode getErrorCode() {
        return errorCode;
    }
}
