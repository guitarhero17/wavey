package com.wavey.api.web.advices;

import com.wavey.api.errors.CustomFieldError;
import com.wavey.api.web.errors.FieldErrorResponse;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.*;

@RestControllerAdvice
public class InvalidNewUserDataAdvice extends ResponseEntityExceptionHandler {

    @Override
    @ResponseBody
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
