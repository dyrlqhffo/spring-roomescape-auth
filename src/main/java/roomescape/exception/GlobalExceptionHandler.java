package roomescape.exception;

import com.fasterxml.jackson.databind.exc.InvalidFormatException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import roomescape.exception.custom.AuthorizationException;
import roomescape.exception.custom.InvalidInputException;
import roomescape.exception.custom.InvalidReservationTimeException;
import roomescape.exception.custom.ThemeNotFoundException;

import java.util.*;
import java.util.stream.Collectors;


@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, List<String>>> handleValidationExceptions(MethodArgumentNotValidException ex) {
        List<String> errors = ex.getBindingResult().getFieldErrors()
                .stream()
                .map(FieldError::getDefaultMessage)
                .collect(Collectors.toList());
        return new ResponseEntity<>(getErrorsMap(errors), new HttpHeaders(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(Exception.class)
    public final ResponseEntity<Map<String, List<String>>> handleGeneralExceptions(Exception ex) {
        List<String> errors = Collections.singletonList(ex.getMessage());
        return new ResponseEntity<>(getErrorsMap(errors), new HttpHeaders(), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(RuntimeException.class)
    public final ResponseEntity<Map<String, List<String>>> handleRuntimeExceptions(RuntimeException ex) {
        List<String> errors = Collections.singletonList(ex.getMessage());
        return new ResponseEntity<>(getErrorsMap(errors), new HttpHeaders(), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<Map<String, List<String>>> handleHttpMessageNotReadableException(HttpMessageNotReadableException ex) {
        Throwable cause = ex.getCause();
        if (cause instanceof InvalidFormatException) {
            return handleInvalidFormatExceptions((InvalidFormatException) cause);
        }
        List<String> errors = Collections.singletonList("잘못된 입력입니다. 다시 확인해주세요.");
        return new ResponseEntity<>(getErrorsMap(errors), new HttpHeaders(), HttpStatus.BAD_REQUEST);
    }

    public ResponseEntity<Map<String, List<String>>> handleInvalidFormatExceptions(InvalidFormatException ex) {
        String fieldName = ex.getPath().stream()
                .map(reference -> reference.getFieldName())
                .findFirst()
                .orElse("error!!!!!!");

        String errorMsg = String.format("테마 혹은 시간을 다시 확인해주세요.");
        List<String> errors = Collections.singletonList(errorMsg);
        return new ResponseEntity<>(getErrorsMap(errors), new HttpHeaders(), HttpStatus.BAD_REQUEST);
    }

    private Map<String, List<String>> getErrorsMap(List<String> errors) {
        Map<String, List<String>> errorResponse = new HashMap<>();
        errorResponse.put("errors", errors);
        return errorResponse;
    }

    @ExceptionHandler(ThemeNotFoundException.class)
    public ResponseEntity<ErrorCodeResponse> handlerThemeNotFoundException(ThemeNotFoundException ex) {
        ErrorCodeResponse response = new ErrorCodeResponse(ex.getErrorCode(), ex.getMessage());
        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(InvalidReservationTimeException.class)
    public ResponseEntity<ErrorCodeResponse> handlerCustomErrorException(ErrorCodeResponse e) {
        ErrorCodeResponse errorMsg = new ErrorCodeResponse(e.getErrorCode(), e.getMessage());
        return new ResponseEntity<>(errorMsg, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(AuthorizationException.class)
    public ResponseEntity<ErrorCodeResponse> handlerAuthorizationException(AuthorizationException ex) {
        ErrorCodeResponse response = new ErrorCodeResponse(ex.getErrorCode(), ex.getMessage());
        return new ResponseEntity<>(response, HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler(EmptyResultDataAccessException.class)
    public ResponseEntity<ErrorCodeResponse> handlerEmptyResultDataAccessException(EmptyResultDataAccessException exception) {
        ErrorCodeResponse response = new ErrorCodeResponse(ErrorCode.USER_NOT_FOUND, "존재하지 않는 회원입니다. 다시 입력해주세요.");
        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(InvalidInputException.class)
    public ResponseEntity<ErrorCodeResponse> handlerInvalidInputException(InvalidInputException ex) {
        ErrorCodeResponse response = new ErrorCodeResponse(ErrorCode.INVALID_INPUT, ex.getMessage());
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }
}
