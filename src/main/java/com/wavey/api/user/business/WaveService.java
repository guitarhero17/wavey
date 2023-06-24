package com.wavey.api.user.business;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.wavey.api.user.data.Wave;
import com.wavey.api.user.data.WaveRepository;
import com.wavey.api.user.exceptions.WaveNotFoundException;

@Service
public class WaveService {

	@Autowired
	private WaveRepository waveRepository;
	
	private Optional<Wave> getWaveOptional(Long userId, Long waveId) {
		return waveRepository.findByUserId(userId).stream()
				.filter(wave -> waveId.equals(wave.getId())).findAny();
	}
	
	public List<Wave> getAllWaves(Long userId) {
		return new ArrayList<>(waveRepository.findByUserId(userId));
	}
	
	public Wave getWave(Long userId, Long waveId) {
		return getWaveOptional(userId, waveId).orElseThrow(() -> new WaveNotFoundException(userId, waveId));
	}
	
	public Wave createWave(Wave wave) {
		return waveRepository.save(wave);
	}
	
	public Wave updateWave(Long userId, Long waveId, Wave newWave) {

		return getWaveOptional(userId, waveId).map(wave -> {
			wave.setDescription(newWave.getDescription());
			return waveRepository.save(wave);
		}).orElseGet(() -> {
			newWave.setId(waveId);
			return waveRepository.save(newWave);
		});
	}
	
	public void deleteWave(Long userId, Long waveId) {
		Wave wave = getWaveOptional(userId, waveId).orElseThrow(() -> new WaveNotFoundException(userId, waveId));
		waveRepository.delete(wave);
	}
}
