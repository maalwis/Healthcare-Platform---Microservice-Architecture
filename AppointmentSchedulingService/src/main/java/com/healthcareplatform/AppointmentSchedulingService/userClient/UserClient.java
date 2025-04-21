package com.healthcareplatform.AppointmentSchedulingService.userClient;


import com.healthcareplatform.AppointmentSchedulingService.dto.UserDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;

@FeignClient(name = "AuthenticationService")
public interface UserClient {

    @GetMapping("/api/v1/private/validateToken")
    ResponseEntity<UserDTO> getUserDto(@RequestHeader("Authorization") String authHeader);
}
