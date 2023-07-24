package com.wavey.api.data;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;


public interface WaveRepository extends JpaRepository<Wave, Long> {
	Optional<Wave> findWaveById(UUID waveId);
	void deleteWaveById(UUID waveId);
}
