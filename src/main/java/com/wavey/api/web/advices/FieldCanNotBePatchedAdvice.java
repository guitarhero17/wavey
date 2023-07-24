package com.wavey.api.web.advices;

import com.wavey.api.exceptions.FieldCanNotBePatchedException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class FieldCanNotBePatchedAdvice extends ResponseEntityExceptionHandler {

    @ExceptionHandler(FieldCanNotBePatchedException.class)
    ResponseEntity<Map<String, String>> fieldCanNotBePatchedHandler(FieldCanNotBePatchedException ex) {
        Map<String, String> responseBody = new HashMap<>();
        responseBody.put("error", ex.getMessage());
        return new ResponseEntity<>(responseBody, HttpStatus.FORBIDDEN);
    }
}
