package com.wavey.api.user.web.advices;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import com.wavey.api.user.exceptions.UserNotFoundException;

@ControllerAdvice
public class UserNotFoundAdvice {

	@ResponseBody
	@ExceptionHandler(UserNotFoundException.class)
	ResponseEntity<?> userNotFoundHandler() {
		return ResponseEntity.notFound().build();
	}
}
