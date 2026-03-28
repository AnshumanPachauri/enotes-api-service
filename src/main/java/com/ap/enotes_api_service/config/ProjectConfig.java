package com.ap.enotes_api_service.config;

import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ProjectConfig {

	@Bean
	public ModelMapper mapper() {
		//this mapper can map objects data from one to another.
		return new ModelMapper();
	}
	
}
