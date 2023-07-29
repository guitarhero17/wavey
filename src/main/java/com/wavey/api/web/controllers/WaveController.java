package com.wavey.api.web.controllers;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import java.net.URI;
import java.util.List;
import java.util.stream.Collectors;

import com.fasterxml.jackson.databind.JsonNode;
import com.wavey.api.data.User;
import com.wavey.api.data.Wave;
import com.wavey.api.web.hateoas.WaveModelAssembler;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.data.rest.webmvc.config.RepositoryRestConfigurer;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.IanaLinkRelations;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.wavey.api.business.WaveService;
import org.springframework.web.multipart.MultipartFile;

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

	@PostMapping(path = "/users/{username}/waves", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
	public ResponseEntity<EntityModel<Wave>> createWave(@PathVariable String username, @Valid @RequestPart Wave wave, @RequestPart MultipartFile audioFile) {
		EntityModel<Wave> entityModel = assembler.toModel(waveService.createWave(username, wave, audioFile));
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
	
	@DeleteMapping("/waves/{waveId}")
	public ResponseEntity<?> deleteWave(@PathVariable String waveId) {
		waveService.deleteWave(waveId);
		return ResponseEntity.noContent().build();
	}
}
