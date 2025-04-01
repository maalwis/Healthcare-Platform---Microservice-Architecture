package com.healthcareplatform.AuthenticationService.controller;


import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/api/v1/roles/{roleId}/permissions")
public class RolePermissionController {

    @PostMapping
    public ResponseEntity<?> assignPermissionsToRole( ) {
        try {
            // Delegating to the RolePermissionService to assign permissions to the role.
            return ResponseEntity.ok("Permissions assigned to role successfully.");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Permission assignment failed.");
        }
    }


    @DeleteMapping("/{permissionId}")
    public ResponseEntity<?> removePermissionFromRole() {
        try {
            // Delegating to the RolePermissionService to remove the permission from the role.
            return ResponseEntity.ok("Permission removed from role successfully.");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Failed to remove permission from role.");
        }
    }
}

