package com.wavey.api.exceptions;

public class WaveNotFoundException extends RuntimeException{


	public WaveNotFoundException(String waveId) {
		super("The wave with id " + waveId + " was not found.");
	}

}
