package roomescape.exception;

import org.springframework.http.HttpStatus;

public enum ErrorCode {

    THEME_NOT_FOUND(HttpStatus.NOT_FOUND),

    INVALID_RESERVATION_TIME(HttpStatus.BAD_REQUEST),

    USER_NOT_FOUND(HttpStatus.NOT_FOUND),

    UNAUTHORIZED_USER(HttpStatus.UNAUTHORIZED);

    private final HttpStatus status;
    ErrorCode(HttpStatus status) {
        this.status = status;
    }

    public HttpStatus getStatus() {
        return status;
    }
}
