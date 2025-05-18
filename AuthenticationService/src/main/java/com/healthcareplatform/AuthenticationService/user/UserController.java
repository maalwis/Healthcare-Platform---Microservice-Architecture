package com.healthcareplatform.AuthenticationService.user;

import com.healthcareplatform.AuthenticationService.dto.*;
import com.healthcareplatform.AuthenticationService.userdetails.UserDetailsImpl;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/user")
public class UserController {

    @Autowired
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    /**
     * Retrieve a list of all users.
     *
     * @return ResponseEntity containing a list of UserResponse objects and HTTP 200 status.
     */
    @GetMapping
    public ResponseEntity<?> getAllUsers() {
        // Delegate to UserService to retrieve all users
        List<UserResponse> UserResponses = userService.getAllUsers();
        return ResponseEntity.ok(UserResponses);
    }

    /**
     * Retrieve details for a specific user (employee) by ID.
     *
     * @param userId Unique identifier of the patient (path variable)
     * @return ResponseEntity containing UserResponse and HTTP 200 status if found;
     *         otherwise exception is propagated (e.g., 404 Not Found).
     */
    @GetMapping("/{userId}")
    public ResponseEntity<?> getUser(@PathVariable Long userId) {
        // Delegate to UserService to retrieve a specific user
        UserResponse userResponse= userService.getUserById(userId);
        return ResponseEntity.ok(userResponse);
    }

    /**
     * Create a new user record.
     *
     * @param userRequest Payload containing patient data (validated request body)
     * @return ResponseEntity containing created PatientDto, HTTP 201 status.
     */
    @PostMapping
    public ResponseEntity<UserResponse> createUser(@Valid @RequestBody UserRequest userRequest) {
        // Delegate to UserService to create a new patient
        UserResponse created = userService.createUser(userRequest);

        return ResponseEntity.ok(created);
    }

    /**
     * Updates the authenticated user's username.
     *
     * @param userDetails      the authenticated principal (current user context)
     * @param updateUsername   DTO containing the new, validated username
     * @return the updated {@link UserResponse} wrapped in a 200 OK
     */
    @PutMapping("/profile/username")
    public ResponseEntity<UserResponse> updateUsername(@AuthenticationPrincipal UserDetailsImpl userDetails,
                                                       @Valid @RequestBody UpdateUsername updateUsername) {
        // TODO Delegate to UserService to update user details
        UserResponse updated = userService.updateUsername(userDetails, updateUsername);
        return ResponseEntity.ok(updated);
    }

    /**
     * Updates the authenticated user's full name.
     *
     * @param userDetails      the authenticated principal (current user context)
     * @param updateFullName   DTO containing the new, validated full name
     * @return the updated {@link UserResponse} wrapped in a 200 OK
     */
    @PutMapping("/profile/full-name")
    public ResponseEntity<UserResponse> updateFullName(@AuthenticationPrincipal UserDetailsImpl userDetails,
                                                       @Valid @RequestBody UpdateFullName updateFullName) {
        // TODO Delegate to UserService to update user details
        UserResponse updated = userService.updateFullName(userDetails, updateFullName);
        return ResponseEntity.ok(updated);
    }

    /**
     * Updates the authenticated user's email address.
     *
     * @param userDetails   the authenticated principal (current user context)
     * @param updateEmail   DTO containing the new, validated email address
     * @return the updated {@link UserResponse} wrapped in a 200 OK
     */
    @PutMapping("/profile/email")
    public ResponseEntity<UserResponse> updateEmail(@AuthenticationPrincipal UserDetailsImpl userDetails,
                                                       @Valid @RequestBody UpdateEmail updateEmail) {
        // TODO Delegate to UserService to update user details
        UserResponse updated = userService.updateEmail(userDetails, updateEmail);
        return ResponseEntity.ok(updated);
    }

    /**
     * Changes the authenticated user's password.
     *
     * @param userDetails      the authenticated principal (current user context)
     * @param updatePassword   DTO containing the new, validated password
     * @return the updated {@link UserResponse} wrapped in a 200 OK
     */
    @PutMapping("/profile/password")
    public ResponseEntity<UserResponse> updatePassword(@AuthenticationPrincipal UserDetailsImpl userDetails,
                                                       @Valid @RequestBody UpdatePassword updatePassword) {
        // TODO Delegate to UserService to update user details
        UserResponse updated = userService.updatePassword(userDetails, updatePassword);
        return ResponseEntity.ok(updated);
    }

    /**
     * Delete a user record by useId.

     * @param userId Unique identifier of the patient (path variable)
     * @return ResponseEntity with HTTP 204 No Content on successful deletion.
     */
    @DeleteMapping("/{userId}")
    public ResponseEntity<Void> deletePatient(@PathVariable Long userId) {
        // TODO Delegate to UserService to delete patient
        userService.deleteUser(userId);
        return ResponseEntity.noContent().build();
    }

}
