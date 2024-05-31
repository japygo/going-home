package com.project.goinghome.common.util.exception;

public class InvalidTypeException extends RuntimeException {

    public InvalidTypeException() {
    }

    public InvalidTypeException(String message) {
        super(message);
    }

    public InvalidTypeException(String message, Throwable cause) {
        super(message, cause);
    }

    public InvalidTypeException(Throwable cause) {
        super(cause);
    }

    public <T extends Enum<T>> InvalidTypeException(String name, Class<T> clazz) {
        this(name + " 은/는 " + clazz.getSimpleName() + " 타입이 아닙니다.");
    }
}
