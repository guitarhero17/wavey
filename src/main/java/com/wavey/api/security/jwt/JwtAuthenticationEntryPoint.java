package com.wavey.api.security.jwt;

import java.io.IOException;
import java.util.Collections;

// import javax.servlet.ServletException;
// import javax.servlet.http.HttpServletRequest;
// import javax.servlet.http.HttpServletResponse;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.MediaType;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.ObjectMapper;

@Component
public class JwtAuthenticationEntryPoint implements AuthenticationEntryPoint {

	@Override
	public void commence(HttpServletRequest req, HttpServletResponse res,
			AuthenticationException authException) throws IOException, ServletException {

		res.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
		res.setContentType(MediaType.APPLICATION_JSON_VALUE);
		String message;
		// Check if the req has any exception that we have stored in the request
		final Exception exception = (Exception) req.getAttribute("exception");
		
		// If yes then use it to create the response message else use the authException
		if (exception != null) {
			
			byte[] body = new ObjectMapper().writeValueAsBytes(Collections.singletonMap("cause", exception.toString()));
			res.getOutputStream().write(body);
		} else {
			if (authException.getCause() != null) {
				message = authException.getCause().toString() + " " + authException.getMessage();
			} else {
				message = authException.getMessage();
			}

			byte[] body = new ObjectMapper().writeValueAsBytes(Collections.singletonMap("error", message));

			res.getOutputStream().write(body);
		}
	}
}
