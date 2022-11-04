package com.wavey.api.user.exceptions;

@SuppressWarnings("serial")
public class WaveNotFoundException extends RuntimeException{


	public WaveNotFoundException(Long userId, Long waveId) {
		super("The wave with id " + waveId + " belonging to user with id " + userId + " was not found!");
	}

}
