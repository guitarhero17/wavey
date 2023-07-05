package com.wavey.api.user.web.advices;

import com.wavey.api.user.errors.CustomFieldError;
import com.wavey.api.user.web.errors.FieldErrorResponse;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.*;

@ControllerAdvice
public class InvalidNewUserDataAdvice extends ResponseEntityExceptionHandler {

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatusCode status, WebRequest request) {
        FieldErrorResponse fieldErrorResponse = new FieldErrorResponse();
        List<CustomFieldError> fieldErrors = new ArrayList<>();
        ex.getBindingResult().getAllErrors().forEach(e -> {
            CustomFieldError fieldError = new CustomFieldError();
            fieldError.setField(((FieldError) e).getField());
            fieldError.setMessage(e.getDefaultMessage());
            fieldErrors.add(fieldError);
        });
        fieldErrorResponse.setFieldErrors(fieldErrors);
        return ResponseEntity.badRequest().body(fieldErrorResponse);
    }
}
