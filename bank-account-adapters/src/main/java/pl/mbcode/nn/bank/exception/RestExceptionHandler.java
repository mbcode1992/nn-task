package pl.mbcode.nn.bank.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
@SuppressWarnings("unused")
public class RestExceptionHandler {

    @ExceptionHandler(SingleReasonException.class)
    ResponseEntity<ErrorDto> handle(SingleReasonException exception) {
        log.error("RestResponseException occurred '{}'.", exception.getMessage(), exception);
        return ResponseEntity
                .status(exception.statusCode())
                .body(ErrorDtoFactory.create(exception));
    }

    @ExceptionHandler(Exception.class)
    ResponseEntity<ErrorDto> handle(Exception exception) {
        log.error("Exception occurred '{}'.", exception.getMessage(), exception);
        return ResponseEntity
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(ErrorDtoFactory.create(exception));
    }

}
