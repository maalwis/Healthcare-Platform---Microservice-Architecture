package com.healthcareplatform.AuthenticationService.controller;


import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/api/v1/users/{userId}/profile")
public class UserProfileController {

    @GetMapping
    public ResponseEntity<?> getUserProfile() {
        try {
            // Delegating to the ProfileService to retrieve user profile details.
            return ResponseEntity.ok("User profile for user " + "userId");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("User profile not found.");
        }
    }


    @PutMapping
    public ResponseEntity<?> updateUserProfile() {
        try {
            // Delegating to the ProfileService to update user profile details.
            return ResponseEntity.ok("User profile updated successfully.");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("User profile update failed.");
        }
    }
}

