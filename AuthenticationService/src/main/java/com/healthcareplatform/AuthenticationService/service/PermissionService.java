package com.healthcareplatform.AuthenticationService.service;

import com.healthcareplatform.AuthenticationService.dto.PermissionResponse;

import java.util.List;


public interface PermissionService {

    List<PermissionResponse> getAllPermissions();


    PermissionResponse getPermissionById();

    PermissionResponse updatePermission();
}

