package pl.mbcode.nn.bank.exception;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.NONE)
class ErrorDtoFactory {
    private static final String UNEXPECTED_EXCEPTION_LABEL = "unexpected_exception";

    static ErrorDto create(SingleReasonException exception) {
        return ErrorDto.builder()
                .status(exception.statusCode())
                .code(exception.errorCode())
                .message(exception.getMessage())
                .build();
    }

    static ErrorDto create(Exception exception) {
        return ErrorDto.builder()
                .status(500)
                .code(UNEXPECTED_EXCEPTION_LABEL)
                .message(exception.getMessage())
                .build();
    }
}
