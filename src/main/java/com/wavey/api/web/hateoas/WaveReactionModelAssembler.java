package com.wavey.api.web.hateoas;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import com.wavey.api.data.WaveReaction;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import com.wavey.api.web.controllers.WaveController;
import com.wavey.api.web.controllers.WaveReactionController;
import com.wavey.api.web.controllers.UserController;

@Component
public class WaveReactionModelAssembler implements RepresentationModelAssembler<WaveReaction, EntityModel<WaveReaction>> {

	@Override
	public EntityModel<WaveReaction> toModel(WaveReaction reaction) {
		
		String username = reaction.getWave().getUser().getUsername();
		String waveId = reaction.getWave().getId().toString();
		String reactionId = reaction.getId().toString();
		
		return EntityModel.of(reaction,
				linkTo(methodOn(WaveReactionController.class).getWaveReaction(reactionId)).withSelfRel(),
				linkTo(methodOn(WaveReactionController.class).getAllReactionsForWave(waveId)).withRel("waveReactions"),
				linkTo(methodOn(WaveController.class).getWave(waveId)).withRel("wave"),
				linkTo(methodOn(WaveController.class).getAllWavesForUser(username)).withRel("waves"),
				linkTo(methodOn(UserController.class).getUser(username)).withRel("user"));
	}

}
