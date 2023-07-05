package com.wavey.api.user.exceptions;

public class WaveReactionNotFoundException extends RuntimeException{


	public WaveReactionNotFoundException(String reactionId) {
		super("Reaction with id " + reactionId + " was not found.");
	}

}
