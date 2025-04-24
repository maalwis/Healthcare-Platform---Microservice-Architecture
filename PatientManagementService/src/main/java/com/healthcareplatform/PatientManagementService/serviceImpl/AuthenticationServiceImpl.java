package com.healthcareplatform.PatientManagementService.serviceImpl;

import com.healthcareplatform.PatientManagementService.dto.UserDTO;
import com.healthcareplatform.PatientManagementService.service.AuthenticationService;
import com.healthcareplatform.PatientManagementService.userClient.AuthenticationClient;
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
