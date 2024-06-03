package com.project.goinghome.common.error;

import lombok.Getter;
import org.springframework.http.HttpStatus;

import java.util.List;

@Getter
public class ErrorResponse {
    private final int code;
    private final List<String> messages;

    public ErrorResponse(int code, List<String> messages) {
        this.code = code;
        this.messages = messages;
    }

    public ErrorResponse(int code, String... message) {
        this(code, List.of(message));
    }

    public static ErrorResponse of(HttpStatus status, List<String> messages) {
        return new ErrorResponse(status.value(), messages);
    }

    public static ErrorResponse of(HttpStatus status, String... message) {
        return new ErrorResponse(status.value(), message);
    }
}
