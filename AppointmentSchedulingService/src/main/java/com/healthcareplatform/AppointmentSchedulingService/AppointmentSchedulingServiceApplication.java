package com.healthcareplatform.AppointmentSchedulingService;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
public class AppointmentSchedulingServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(AppointmentSchedulingServiceApplication.class, args);
	}

}
