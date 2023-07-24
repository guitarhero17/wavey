package com.wavey.api.init;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.wavey.api.data.UserRepository;
import com.wavey.api.data.WaveReactionRepository;
import com.wavey.api.data.WaveRepository;

import java.util.Arrays;

@Configuration
public class DatabaseInitializer {
	
	private static final Logger log = LoggerFactory.getLogger(DatabaseInitializer.class);
	
	@Bean
	CommandLineRunner initDatabase(UserRepository userRepository, WaveRepository waveRepository, WaveReactionRepository reactionRepository) {
		
		return args -> {
			log.info("Preloading Data...");
			if (userRepository != null) {

				userRepository.saveAll(Arrays.asList(InitialData.getInitUsers()));

				waveRepository.saveAll(Arrays.asList(InitialData.getInitWaves()));

				reactionRepository.saveAll(Arrays.asList(InitialData.getInitReactions()));
				
				userRepository.findAll().forEach(user -> log.info("Preloaded User " + user.getUsername()));
				System.out.println("\n");
				waveRepository.findAll().forEach(wave -> log.info("Preloaded Wave: " + wave.getTitle()));
				System.out.println("\n");
				reactionRepository.findAll().forEach(reaction -> log.info("Preloaded a reaction " + reaction.getReaction() + " with id " + reaction.getId()));
			} else {
				System.out.println("Some of the repositories can not be reached");
			}
		};
	}
}
