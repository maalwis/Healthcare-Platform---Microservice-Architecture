package com.healthcareplatform.StaffService;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
public class StaffServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(StaffServiceApplication.class, args);
	}

}
