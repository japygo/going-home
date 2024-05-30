package com.project.goinghome.common.error;

import com.project.goinghome.common.error.exception.InternalServerErrorException;
import lombok.extern.slf4j.Slf4j;
import org.apache.coyote.BadRequestException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
public class ErrorHandler {

    @ExceptionHandler({
            BadRequestException.class,
            IllegalArgumentException.class,
    })
    public ResponseEntity<ErrorResponse> badRequest(BadRequestException e) {
        log.error("BadRequestException : {}", e.getMessage(), e);
        return ResponseEntity.badRequest()
                .body(ErrorResponse.of(HttpStatus.BAD_REQUEST, e.getMessage()));
    }

    @ExceptionHandler({
            Exception.class,
            InternalServerErrorException.class,
    })
    public ResponseEntity<ErrorResponse> exception(Exception e) {
        log.error("그 외 Exception : {}", e.getMessage(), e);
        return ResponseEntity.internalServerError()
                .body(ErrorResponse.of(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage()));
    }
}
