package com.wavey.api.web.controllers;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import java.util.List;
import java.util.stream.Collectors;

import com.wavey.api.data.WaveReaction;
import com.wavey.api.web.hateoas.WaveReactionModelAssembler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.data.rest.webmvc.config.RepositoryRestConfigurer;
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

	@GetMapping("/wave-reactions/{waveReactionId}")
	public EntityModel<WaveReaction> getWaveReaction(@PathVariable String waveReactionId) {
		return assembler.toModel(waveReactionService.getWaveReaction(waveReactionId));
	}

	@PostMapping(path = "/waves/{waveId}/wave-reactions", consumes = "application/json")
	public ResponseEntity<EntityModel<WaveReaction>> createWaveReaction(@RequestBody WaveReaction reaction, @PathVariable String waveId) {
		EntityModel<WaveReaction> entityModel = assembler.toModel(waveReactionService.createWaveReaction(reaction, waveId));

		return ResponseEntity
				.created(entityModel.getRequiredLink(IanaLinkRelations.SELF).toUri()).body(entityModel);
	}
	
	@DeleteMapping("/wave-reactions/{waveReactionId}")
	public ResponseEntity<?> deleteWaveReaction(@PathVariable String waveReactionId) {
		waveReactionService.deleteWaveReaction(waveReactionId);
		return ResponseEntity.noContent().build();
	}
}
