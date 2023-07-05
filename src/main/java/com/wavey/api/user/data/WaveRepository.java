package com.wavey.api.user.data;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;


public interface WaveRepository extends JpaRepository<Wave, Long> {
	Optional<Wave> findWaveById(UUID waveId);
	void deleteWaveById(UUID waveId);
}
