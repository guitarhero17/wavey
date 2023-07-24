package com.wavey.api.web.advices;

import com.wavey.api.exceptions.WaveAlreadyExistsException;
import com.wavey.api.exceptions.UserAlreadyExistsException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class ResourceAlreadyExistsAdvice {

	@ExceptionHandler({UserAlreadyExistsException.class, WaveAlreadyExistsException.class})
	ResponseEntity<Map<String, String>> resourceAlreadyExistsHandler(RuntimeException ex) {
		Map<String, String> responseBody = new HashMap<>();
		responseBody.put("error", ex.getMessage());
		return new ResponseEntity<>(responseBody, HttpStatus.CONFLICT);
	}
}
