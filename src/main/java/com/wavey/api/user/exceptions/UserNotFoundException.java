package com.wavey.api.user.exceptions;

@SuppressWarnings("serial")
public class UserNotFoundException extends RuntimeException{


	public UserNotFoundException(Long userId) {
		super("The person with id " + userId + " was not found!");
	}

}
