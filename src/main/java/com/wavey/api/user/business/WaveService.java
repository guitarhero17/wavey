package com.wavey.api.user.business;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.fge.jsonpatch.JsonPatchException;
import com.github.fge.jsonpatch.mergepatch.JsonMergePatch;
import com.wavey.api.user.data.*;
import com.wavey.api.user.exceptions.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class WaveService {

	@Autowired
	private WaveRepository waveRepository;

	@Autowired
	private UserRepository userRepository;

	private final ObjectMapper objectMapper = new ObjectMapper();

	private static final Logger log = LoggerFactory.getLogger(WaveService.class);
	
	private Optional<Wave> getWaveOptional(String waveId) {
//		return waveRepository.findByUserName(username).stream()
//				.filter(wave -> UUID.fromString(waveId).equals(wave.getId())).findAny();
		try {
			return waveRepository.findWaveById(UUID.fromString(waveId));
		} catch (IllegalArgumentException e) {
			throw new UUIDhasWrongFormatException(e.getMessage());
		}
	}
	
	public List<Wave> getAllWavesForUser(String username) {
		return new ArrayList<>(
				userRepository.findByUsername(username).orElseThrow(
						() -> new UserNotFoundException(username)
				).getWaves());
	}
	
	public Wave getWave(String waveId) {
//		return getWaveOptional(username, waveId).orElseThrow(() -> new WaveNotFoundException(username, waveId));
		return getWaveOptional(waveId).orElseThrow(() -> new WaveNotFoundException(waveId));
	}
	
	public Wave createWave(Wave newWave, String username) {
		User waveUser = userRepository.findByUsername(username).orElseThrow(() -> new UserNotFoundException(username));
		List<Wave> sameTitleWaves = waveUser.getWaves().stream().filter(wave -> wave.getTitle().equals(newWave.getTitle())).toList();
		if (sameTitleWaves.isEmpty()) {
			newWave.setUser(waveUser);
			return waveRepository.save(newWave);
		} else {
			throw new WaveAlreadyExistsException(newWave.getTitle());
		}
	}
	
	public void updateWave(String waveId, JsonNode patch) {
		Wave wave = waveRepository.findWaveById(UUID.fromString(waveId)).orElseThrow(() -> new WaveNotFoundException(waveId));
		User waveUser = wave.getUser();

		if (patch.has("id")) {
			throw new FieldCanNotBePatchedException("id");
		}

		try {
			JsonNode original = objectMapper.valueToTree(wave);
			JsonNode patched = JsonMergePatch.fromJson(patch).apply(original);
			Wave patchedWave = objectMapper.treeToValue(patched, Wave.class);
			patchedWave.setUser(waveUser);

			waveRepository.save(patchedWave);
		} catch (JsonProcessingException e) {
			log.info(e.getMessage());
			throw new RuntimeException("Could not convert json back to Wave");
		} catch (JsonPatchException e) {
			throw new RuntimeException(e);
		} catch (IllegalArgumentException e) {
			if (e.getMessage().contains("UUID")) {
				throw new UUIDhasWrongFormatException(e.getMessage());
			}
		}
	}
	
	public void deleteWave(String waveId) {
//		Wave wave = getWaveOptional(waveId).orElseThrow(() -> new WaveNotFoundException(waveId));
//		waveRepository.delete(wave);
		try {
			waveRepository.deleteWaveById(UUID.fromString(waveId));
		} catch (IllegalArgumentException e) {
			if (e.getMessage().contains("UUID")) {
				throw new UUIDhasWrongFormatException(e.getMessage());
			}
		}
	}
}
