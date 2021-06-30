package com.wavey.api.user.web.hateoas;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

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
		return EntityModel.of(wave,
				linkTo(methodOn(WaveController.class).getWave(wave.getUser().getId(), wave.getId())).withSelfRel(),
				linkTo(methodOn(WaveController.class).getAllWaves(wave.getUser().getId())).withRel("waves"),
				linkTo(methodOn(UserController.class).getUser(wave.getUser().getId())).withRel("user"));
	}

}
