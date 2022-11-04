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

@Component
public class WaveReactionModelAssembler implements RepresentationModelAssembler<WaveReaction, EntityModel<WaveReaction>> {

	@Override
	public EntityModel<WaveReaction> toModel(WaveReaction reaction) {
		
		Long userId = reaction.getWave().getUser().getId();
		Long waveId = reaction.getWave().getId();
		Long reactionId = reaction.getId();
		
		return EntityModel.of(reaction,
				linkTo(methodOn(WaveReactionController.class).getWaveReaction(userId, waveId, reactionId)).withSelfRel(),
				linkTo(methodOn(WaveReactionController.class).getAllWaveReactions(userId, waveId)).withRel("waveReactions"),
				linkTo(methodOn(WaveController.class).getWave(userId, waveId)).withRel("wave"),
				linkTo(methodOn(WaveController.class).getAllWaves(userId)).withRel("waves"),
				linkTo(methodOn(UserController.class).getUser(userId)).withRel("user"));
	}

}
