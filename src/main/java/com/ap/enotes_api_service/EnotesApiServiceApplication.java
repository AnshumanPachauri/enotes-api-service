package com.ap.enotes_api_service;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

import com.ap.enotes_api_service.config.AuditAwareConfig;

@SpringBootApplication
@EnableJpaAuditing(auditorAwareRef = "auditAware") //this will help Spring to find the bean class whenever the Audit aware is searched
public class EnotesApiServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(EnotesApiServiceApplication.class, args);
	}

}
