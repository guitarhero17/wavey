package com.wavey.api.user.web.hateoas;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import com.wavey.api.user.data.WaveReaction;
import com.wavey.api.user.web.WaveController;
import com.wavey.api.user.web.WaveReactionController;
import com.wavey.api.user.web.UserController;

import java.util.UUID;

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
