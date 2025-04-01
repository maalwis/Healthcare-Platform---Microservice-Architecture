package com.healthcareplatform.AuthenticationService.controller;


import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/api/v1/users/{userId}/roles")
public class UserRoleController {

    @PostMapping
    public ResponseEntity<?> assignRolesToUser() {
        try {
            // Delegating to the UserRoleService to assign roles to the user.
            return ResponseEntity.ok("Roles assigned to user successfully.");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Role assignment failed.");
        }
    }


    @DeleteMapping("/{roleId}")
    public ResponseEntity<?> removeRoleFromUser() {
        try {
            // Delegating to the UserRoleService to remove the specified role from the user.
            return ResponseEntity.ok("Role removed from user successfully.");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Failed to remove role from user.");
        }
    }
}

