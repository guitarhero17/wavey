package com.wavey.api.exceptions;

public class WaveAlreadyExistsException extends RuntimeException{

	public WaveAlreadyExistsException(String waveTitle) {
		super("A wave with title " + waveTitle + " already exists.");
	}

}
