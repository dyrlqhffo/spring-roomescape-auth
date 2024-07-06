package roomescape.exception;

import org.springframework.http.HttpStatus;

public enum ErrorCode {

    THEME_NOT_FOUND(HttpStatus.NOT_FOUND),

    INVALID_RESERVATION_TIME(HttpStatus.BAD_REQUEST);

    private final HttpStatus status;
    ErrorCode(HttpStatus status) {
        this.status = status;
    }

    public HttpStatus getStatus() {
        return status;
    }
}
