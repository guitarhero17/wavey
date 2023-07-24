package com.wavey.api.web.advices;

import com.wavey.api.exceptions.WaveNotFoundException;
import com.wavey.api.exceptions.WaveReactionNotFoundException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import com.wavey.api.exceptions.UserNotFoundException;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ResourceNotFoundAdvice {

	@ExceptionHandler({UserNotFoundException.class, WaveNotFoundException.class, WaveReactionNotFoundException.class})
	ResponseEntity<?> userNotFoundHandler() {
		return ResponseEntity.notFound().build();
	}
}
