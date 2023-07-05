package com.wavey.api.user.data;

import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

public interface WaveReactionRepository extends JpaRepository<WaveReaction, Long> {
	Optional<WaveReaction> findWaveReactionById(UUID reactionId);
	void deleteWaveReactionById(UUID reactionId);
}
