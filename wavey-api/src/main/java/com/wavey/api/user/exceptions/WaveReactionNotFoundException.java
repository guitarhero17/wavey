package com.wavey.api.user.exceptions;

@SuppressWarnings("serial")
public class WaveReactionNotFoundException extends RuntimeException{


	public WaveReactionNotFoundException(Long userId, Long articleId, Long reactionId) {
		super("Reaction with id " + reactionId + " belonging to article with id " + articleId + " of user with id " + userId + " was not found!");
	}

}
