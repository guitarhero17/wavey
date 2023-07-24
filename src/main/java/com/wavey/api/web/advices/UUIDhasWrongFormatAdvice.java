package com.wavey.api.web.advices;

import com.wavey.api.exceptions.UUIDhasWrongFormatException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class UUIDhasWrongFormatAdvice {

	@ExceptionHandler(UUIDhasWrongFormatException.class)
	ResponseEntity<Map<String, String>> uuidHasWrongFormatHandler(UUIDhasWrongFormatException ex) {
		Map<String, String> responseBody = new HashMap<>();
		responseBody.put("error", ex.getMessage());
		return ResponseEntity.badRequest().body(responseBody);
	}
}
