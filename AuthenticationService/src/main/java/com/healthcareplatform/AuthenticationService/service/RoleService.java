package com.healthcareplatform.AuthenticationService.service;

import com.healthcareplatform.AuthenticationService.dto.RoleResponse;

import java.util.List;


public interface RoleService {

    List<RoleResponse> getAllRoles();

    RoleResponse getRoleById();


    RoleResponse updateRole();
}

