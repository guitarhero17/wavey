package com.wavey.api.user.data;

import java.util.List;

import org.springframework.data.repository.CrudRepository;


public interface WaveRepository extends CrudRepository<Wave, Long>{
	public List<Wave> findByUserId(Long userId);
}
