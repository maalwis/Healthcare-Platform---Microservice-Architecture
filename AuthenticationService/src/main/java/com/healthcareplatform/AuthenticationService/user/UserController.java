package com.healthcareplatform.AuthenticationService.user;

import com.healthcareplatform.AuthenticationService.dto.UserDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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

     * @return ResponseEntity containing a list of userDto objects and HTTP 200 status.
     */
    @GetMapping
    public ResponseEntity<?> getAllUsers() {
        // TODO Delegate to UserService to retrieve all users
        List<UserResponse> users = userService.getAllUsers();
        return ResponseEntity.ok(users);
    }
}
