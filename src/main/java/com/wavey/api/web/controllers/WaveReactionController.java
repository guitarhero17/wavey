package com.wavey.api.web.controllers;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import java.util.List;
import java.util.stream.Collectors;

import com.wavey.api.data.WaveReaction;
import com.wavey.api.web.hateoas.WaveReactionModelAssembler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.IanaLinkRelations;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.wavey.api.business.WaveReactionService;

@RestController
@CrossOrigin
public class WaveReactionController {

	@Autowired
	private WaveReactionService waveReactionService;

	@Autowired
	private WaveReactionModelAssembler assembler;
	
	@GetMapping("/waves/{waveId}/wave-reactions")
	public CollectionModel<EntityModel<WaveReaction>> getAllReactionsForWave(@PathVariable String waveId) {
		List<EntityModel<WaveReaction>> reactionModels = waveReactionService.getAllReactionsForWave(waveId).stream()
				.map(assembler::toModel).collect(Collectors.toList());
		
		return CollectionModel.of(reactionModels, linkTo(methodOn(WaveReactionController.class).getAllReactionsForWave(waveId)).withSelfRel());
	}

	@PostMapping(path = "/waves/{waveId}/wave-reactions", consumes = "application/json")
	public ResponseEntity<EntityModel<WaveReaction>> createWaveReaction(@RequestBody WaveReaction reaction, @PathVariable String waveId) {
		EntityModel<WaveReaction> entityModel = assembler.toModel(waveReactionService.createWaveReaction(reaction, waveId));

		return ResponseEntity
				.created(entityModel.getRequiredLink(IanaLinkRelations.SELF).toUri()).body(entityModel);
	}
	
	@GetMapping("/wave-reactions/{waveReactionId}")
	public EntityModel<WaveReaction> getWaveReaction(@PathVariable String waveReactionId) {
		return assembler.toModel(waveReactionService.getWaveReaction(waveReactionId));
	}
	
//	@PutMapping("/users/{userId}/waves/{waveId}/wave-reactions/{reacionId}")
//	public ResponseEntity<?> updateWaveReaction(@RequestBody WaveReaction reaction, @PathVariable Long userId, @PathVariable Long waveId, @PathVariable Long waveReactionId) {
////		reaction.setWave(new Wave(waveId, null, null, null, null, null));
//		
//		User user = new User();
//		user.setId(userId);
//		Wave wave = new Wave();
//		wave.setId(waveId);
//		wave.setUser(user);
//		reaction.setWave(wave);		
//
//		EntityModel<WaveReaction> entityModel = assembler.toModel(reactionService.updateWaveReaction(userId, waveId, waveReactionId, reaction));
//		
//		return ResponseEntity
//				.created(entityModel.getRequiredLink(IanaLinkRelations.SELF).toUri()).body(entityModel);
//		
//		
//	}
	
	@DeleteMapping("/wave-reactions/{waveReactionId}")
	public ResponseEntity<?> deleteWaveReaction(@PathVariable String waveReactionId) {
		waveReactionService.deleteWaveReaction(waveReactionId);
		return ResponseEntity.noContent().build();
	}
}
