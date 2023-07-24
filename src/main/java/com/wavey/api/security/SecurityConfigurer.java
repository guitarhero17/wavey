package com.wavey.api.security;


import com.wavey.api.security.web.WaveyBasicAuthenticationEntryPoint;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@EnableWebSecurity
@Configuration
public class SecurityConfigurer {

	private final WaveyBasicAuthenticationEntryPoint authenticationEntryPoint = new WaveyBasicAuthenticationEntryPoint();

	@Bean
	public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
		// Here we had "cors().and()"
		http.csrf(AbstractHttpConfigurer::disable).cors(Customizer.withDefaults())
// Temporarily disabling authentication
//
//				.authorizeHttpRequests(authorize -> authorize
//						.requestMatchers("/users").permitAll()
//						.anyRequest().authenticated()
//				)
				.httpBasic(h -> h.authenticationEntryPoint(authenticationEntryPoint));

		http.requiresChannel(channel -> channel.anyRequest().requiresSecure());
		return http.build();
	}

	@Bean
	public PasswordEncoder encoder() {
		return new BCryptPasswordEncoder();
	}
}
