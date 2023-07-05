package com.wavey.api.user.business;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.fge.jsonpatch.JsonPatch;
import com.github.fge.jsonpatch.JsonPatchException;
import com.github.fge.jsonpatch.mergepatch.JsonMergePatch;
import com.wavey.api.user.exceptions.FieldCanNotBePatchedException;
import com.wavey.api.user.exceptions.UserAlreadyExistsException;
import com.wavey.api.user.init.DatabaseInitializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.wavey.api.user.data.User;
import com.wavey.api.user.data.UserRepository;
import com.wavey.api.user.exceptions.UserNotFoundException;
import org.springframework.web.server.ResponseStatusException;

@Service
public class UserService {

	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private PasswordEncoder passwordEncoder;

	private final ObjectMapper objectMapper = new ObjectMapper();

	private static final Logger log = LoggerFactory.getLogger(UserService.class);

	public List<User> getUsers() {
		return new ArrayList<>(userRepository.findAll());
	}
	
	
	public User getUser(String username) {
		return userRepository.findByUsername(username).orElseThrow(() -> new UserNotFoundException(username));
	}
	
	
	public User createUser(User user) {
		final String username = user.getUsername();

		Optional<User> potentiallyPresentUser = userRepository.findByUsername(username);

		if (potentiallyPresentUser.isPresent()) {
			throw new UserAlreadyExistsException();
		}

		final String name = user.getName();
		final String password = user.getPassword();

		User newUser = new User();
		newUser.setUsername(username);
		newUser.setName(name);
		newUser.setPassword(passwordEncoder.encode(password));
		
		return userRepository.save(newUser);
	}

//	public User replaceUser(String username, User newUser) {
//
//		return userRepository.findByUsername(username).map(user -> {
//
//			user.setName(newUser.getName());
//			user.setCity(newUser.getCity());
//			user.setInstrumentPrimary(newUser.getInstrumentPrimary());
//			user.setInstrumentsSecondary(newUser.getInstrumentsSecondary());
//			user.setLookingFor(newUser.getLookingFor());
//
//			return userRepository.save(user);
//		}).orElseGet(() -> {
//			newUser.setUsername(username);
//			return userRepository.save(newUser);
//		});
//	}

	// In reference to https://github.com/bijukunjummen/coroutine-cities-demo/blob/main/src/test/kotlin/samples/geo/patch/BookController.kt
	public void updateUser(String username, JsonNode patch) {
			User user = userRepository.findByUsername(username).orElseThrow(() -> new UserNotFoundException(username));
			String userPassword = user.getPassword();

		if (patch.has("username")) {
			throw new FieldCanNotBePatchedException("username");
		}

		try {
			JsonNode original = objectMapper.valueToTree(user);
			JsonNode patched = JsonMergePatch.fromJson(patch).apply(original);
			User patchedUser = objectMapper.treeToValue(patched, User.class);
			patchedUser.setPassword(userPassword);

			userRepository.save(patchedUser);
		} catch (JsonProcessingException e) {
			log.error(e.getMessage());
			throw new RuntimeException("Could not convert json back to User");
		} catch (JsonPatchException e) {
			log.error(e.getMessage());
			throw new RuntimeException(e);
		}
	}
	
	public void deleteUser(String username) {
		userRepository.deleteByUsername(username);
	}
}
