package com.wavey.api.user.business;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.wavey.api.user.data.Wave;
import com.wavey.api.user.data.WaveReaction;
import com.wavey.api.user.data.WaveReactionRepository;
import com.wavey.api.user.data.User;
import com.wavey.api.user.exceptions.WaveReactionNotFoundException;

@Service
public class WaveReactionService {

	@Autowired
	private WaveReactionRepository reactionRepository;
	
	private Optional<WaveReaction> getWaveReactionOptional(Long userId, Long articleId, Long reactionId) {
		return reactionRepository.findByWaveUserId(userId).stream()
				.filter(reaction -> articleId.equals(reaction.getWave().getId()))
				.filter(reaction -> reactionId.equals(reaction.getId())).findAny();		
	}
	
	public List<WaveReaction> getAllWaveReactions(Long userId, Long articleId) {
		List<WaveReaction> reactions = new ArrayList<>();
		
		reactionRepository.findByWaveUserId(userId).stream()
			.filter(reaction -> articleId.equals(reaction.getWave().getId()))
			.collect(Collectors.toList())
			.forEach(reactions::add);
		
		return reactions;
	}
	
	public WaveReaction getWaveReaction(Long userId, Long articleId, Long reactionId) {
//		return reactionRepository.findById(reactionId).orElse(null);
		return getWaveReactionOptional(userId, articleId, reactionId).orElseThrow(() -> new WaveReactionNotFoundException(userId, articleId, reactionId));
	}
	
	public WaveReaction createWaveReaction(WaveReaction reaction) {
		return reactionRepository.save(reaction);
	}
	
	public WaveReaction updateWaveReaction(Long userId, Long articleId, Long reactionId, WaveReaction newReaction) {
//		reactionRepository.save(reaction);
		
		WaveReaction updatedReaction = getWaveReactionOptional(userId, articleId, reactionId).map(reaction -> {
			reaction.setDate(newReaction.getDate());
			reaction.setReaction(newReaction.getReaction());
			reaction.setReactorId(newReaction.getReactorId());
			
			return reactionRepository.save(reaction);
		}).orElseGet(() -> {
			User user = new User();
			user.setId(userId);
			Wave article = new Wave();
			article.setId(articleId);
			article.setUser(user);
			newReaction.setId(reactionId);
			newReaction.setWave(article);
			return reactionRepository.save(newReaction);
		});
		
		return updatedReaction;
	}
	
	public void deleteWaveReaction(Long userId, Long articleId, Long reactionId) {
//		reactionRepository.deleteById(reactionId);
		WaveReaction reaction = getWaveReactionOptional(userId, articleId, reactionId).orElseThrow(() -> new WaveReactionNotFoundException(userId, articleId, reactionId));
		reactionRepository.delete(reaction);
	}
}
