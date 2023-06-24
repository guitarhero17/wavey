package com.wavey.api.user.init

import com.wavey.api.user.data.*
import org.slf4j.LoggerFactory
import org.springframework.boot.CommandLineRunner
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import java.util.*
import java.util.function.Consumer

@Configuration
open class DatabaseInitializer_k {
    @Bean
    open fun initDatabase(
        userRepository: UserRepository?,
        waveRepository: WaveRepository,
        reactionRepository: WaveReactionRepository
    ): CommandLineRunner {
        return CommandLineRunner { args: Array<String?>? ->
            log.info("Preloading Data...")
            if (userRepository != null) {
                userRepository.saveAll(listOf(*InitialData.getInitUsers()))
                waveRepository.saveAll(listOf(*InitialData.getInitWaves()))
                reactionRepository.saveAll(listOf(*InitialData.getInitReactions()))
                userRepository.findAll().forEach(Consumer { user: User -> log.info("Preloaded User " + user.username) })
                println("\n")
                waveRepository.findAll()
                    .forEach(Consumer { wave: Wave -> log.info("Preloaded Wave: " + wave.description) })
                println("\n")
                reactionRepository.findAll()
                    .forEach(Consumer { reaction: WaveReaction -> log.info("Preloaded a reaction " + reaction.reaction + " with id " + reaction.id) })
            } else {
                println("Some of the repositories can not be reached")
            }
        }
    }

    companion object {
        private val log = LoggerFactory.getLogger(DatabaseInitializer_k::class.java)
    }
}
