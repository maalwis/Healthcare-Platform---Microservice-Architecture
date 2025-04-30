package com.healthcareplatform.BillingClaimsService;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
public class BillingClaimsServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(BillingClaimsServiceApplication.class, args);
	}

}
