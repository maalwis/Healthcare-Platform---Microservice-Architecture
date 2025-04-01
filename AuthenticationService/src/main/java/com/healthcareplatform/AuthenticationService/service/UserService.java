package com.healthcareplatform.AuthenticationService.service;

import com.healthcareplatform.AuthenticationService.dto.UserResponse;


public interface UserService {

    UserResponse createUser();

    UserResponse getUserById();

    UserResponse updateUser();

      void deleteUser();
}

