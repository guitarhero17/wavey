package com.wavey.api.user.web;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.IanaLinkRelations;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.wavey.api.user.business.UserService;
import com.wavey.api.user.data.User;
import com.wavey.api.user.web.hateoas.UserModelAssembler;


/**
 * Created in compliance with the "Building REST services" guide
 * <p>{@link https://spring.io/guides/tutorials/rest/}</p>
 * @author guitarhero17
 *
 */
@RestController
@CrossOrigin
public class UserController {

	@Autowired
	private UserService userService;
	
	@Autowired
	private UserModelAssembler assembler;
	
	@GetMapping("/users")
	@CrossOrigin(allowedHeaders = "*")
	public CollectionModel<EntityModel<User>> getAllUsers() {
		
		List<EntityModel<User>> userModels = userService.getUsers().stream()
				.map(assembler::toModel).collect(Collectors.toList());
		
		return CollectionModel.of(userModels, linkTo(methodOn(UserController.class).getAllUsers()).withSelfRel());			
	}
	
	
	@GetMapping("/users/{id}")
	@CrossOrigin(allowedHeaders = "*")
	public EntityModel<User> getUser(@PathVariable Long id) {		
		return assembler.toModel(userService.getUser(id));
	}
	
	@PostMapping("/users")
	@CrossOrigin(allowedHeaders = "*")
	public ResponseEntity<?> createUser(@RequestBody User user) {
		
		final String username = user.getUsername();
		final String name = user.getName();
		final String password = user.getPassword();
		
		EntityModel<User> entityModel = assembler.toModel(userService.createUser(username, name, password));
		
		return ResponseEntity
				.created(entityModel.getRequiredLink(IanaLinkRelations.SELF).toUri()).body(entityModel);
		
	}
	
	@PutMapping("/users/{id}")
	@CrossOrigin(allowedHeaders = "*")
	public ResponseEntity<?> updateUser(@PathVariable Long id, @RequestBody User newUser) {
		
		EntityModel<User> entityModel = assembler.toModel(userService.updateUser(id, newUser));
		
		return ResponseEntity
				.created(entityModel.getRequiredLink(IanaLinkRelations.SELF).toUri()).body(entityModel);
	}
	
	@DeleteMapping("/users/{id}")
	@CrossOrigin(allowedHeaders = "*")
	public ResponseEntity<?> deleteUser(@PathVariable Long id) {
		
		userService.deleteUser(id);
		
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
