package com.ap.enotes_api_service.config;

import java.util.List;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import io.swagger.v3.oas.models.security.SecurityScheme.In;
import io.swagger.v3.oas.models.security.SecurityScheme.Type;
import io.swagger.v3.oas.models.servers.Server;

@Configuration
public class SwaggerConfig {

	@Bean
	public OpenAPI OpenApi() {
		
		OpenAPI openApi = new OpenAPI();
		
		
		Info info = new Info();
		info.setTitle("Enotes API");
		info.setDescription("API for Enotes");
		info.setVersion("1.0.0");
		info.setTermsOfService("http://hokageapsama.com");
		info.setContact(new Contact().email("anshumanpachauri26@gmail.com")
				.name("Anshuman Pachauri"));
		info.setLicense(new License().name("Enotes 1.0").url("http://hokageapsama.com"));
		
		
		
		List<Server> serverList = List.of( new Server().description("Dev").url("http://localhost:8080"),
		new Server().description("test").url("http://localhost:8081"),
		new Server().description("prod").url("http://localhost:8082"));
		
		
		
		SecurityScheme securityScheme = new SecurityScheme().name("Authorization")
				.scheme("bearer").type(Type.HTTP)
				.bearerFormat("JWT").in(In.HEADER);
		
		
		
		
		Components components = new Components().addSecuritySchemes("Token", securityScheme);
		
		
		
		
		openApi.setSecurity(List.of(new SecurityRequirement().addList("Token")));
		openApi.setComponents(components);
		openApi.setServers(serverList);
		openApi.setInfo(info);
		
		return openApi;
	}
	
}
