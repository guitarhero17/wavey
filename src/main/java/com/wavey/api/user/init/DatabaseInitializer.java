package com.wavey.api.user.init;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.wavey.api.user.data.User;
import com.wavey.api.user.data.UserRepository;
import com.wavey.api.user.data.Wave;
import com.wavey.api.user.data.WaveReaction;
import com.wavey.api.user.data.WaveReactionRepository;
import com.wavey.api.user.data.WaveRepository;

@Configuration
public class DatabaseInitializer {
	
	private static final Logger log = LoggerFactory.getLogger(DatabaseInitializer.class);
	
	@Bean
	CommandLineRunner initDatabase(UserRepository userRepository, WaveRepository waveRepository, WaveReactionRepository reactionRepository) {
		
		return args -> {
			log.info("Preloading Data...");
			if (userRepository != null) {
				
				for(User user: InitialData.getInitUsers()) {
					userRepository.save(user);
				}
				
				for (Wave wave: InitialData.getInitWaves()) {
					waveRepository.save(wave);
				}
				
				for (WaveReaction reaction: InitialData.getInitReactions()) {
					reactionRepository.save(reaction);
				}
				
				userRepository.findAll().forEach(user -> log.info("Preloaded User " + user.getUsername()));
				System.out.println("\n");
				waveRepository.findAll().forEach(wave -> log.info("Preloaded Wave: " + wave.getDescription()));
				System.out.println("\n");
				reactionRepository.findAll().forEach(reaction -> log.info("Preloaded a reaction " + reaction.getReaction() + " with id " + reaction.getId()));
			} else {
				System.out.println("Some of the repositories can not be reached");
			}
		};
	}
}
