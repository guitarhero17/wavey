package com.wavey.api.user.data;

import java.util.Date;
import java.util.List;

import org.springframework.data.repository.CrudRepository;


public interface WaveReactionRepository extends CrudRepository<WaveReaction, Long>{
	public List<WaveReaction> findByDate(Date date);
	public List<WaveReaction> findByWaveUserId(Long userId);
	public List<WaveReaction> findByReactorId(Long reactorId);
}
