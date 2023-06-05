package pl.mbcode.nn.bank.exception;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Builder
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
public class ErrorDto {

    private final int status;
    private final String code;
    private final LocalDateTime timestamp = LocalDateTime.now();
    private final String message;

}
