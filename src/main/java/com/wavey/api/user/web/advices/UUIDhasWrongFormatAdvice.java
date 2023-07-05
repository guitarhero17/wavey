package com.wavey.api.user.web.advices;

import com.wavey.api.user.exceptions.UUIDhasWrongFormatException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.HashMap;
import java.util.Map;

@ControllerAdvice
public class UUIDhasWrongFormatAdvice {

	@ResponseBody
	@ExceptionHandler(UUIDhasWrongFormatException.class)
	ResponseEntity<Map<String, String>> uuidHasWrongFormatHandler(UUIDhasWrongFormatException ex) {
		Map<String, String> responseBody = new HashMap<>();
		responseBody.put("error", ex.getMessage());
		return ResponseEntity.badRequest().body(responseBody);
	}
}
