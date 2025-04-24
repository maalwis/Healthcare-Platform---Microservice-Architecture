package com.healthcareplatform.AppointmentSchedulingService.serviceImpl;


import com.healthcareplatform.AppointmentSchedulingService.dto.UserDTO;
import com.healthcareplatform.AppointmentSchedulingService.service.AuthenticationService;
import com.healthcareplatform.AppointmentSchedulingService.userClient.AuthenticationClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class AuthenticationServiceImpl implements AuthenticationService {
    @Autowired
    private AuthenticationClient authenticationClient ;

    @Override
    public ResponseEntity<UserDTO> callAuth(String jwt) {
        return authenticationClient.getUserDto("Bearer " + jwt);
    }
}
