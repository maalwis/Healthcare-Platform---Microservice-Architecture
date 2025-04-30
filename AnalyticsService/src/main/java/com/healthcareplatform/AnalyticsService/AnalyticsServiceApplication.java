package com.healthcareplatform.AnalyticsService;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
public class AnalyticsServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(AnalyticsServiceApplication.class, args);
	}

}
