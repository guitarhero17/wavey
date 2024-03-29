package com.wavey.api.security.business;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.wavey.api.user.data.UserRepository;
import com.wavey.api.security.data.AuthenticatedUser;
import com.wavey.api.user.data.User;

@Service
public class WaveyUserDetailsService implements UserDetailsService {

	@Autowired
	private UserRepository userRepository;
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		User user = userRepository.findByUsername(username);
		
		if (user == null) {
			throw new UsernameNotFoundException("The user " + username + " does not exist.");
		} else {
			return new AuthenticatedUser(user);
		}
	}

}
