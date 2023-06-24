package com.wavey.api.user.web

import com.wavey.api.user.business.UserService
import com.wavey.api.user.data.User
import com.wavey.api.user.web.hateoas.UserModelAssembler
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.hateoas.CollectionModel
import org.springframework.hateoas.EntityModel
import org.springframework.hateoas.IanaLinkRelations
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import java.util.stream.Collectors

/**
 * Created in compliance with the "Building REST services" guide
 *
 * [][<a href=]//spring.io/guides/tutorials/rest/">Rest tutorials">&lt;a href=&quot;https://spring.io/guides/tutorials/rest/&quot;&gt;Rest tutorials&lt;/a&gt;
 * @author guitarhero17
 */
@RestController
@RequestMapping("/users")
@CrossOrigin
class UserController_k {
    @Autowired
    private val userService: UserService? = null

    @Autowired
    private val assembler: UserModelAssembler? = null

    @get:CrossOrigin(allowedHeaders = ["*"])
    @get:GetMapping("/")
    val allUsers: CollectionModel<EntityModel<User>>
        get() {
            val userModels = userService!!.users
                .map { user: User? -> assembler!!.toModel(user) }
            return CollectionModel.of(
                userModels, WebMvcLinkBuilder.linkTo(
                    WebMvcLinkBuilder.methodOn(
                        UserController_k::class.java
                    ).allUsers
                ).withSelfRel()
            )
        }

    @GetMapping("/{id}")
    @CrossOrigin(allowedHeaders = ["*"])
    fun getUser(@PathVariable id: Long): EntityModel<User> {
        return assembler!!.toModel(userService!!.getUser(id))
    }

    @PostMapping("/")
    @CrossOrigin(allowedHeaders = ["*"])
    fun createUser(@RequestBody user: User): ResponseEntity<*> {
        val username = user.username
        val name = user.name
        val password = user.password
        val entityModel = assembler!!.toModel(userService!!.createUser(username, name, password))
        return ResponseEntity
            .created(entityModel.getRequiredLink(IanaLinkRelations.SELF).toUri()).body(entityModel)
    }

    @PutMapping("/{id}")
    @CrossOrigin(allowedHeaders = ["*"])
    fun updateUser(@PathVariable id: Long?, @RequestBody newUser: User?): ResponseEntity<*> {
        val entityModel = assembler!!.toModel(userService!!.updateUser(id, newUser))
        return ResponseEntity
            .created(entityModel.getRequiredLink(IanaLinkRelations.SELF).toUri()).body(entityModel)
    }

    @DeleteMapping("/{id}")
    @CrossOrigin(allowedHeaders = ["*"])
    fun deleteUser(@PathVariable id: Long?): ResponseEntity<*> {
        userService!!.deleteUser(id)
        return ResponseEntity.noContent().build<Any>()
    }
}
