package com.project.goinghome.common.api.exception;

public class ExternalApiException extends RuntimeException {

    public ExternalApiException() {
    }

    public ExternalApiException(String message) {
        super(message);
    }

    public ExternalApiException(String message, Throwable cause) {
        super(message, cause);
    }

    public ExternalApiException(Throwable cause) {
        super(cause);
    }
}
