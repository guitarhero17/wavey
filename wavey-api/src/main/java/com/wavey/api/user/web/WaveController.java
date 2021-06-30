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

import com.wavey.api.user.business.WaveService;
import com.wavey.api.user.data.User;
import com.wavey.api.user.data.Wave;
import com.wavey.api.user.web.hateoas.WaveModelAssembler;

/**
 * Created in compliance with the "Building REST services" guide
 * <p>{@link https://spring.io/guides/tutorials/rest/}</p>
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
	
	@GetMapping("/users/{userId}/waves")
	public CollectionModel<EntityModel<Wave>> getAllWaves(@PathVariable Long userId) {
		
		List<EntityModel<Wave>> waveModels = waveService.getAllWaves(userId).stream()
				.map(assembler::toModel).collect(Collectors.toList());
		
		return CollectionModel.of(waveModels, linkTo(methodOn(WaveController.class).getAllWaves(userId)).withSelfRel());			
	}
	
	@GetMapping("/users/{userId}/waves/{waveId}")
	public EntityModel<Wave> getWave(@PathVariable Long userId, @PathVariable Long waveId) {
		return assembler.toModel(waveService.getWave(userId, waveId));
	}
	
	@PostMapping("/users/{userId}/waves")
	public ResponseEntity<?> createWave(@RequestBody Wave wave, @PathVariable Long userId) {
		
//		wave.setUser(new User(userId, null, null, null, null, null, null));
		User user = new User();
		user.setId(userId);
		wave.setUser(user);
		EntityModel<Wave> entityModel = assembler.toModel(waveService.createWave(wave));
		
		return ResponseEntity
				.created(entityModel.getRequiredLink(IanaLinkRelations.SELF).toUri()).body(entityModel);
	}
	
	@PutMapping("/users/{userId}/waves/{waveId}")
	public ResponseEntity<?> updateWave(@RequestBody Wave wave, @PathVariable Long userId, @PathVariable Long waveId) {
//		wave.setUser(new User(userId, null, null, null, null, null, null));
		User user = new User();
		user.setId(userId);
		wave.setUser(user);
		EntityModel<Wave> entityModel = assembler.toModel(waveService.updateWave(userId, waveId, wave));
		
		return ResponseEntity
				.created(entityModel.getRequiredLink(IanaLinkRelations.SELF).toUri()).body(entityModel);
	}
	
	@DeleteMapping("/users/{userId}/waves/{waveId}")
	public ResponseEntity<?> deleteWave(@PathVariable Long userId, @PathVariable Long waveId) {
		waveService.deleteWave(userId, waveId);
		return ResponseEntity.noContent().build();
	}
}
