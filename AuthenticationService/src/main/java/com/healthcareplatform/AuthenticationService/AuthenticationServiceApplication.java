package com.healthcareplatform.AuthenticationService;

import com.healthcareplatform.AuthenticationService.config.database.InitializationService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;


@SpringBootApplication
public class AuthenticationServiceApplication {

	public static void main(String[] args) {

		SpringApplication.run(AuthenticationServiceApplication.class, args);


	}

	@Bean
	public CommandLineRunner init(InitializationService initializationService ) {
		return args -> {
			initializationService.initializeRolesPermissionsAndUsers();
		};
	}

}
