package com.ap.enotes_api_service.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {
	@Bean
	public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) {
		
		httpSecurity.csrf(csrf -> csrf.disable())
		.authorizeHttpRequests(req -> req.requestMatchers("/api/v1/auth/**", "/api/v1/home/**").permitAll()
		.anyRequest().authenticated())
		.httpBasic(Customizer.withDefaults());
		return httpSecurity.build();
	}
	
}
