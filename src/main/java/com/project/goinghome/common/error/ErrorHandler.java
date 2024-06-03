package com.project.goinghome.common.error;

import com.project.goinghome.common.error.exception.BadRequestException;
import com.project.goinghome.common.error.exception.InternalServerErrorException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@RestControllerAdvice
public class ErrorHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler({
            BadRequestException.class,
            IllegalArgumentException.class
    })
    public ResponseEntity<ErrorResponse> badRequest(Exception e) {
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

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
                                                                  HttpHeaders headers, HttpStatusCode status,
                                                                  WebRequest request) {
        BindingResult bindingResult = ex.getBindingResult();
        List<String> messages = new ArrayList<>();

        for (FieldError fieldError : bindingResult.getFieldErrors()) {
            messages.add(fieldError.getDefaultMessage() + " (" + fieldError.getRejectedValue() + ")");
        }

        log.error("MethodArgumentNotValidException : {}", messages, ex);

        return ResponseEntity.badRequest()
                .body(ErrorResponse.of(HttpStatus.BAD_REQUEST, messages));
    }
}
