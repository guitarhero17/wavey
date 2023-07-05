package com.wavey.api.user.exceptions;

public class UserAlreadyExistsException extends RuntimeException{

	public UserAlreadyExistsException() {
		super("The username already exists");
	}

}
