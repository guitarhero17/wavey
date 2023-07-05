package com.wavey.api.user.web.hateoas;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import com.wavey.api.user.web.WaveReactionController;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import com.wavey.api.user.data.Wave;
import com.wavey.api.user.web.UserController;
import com.wavey.api.user.web.WaveController;

@Component
public class WaveModelAssembler implements RepresentationModelAssembler<Wave, EntityModel<Wave>> {

	@Override
	public EntityModel<Wave> toModel(Wave wave) {
		String username = wave.getUser().getUsername();
		return EntityModel.of(wave,
				linkTo(methodOn(WaveController.class).getWave(wave.getId().toString())).withSelfRel(),
				linkTo(methodOn(WaveReactionController.class).getAllReactionsForWave(wave.getId().toString())).withRel("waveReactions"),
				linkTo(methodOn(WaveController.class).getAllWavesForUser(username)).withRel("waves"),
				linkTo(methodOn(UserController.class).getUser(username)).withRel("user"));
	}

}
