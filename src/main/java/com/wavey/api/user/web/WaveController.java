package com.wavey.api.user.web;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import java.net.URI;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import com.fasterxml.jackson.databind.JsonNode;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.IanaLinkRelations;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.wavey.api.user.business.WaveService;
import com.wavey.api.user.data.User;
import com.wavey.api.user.data.Wave;
import com.wavey.api.user.web.hateoas.WaveModelAssembler;

/**
 * Created in compliance with the "Building REST services" guide
 * <p>{@link <a href="https://spring.io/guides/tutorials/rest/">REST</a>}</p>
 * @author guitarhero17
 *
 */
@RestController
@CrossOrigin
public class WaveController {

	@Autowired
	private WaveService waveService;

	@Autowired
	private WaveModelAssembler assembler;

	@GetMapping("/users/{username}/waves")
	public CollectionModel<EntityModel<Wave>> getAllWavesForUser(@PathVariable String username) {

		List<EntityModel<Wave>> waveModels = waveService.getAllWavesForUser(username).stream()
				.map(assembler::toModel).collect(Collectors.toList());

		return CollectionModel.of(waveModels, linkTo(methodOn(WaveController.class).getAllWavesForUser(username)).withSelfRel());
	}

	@PostMapping(path = "/users/{username}/waves", consumes = "application/json")
	public ResponseEntity<EntityModel<Wave>> createWave(@Valid @RequestBody Wave wave, @PathVariable String username) {
		EntityModel<Wave> entityModel = assembler.toModel(waveService.createWave(wave, username));
		URI selfLink = entityModel.getRequiredLink(IanaLinkRelations.SELF).toUri();
		return ResponseEntity
				.created(selfLink).header("Location", selfLink.toString()).body(entityModel);
	}
	
	@GetMapping("/waves/{waveId}")
	public EntityModel<Wave> getWave(@PathVariable String waveId) {
		return assembler.toModel(waveService.getWave(waveId));
	}
	
//	@PutMapping("/users/{userId}/waves/{waveId}")
//	public ResponseEntity<?> updateWave(@RequestBody Wave wave, @PathVariable Long userId, @PathVariable Long waveId) {
////		wave.setUser(new User(userId, null, null, null, null, null, null));
//		User user = new User();
//		user.setId(userId);
//		wave.setUser(user);
//		EntityModel<Wave> entityModel = assembler.toModel(waveService.updateWave(userId, waveId, wave));
//
//		return ResponseEntity
//				.created(entityModel.getRequiredLink(IanaLinkRelations.SELF).toUri()).body(entityModel);
//	}

	@PatchMapping(path = "/waves/{waveId}", consumes = "application/merge-patch+json")
	@CrossOrigin(allowedHeaders = "*")
	public ResponseEntity<User> updateWave(@PathVariable String waveId, @RequestBody JsonNode patch) {
		waveService.updateWave(waveId, patch);
		return ResponseEntity.noContent().build();
	}
	
	@DeleteMapping("/{waveId}")
	public ResponseEntity<?> deleteWave(@PathVariable String waveId) {
		waveService.deleteWave(waveId);
		return ResponseEntity.noContent().build();
	}
}
