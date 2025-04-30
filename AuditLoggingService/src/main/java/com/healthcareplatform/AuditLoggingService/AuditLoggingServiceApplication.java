package com.healthcareplatform.AuditLoggingService;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
public class AuditLoggingServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(AuditLoggingServiceApplication.class, args);
	}

}
