package com.wavey.api.user.business;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.fge.jsonpatch.JsonPatch;
import com.github.fge.jsonpatch.JsonPatchException;
import com.github.fge.jsonpatch.mergepatch.JsonMergePatch;
import com.wavey.api.user.data.*;
import com.wavey.api.user.exceptions.FieldCanNotBePatchedException;
import com.wavey.api.user.exceptions.UUIDhasWrongFormatException;
import com.wavey.api.user.exceptions.WaveNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.wavey.api.user.exceptions.WaveReactionNotFoundException;

@Service
public class WaveReactionService {

	@Autowired
	private WaveReactionRepository waveReactionRepository;

	@Autowired
	private WaveRepository waveRepository;

	private final ObjectMapper objectMapper = new ObjectMapper();

	private static final Logger log = LoggerFactory.getLogger(WaveService.class);
	
	private Optional<WaveReaction> getWaveReactionOptional(String waveReactionId) {
//		return reactionRepository.findByWaveUserId(userId).stream()
//				.filter(reaction -> articleId.equals(reaction.getWave().getId()))
//				.filter(reaction -> waveReactionId.equals(reaction.getId())).findAny();
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
//		return reactionRepository.findById(waveReactionId).orElse(null);
//		return getWaveReactionOptional(userId, articleId, waveReactionId).orElseThrow(() -> new WaveReactionNotFoundException(userId, articleId, waveReactionId));
		return getWaveReactionOptional(waveReactionId).orElseThrow(() -> new WaveReactionNotFoundException(waveReactionId));
	}
	
	public WaveReaction createWaveReaction(WaveReaction reaction, String waveId) {
		try {
			Wave wave = waveRepository.findWaveById(UUID.fromString(waveId)).orElseThrow(() -> new WaveNotFoundException(waveId));
			wave.addReaction(reaction);
			return waveReactionRepository.save(reaction);
		} catch (IllegalArgumentException e) {
			throw new UUIDhasWrongFormatException(e.getMessage());
		}
	}
	
	public WaveReaction createWaveReaction(UUID userId, UUID waveId, String waveReactionId, WaveReaction newReaction) {
//		reactionRepository.save(reaction);
		try {
			return getWaveReactionOptional(waveReactionId).map(reaction -> {
				reaction.setDate(newReaction.getDate());
				reaction.setReaction(newReaction.getReaction());

				return waveReactionRepository.save(reaction);
			}).orElseGet(() -> {
				User user = new User();
				user.setId(userId);
				Wave article = new Wave();
				article.setId(waveId);
				article.setUser(user);
				newReaction.setId(UUID.fromString(waveReactionId));
				newReaction.setWave(article);
				return waveReactionRepository.save(newReaction);
			});
		} catch (IllegalArgumentException e) {
			throw new UUIDhasWrongFormatException(e.getMessage());
		}
	}
	
	public void deleteWaveReaction(String waveReactionId) {
//		WaveReaction reaction = getWaveReactionOptional(waveReactionId).orElseThrow(() -> new WaveReactionNotFoundException(waveReactionId));
//		waveReactionRepository.delete(reaction);
		try {
			waveReactionRepository.deleteWaveReactionById(UUID.fromString(waveReactionId));
		} catch (IllegalArgumentException e) {
			if (e.getMessage().contains("UUID")) {
				throw new UUIDhasWrongFormatException(e.getMessage());
			}
		}
	}
}
