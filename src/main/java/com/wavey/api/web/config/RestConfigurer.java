package com.wavey.api.web.config;

import com.wavey.api.data.Wave;
import com.wavey.api.data.WaveReaction;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.rest.webmvc.config.RepositoryRestConfigurer;

@Configuration
public class RestConfigurer {
    @Bean
    public RepositoryRestConfigurer repositoryRestConfigurer()
    {
        return RepositoryRestConfigurer.withConfig(config -> {
            config.exposeIdsFor(Wave.class, WaveReaction.class);
        });
    }
}
