package com.wavey.api.user.web.hateoas;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import com.wavey.api.user.data.User;
import com.wavey.api.user.web.UserController;
import com.wavey.api.user.web.WaveController;

@Component
public class UserModelAssembler implements RepresentationModelAssembler<User, EntityModel<User>> {

	@Override
	public EntityModel<User> toModel(User user) {
		// TODO Auto-generated method stub
		return EntityModel.of(user, 
				linkTo(methodOn(UserController.class).getUser(user.getId())).withSelfRel(),
				linkTo(methodOn(UserController.class).getAllUsers()).withRel("users"),
				linkTo(methodOn(WaveController.class).getAllWaves(user.getId())).withRel("waves"));
	}

}
