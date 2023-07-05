package com.wavey.api.user.exceptions;

public class WaveAlreadyExistsException extends RuntimeException{

	public WaveAlreadyExistsException(String waveTitle) {
		super("A wave with title " + waveTitle + " already exists.");
	}

}
