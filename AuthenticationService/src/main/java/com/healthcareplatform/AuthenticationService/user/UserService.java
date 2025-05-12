package com.healthcareplatform.AuthenticationService.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserService {

    @Autowired
    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    /**
     * Retrieve all users from the database.
     * <p>
     * Runs within a read-only transaction for performance optimization.
     *
     * @return List of UserDto objects representing all users
     * @throws DataAccessException if a database access error occurs
     */
    @Transactional(readOnly = true)
    public List<UserResponse> getAllUsers() {
        // fetch all Patient entities
        List<User> users = userRepository.findAll();
        // map each user entity to userResponse and collect in a list
        return users.stream()
                .map(this::mapToDto)
                .collect(Collectors.toList());
    }

    // Helper method to map user entity to userResponse
    private UserResponse mapToDto(User user) {

        return new UserResponse(user.getUserId(), user.getEmployeeId(),
                user.getFullName(), user.getUserName(), user.getEmail(),
                user.getHireDate(), user.getCredentialsExpiryDate(),
                user.getAccountExpiryDate(),user.getUserRoles(),
                user.getCreatedDate(), user.getUpdatedDate());

    }

}
