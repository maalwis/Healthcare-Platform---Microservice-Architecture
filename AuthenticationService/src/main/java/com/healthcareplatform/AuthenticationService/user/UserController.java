package com.healthcareplatform.AuthenticationService.user;

import com.healthcareplatform.AuthenticationService.dto.UserDTO;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/users")
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
    public ResponseEntity<UserResponse> createPatient(@Valid @RequestBody UserRequest userRequest) {
        // Delegate to UserService to create a new patient
        UserResponse created = userService.createUser(userRequest);

        return ResponseEntity.ok(created);
    }

    /**
     * Update an existing patient's details.
     *
     * @param userId Unique identifier of the patient (path variable)
     * @param userRequest Payload containing updated data (validated request body)
     * @return ResponseEntity containing updated PatientDto and HTTP 200 status.
     */
    @PutMapping("/{userId}")
    public ResponseEntity<UserResponse> updatePatient(
            @PathVariable Long userId,
            @Valid @RequestBody UserRequest userRequest) {
        // TODO Delegate to UserService to update user details
        UserResponse updated = userService.updateUser(userId, userRequest);
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
