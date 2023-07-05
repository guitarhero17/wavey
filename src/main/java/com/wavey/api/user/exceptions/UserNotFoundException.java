package com.wavey.api.user.exceptions;

public class UserNotFoundException extends RuntimeException{

	public UserNotFoundException(String username) {
		super("The person with username " + username + " was not found.");
	}

}
