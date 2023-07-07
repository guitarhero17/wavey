package com.wavey.api.user.web.advices;

import com.wavey.api.user.exceptions.WaveNotFoundException;
import com.wavey.api.user.exceptions.WaveReactionNotFoundException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import com.wavey.api.user.exceptions.UserNotFoundException;

@ControllerAdvice
public class ResourceNotFoundAdvice {

	@ResponseBody
	@ExceptionHandler({UserNotFoundException.class, WaveNotFoundException.class, WaveReactionNotFoundException.class})
	ResponseEntity<?> userNotFoundHandler() {
		return ResponseEntity.notFound().build();
	}
}
