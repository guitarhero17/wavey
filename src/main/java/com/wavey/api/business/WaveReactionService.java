package com.wavey.api.business;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.wavey.api.data.*;
import com.wavey.api.exceptions.UUIDhasWrongFormatException;
import com.wavey.api.exceptions.WaveNotFoundException;
import com.wavey.api.exceptions.WaveReactionNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class WaveReactionService {

	@Autowired
	private WaveReactionRepository waveReactionRepository;

	@Autowired
	private WaveRepository waveRepository;
	
	private Optional<WaveReaction> getWaveReactionOptional(String waveReactionId) {
		try {
			return waveReactionRepository.findWaveReactionById(UUID.fromString(waveReactionId));
		} catch (IllegalArgumentException e) {
			throw new UUIDhasWrongFormatException(e.getMessage());
		}
	}
	
	public List<WaveReaction> getAllReactionsForWave(String waveId) {
		try {
			return new ArrayList<>(waveRepository.findWaveById(
					UUID.fromString(waveId)).orElseThrow(
							() -> new WaveNotFoundException(waveId)).getReactions());
		} catch (IllegalArgumentException e) {
			throw new UUIDhasWrongFormatException(e.getMessage());
		}
	}
	
	public WaveReaction getWaveReaction(String waveReactionId) {
		return getWaveReactionOptional(waveReactionId).orElseThrow(() -> new WaveReactionNotFoundException(waveReactionId));
	}
	
	public WaveReaction createWaveReaction(WaveReaction reaction, String waveId) {
		try {
			Wave wave = waveRepository.findWaveById(UUID.fromString(waveId)).orElseThrow(() -> new WaveNotFoundException(waveId));
			reaction.setWave(wave);
			return waveReactionRepository.save(reaction);
		} catch (IllegalArgumentException e) {
			throw new UUIDhasWrongFormatException(e.getMessage());
		}
	}
	
	public void deleteWaveReaction(String waveReactionId) {
		try {
			waveReactionRepository.deleteWaveReactionById(UUID.fromString(waveReactionId));
		} catch (IllegalArgumentException e) {
			if (e.getMessage().contains("UUID")) {
				throw new UUIDhasWrongFormatException(e.getMessage());
			}
		}
	}
}
