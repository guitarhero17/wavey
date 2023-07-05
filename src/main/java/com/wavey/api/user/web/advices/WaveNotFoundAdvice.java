package com.wavey.api.user.web.advices;

import com.wavey.api.user.exceptions.WaveNotFoundException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

@ControllerAdvice
public class WaveNotFoundAdvice {

    @ResponseBody
    @ExceptionHandler(WaveNotFoundException.class)
    ResponseEntity<?> waveNotFoundHandler() {
        return ResponseEntity.notFound().build();
    }
}
