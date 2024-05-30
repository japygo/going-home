package com.project.goinghome.common.error;

import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@Builder
@RequiredArgsConstructor
public class ErrorResponse {
    private final int code;
    private final String message;

    public static ErrorResponse of(HttpStatus status, String message) {
        return ErrorResponse.builder()
                .code(status.value())
                .message(message)
                .build();
    }
}
