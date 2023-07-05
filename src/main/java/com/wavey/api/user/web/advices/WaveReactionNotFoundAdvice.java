package com.wavey.api.user.web.advices;

import com.wavey.api.user.exceptions.WaveReactionNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
public class WaveReactionNotFoundAdvice {

    @ResponseBody
    @ExceptionHandler(WaveReactionNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    ResponseEntity<?> waveReactionNotFoundHandler() {
        return ResponseEntity.notFound().build();
    }
}
