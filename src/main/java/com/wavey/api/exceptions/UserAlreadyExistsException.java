package com.wavey.api.exceptions;

public class UserAlreadyExistsException extends RuntimeException{

	public UserAlreadyExistsException() {
		super("The username already exists");
	}

}
