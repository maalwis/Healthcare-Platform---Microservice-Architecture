package com.healthcareplatform.PatientManagementService;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
public class PatientManagementServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(PatientManagementServiceApplication.class, args);
	}

}
