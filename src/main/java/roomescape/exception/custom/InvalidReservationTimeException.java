package roomescape.exception.custom;

import roomescape.exception.ErrorCode;

public class InvalidReservationTimeException extends RuntimeException {

    private final ErrorCode errorCode;

    public InvalidReservationTimeException(ErrorCode errorCode, String message) {
        super(message);
        this.errorCode = errorCode;
    }

    public ErrorCode getErrorCode() {
        return errorCode;
    }
}
