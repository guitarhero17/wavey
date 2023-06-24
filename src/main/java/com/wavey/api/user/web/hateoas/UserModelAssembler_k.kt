package com.wavey.api.user.web.hateoas

import com.wavey.api.user.data.User
import com.wavey.api.user.web.UserController
import com.wavey.api.user.web.WaveController
import org.springframework.hateoas.EntityModel
import org.springframework.hateoas.server.RepresentationModelAssembler
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder
import org.springframework.stereotype.Component

@Component
class UserModelAssembler_k : RepresentationModelAssembler<User, EntityModel<User>> {
    override fun toModel(user: User): EntityModel<User> {
        return EntityModel.of(
            user,
            WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(UserController::class.java).getUser(user.id))
                .withSelfRel(),
            WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(UserController::class.java).allUsers).withRel("users"),
            WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(WaveController::class.java).getAllWaves(user.id))
                .withRel("waves")
        )
    }
}
