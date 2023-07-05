package com.wavey.api.user.web.advices;

import com.wavey.api.user.exceptions.WaveAlreadyExistsException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.HashMap;
import java.util.Map;

@ControllerAdvice
public class WaveAlreadyExistsAdvice {

    @ResponseBody
    @ExceptionHandler(WaveAlreadyExistsException.class)
    ResponseEntity<Map<String, String>> waveNotFoundHandler(WaveAlreadyExistsException ex) {
        Map<String, String> responseBody = new HashMap<>();
        responseBody.put("error", ex.getMessage());
        return new ResponseEntity<>(responseBody, HttpStatus.CONFLICT);
    }
}
