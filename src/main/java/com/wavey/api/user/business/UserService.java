package com.wavey.api.user.business;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.wavey.api.user.data.User;
import com.wavey.api.user.data.UserRepository;
import com.wavey.api.user.exceptions.UserNotFoundException;

@Service
public class UserService {

	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	public List<User> getUsers() {
		List<User> users = new ArrayList<User>();
		userRepository.findAll().forEach(users::add);	
		return users;
	}
	
	
	public User getUser(Long userId) {
		return userRepository.findById(userId).orElseThrow(() -> new UserNotFoundException(userId));
	}
	
	
	public User createUser(String username, String name, String password) {
		User newUser = new User();
		newUser.setUsername(username);
		newUser.setName(name);
		newUser.setPassword(passwordEncoder.encode(password));
		
		return userRepository.save(newUser);
	}
	
	public User updateUser(Long id, User newUser) {

		User updatedUser = userRepository.findById(id).map(user -> {
			
			user.setName(newUser.getName());
			user.setCity(newUser.getCity());
			user.setInstrumentPrimary(newUser.getInstrumentPrimary());
			user.setInstrumentsSecondary(newUser.getInstrumentsSecondary());
			user.setLookingFor(newUser.getLookingFor());
			
			return userRepository.save(user);
		}).orElseGet(() -> {
			newUser.setId(id);
			return userRepository.save(newUser);
		});
		
		return updatedUser;
		
	}
	
	public void deleteUser(Long userId) {
		userRepository.deleteById(userId);
	}
}
