package com.healthcareplatform.AuthenticationService.service;

import com.healthcareplatform.AuthenticationService.dto.UserProfileResponse;


public interface UserProfileService {

    UserProfileResponse getUserProfile();

    UserProfileResponse updateUserProfile();
}

