package com.wavey.api.user.web;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.IanaLinkRelations;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.wavey.api.user.business.WaveReactionService;
import com.wavey.api.user.data.Wave;
import com.wavey.api.user.data.WaveReaction;
import com.wavey.api.user.data.User;
import com.wavey.api.user.web.hateoas.WaveReactionModelAssembler;

@RestController
@CrossOrigin
public class WaveReactionController {

	@Autowired
	private WaveReactionService reactionService;
	
	@Autowired
	private WaveReactionModelAssembler assembler;
	
	@GetMapping("/users/{userId}/waves/{waveId}/reactions")
	@CrossOrigin(allowedHeaders = "*")
	public CollectionModel<EntityModel<WaveReaction>> getAllWaveReactions(@PathVariable Long userId, @PathVariable Long waveId) {
		List<EntityModel<WaveReaction>> reactionModels = reactionService.getAllWaveReactions(userId, waveId).stream()
				.map(assembler::toModel).collect(Collectors.toList());
		
		return CollectionModel.of(reactionModels, linkTo(methodOn(WaveReactionController.class).getAllWaveReactions(userId, waveId)).withSelfRel());	
	}
	
	@GetMapping("/users/{userId}/waves/{waveId}/reactions/{reactionId}")
	@CrossOrigin(allowedHeaders = "*")
	public EntityModel<WaveReaction> getWaveReaction(@PathVariable Long userId, @PathVariable Long waveId, @PathVariable Long reactionId) {
		return assembler.toModel(reactionService.getWaveReaction(userId, waveId, reactionId));
	}
	
	@PostMapping("/users/{userId}/waves/{waveId}/reactions")
	@CrossOrigin(allowedHeaders = "*")
	public ResponseEntity<?> createWaveReaction(@RequestBody WaveReaction reaction, @PathVariable Long userId, @PathVariable Long waveId) {
		User user = new User();
		user.setId(userId);
		Wave wave = new Wave();
		wave.setId(waveId);
		wave.setUser(user);
		reaction.setWave(wave);
		
		EntityModel<WaveReaction> entityModel = assembler.toModel(reactionService.createWaveReaction(reaction));
		
		return ResponseEntity
				.created(entityModel.getRequiredLink(IanaLinkRelations.SELF).toUri()).body(entityModel);
	}
	
	@PutMapping("/users/{userId}/waves/{waveId}/reactions/{reacionId}")
	@CrossOrigin(allowedHeaders = "*")
	public ResponseEntity<?> updateWaveReaction(@RequestBody WaveReaction reaction, @PathVariable Long userId, @PathVariable Long waveId, @PathVariable Long reactionId) {
//		reaction.setWave(new Wave(waveId, null, null, null, null, null));
		
		User user = new User();
		user.setId(userId);
		Wave wave = new Wave();
		wave.setId(waveId);
		wave.setUser(user);
		reaction.setWave(wave);		

		EntityModel<WaveReaction> entityModel = assembler.toModel(reactionService.updateWaveReaction(userId, waveId, reactionId, reaction));
		
		return ResponseEntity
				.created(entityModel.getRequiredLink(IanaLinkRelations.SELF).toUri()).body(entityModel);
		
		
	}
	
	@DeleteMapping("/users/{userId}/waves/{waveId}/reactions/{reacionId}")
	@CrossOrigin(allowedHeaders = "*")
	public ResponseEntity<?> deleteWave(@PathVariable Long userId, @PathVariable Long waveId, @PathVariable Long reactionId) {
//		reactionService.deleteWaveReaction(id);
		
		reactionService.deleteWaveReaction(userId, waveId, reactionId);
		return ResponseEntity.noContent().build();
	}
}
