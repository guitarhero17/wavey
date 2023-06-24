package com.wavey.api

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class WaveyApiApplication_k {

    fun main(args: Array<String>) {
        // SpringApplication.run(WaveyApiApplication_k::class.java, *args)
        runApplication<WaveyApiApplication>(*args)
    }
}
