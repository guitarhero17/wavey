package com.wavey.api.web.hateoas;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import com.wavey.api.data.User;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import com.wavey.api.web.controllers.UserController;
import com.wavey.api.web.controllers.WaveController;

@Component
public class UserModelAssembler implements RepresentationModelAssembler<User, EntityModel<User>> {

	@Override
	public EntityModel<User> toModel(User user) {
		return EntityModel.of(user, 
				linkTo(methodOn(UserController.class).getUser(user.getUsername())).withSelfRel(),
				linkTo(methodOn(UserController.class).getAllUsers()).withRel("users"),
				linkTo(methodOn(WaveController.class).getAllWavesForUser(user.getUsername())).withRel("waves"));
	}

}
