package com.wavey.api.user.web;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import java.net.URI;
import java.util.List;
import java.util.stream.Collectors;

import com.fasterxml.jackson.databind.JsonNode;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.IanaLinkRelations;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.wavey.api.user.business.UserService;
import com.wavey.api.user.data.User;
import com.wavey.api.user.web.hateoas.UserModelAssembler;


/**
 * Created in compliance with the "Building REST services" guide
 * <p>{@link <a href="https://spring.io/guides/tutorials/rest/">REST</a>}</p>
 * @author guitarhero17
 *
 */
@RestController
@RequestMapping("/users")
@CrossOrigin
public class UserController {

	@Autowired
	private UserService userService ;

	@Autowired
	private UserModelAssembler assembler;
	
	@GetMapping()
	public CollectionModel<EntityModel<User>> getAllUsers() {
		
		List<EntityModel<User>> userModels = userService.getUsers().stream()
				.map(assembler::toModel).collect(Collectors.toList());
		
		return CollectionModel.of(userModels, linkTo(methodOn(UserController.class).getAllUsers()).withSelfRel());			
	}

	@PostMapping(consumes = "application/json")
	public ResponseEntity<EntityModel<User>> createUser(@Valid @RequestBody User user) {
		EntityModel<User> entityModel = assembler.toModel(userService.createUser(user));
		URI selfLink = entityModel.getRequiredLink(IanaLinkRelations.SELF).toUri();
		return ResponseEntity
				.created(selfLink).header("Location", selfLink.toString()).body(entityModel);
	}
	
	
	@GetMapping("/{username}")
	public EntityModel<User> getUser(@PathVariable String username) {
		return assembler.toModel(userService.getUser(username));
	}

	@PatchMapping(path = "/{username}", consumes = "application/merge-patch+json")
	public ResponseEntity<User> updateUser(@PathVariable String username, @RequestBody JsonNode patch) {
		userService.updateUser(username, patch);
		return ResponseEntity.noContent().build();
	}

// PUT TURNED OFF, PATCH USED FOR UPDATES
//	@PutMapping("/users/{username}")
//	@CrossOrigin(allowedHeaders = "*")
//	public ResponseEntity<?> replaceUser(@PathVariable String username, @RequestBody User newUser) {
//
//		EntityModel<User> entityModel = assembler.toModel(userService.replaceUser(username, newUser));
//
//		return ResponseEntity
//				.created(entityModel.getRequiredLink(IanaLinkRelations.SELF).toUri()).body(entityModel);
//	}

	
	@DeleteMapping("/{username}")
	public ResponseEntity<?> deleteUser(@PathVariable String username) {
		userService.deleteUser(username);
		return ResponseEntity.noContent().build();
	}
	
//	@PostMapping(value="/users/{id}/picture", consumes="multipart/form-data", produces = "application/json")
////	@CrossOrigin(allowedHeaders = {"Content-Type", "X-Requested-With", "accept", "Origin", "Access-Control-Request-Method", "Access-Control-Request-Headers"}, exposedHeaders = {"Access-Control-Allow-Origin", "Access-Control-Allow-Credentials"}, methods = {RequestMethod.POST, RequestMethod.OPTIONS})
//	@CrossOrigin(allowedHeaders = "*")
//    public ResponseEntity<?> uploadPicture(@PathVariable Long id, @RequestPart("image") @Valid @NotNull @NotBlank MultipartFile image) throws IOException {
//         
//        
//		try {
//			FileUploadUtil.saveFile(id, image);
//			return ResponseEntity.ok(new JSONObject()
//					.appendField("status", "uploaded"));			
//		} catch (IOException e) {
//			return ResponseEntity.ok(new JSONObject()
//					.appendField("status", "failed")
//					.appendField("error", e.toString()));
//		}
//         
//    }
}
