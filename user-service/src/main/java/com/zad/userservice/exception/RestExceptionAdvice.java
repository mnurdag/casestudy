package com.zad.userservice.exception;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class RestExceptionAdvice extends ResponseEntityExceptionHandler {

    @ExceptionHandler({ DataIntegrityViolationException.class })
    public ResponseEntity<Object> handleAccessDeniedException(
            Exception ex, WebRequest request) {
        String exceptionMessage = "";
        if(ex.getMessage().isBlank())
            exceptionMessage = ex.getLocalizedMessage();
        else
            exceptionMessage = ex.getMessage();
        return new ResponseEntity<Object>(exceptionMessage, new HttpHeaders(), HttpStatus.CONFLICT);
    }

    @ExceptionHandler({ ApiException.class })
    public ResponseEntity<Object> handleApiException(
            ApiException ex, WebRequest request) {
            return new ResponseEntity<Object>(ex.getCompleteMessage(), new HttpHeaders(), HttpStatus.BAD_REQUEST);
    }

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatusCode status, WebRequest request) {
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getAllErrors().forEach(error -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            errors.put(fieldName, errorMessage);
        });
        return new ResponseEntity<>(errors, HttpStatus.BAD_REQUEST);
    }
}
