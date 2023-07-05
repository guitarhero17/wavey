package com.wavey.api.user.web.advices;

import com.wavey.api.user.errors.CustomFieldError;
import com.wavey.api.user.exceptions.FieldCanNotBePatchedException;
import com.wavey.api.user.exceptions.UserAlreadyExistsException;
import com.wavey.api.user.web.errors.FieldErrorResponse;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@ControllerAdvice
public class FieldCanNotBePatchedAdvice extends ResponseEntityExceptionHandler {

    @ResponseBody
    @ExceptionHandler(FieldCanNotBePatchedException.class)
    ResponseEntity<Map<String, String>> fieldCanNotBePatchedHandler(FieldCanNotBePatchedException ex) {
        Map<String, String> responseBody = new HashMap<>();
        responseBody.put("error", ex.getMessage());
        return new ResponseEntity<>(responseBody, HttpStatus.FORBIDDEN);
    }
}
