package com.healthcareplatform.AuthenticationService.user;

import com.healthcareplatform.AuthenticationService.exception.EmailAlreadyExistsException;
import com.healthcareplatform.AuthenticationService.exception.ResourceNotFoundException;
import com.healthcareplatform.AuthenticationService.exception.UsernameAlreadyExistsException;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Clock;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class UserService {

    @Autowired
    private final UserRepository userRepository;
    @Autowired
    private final PasswordEncoder passwordEncoder;

    private final int credentialsExpiryMonths;
    private final int accountExpiryMonths;
    private static final String SIGNUP_METHOD_EMAIL = "EMAIL";

    public UserService(UserRepository userRepository,
                       PasswordEncoder passwordEncoder,
                       @Value("${user.credentials-expiry-months}") int credentialsExpiryMonths,
                       @Value("${user.account-expiry-months}") int accountExpiryMonths
                       ) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.credentialsExpiryMonths = credentialsExpiryMonths;
        this.accountExpiryMonths = accountExpiryMonths;
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

    /**
     * Retrieve a specific user by their userId.
     * <p>
     * Runs within a read-only transaction. Throws a ResourceNotFoundException
     * if no matching patient is found.
     *
     * @param userId Long of the patient to retrieve
     * @return UserResponse representing the found patient
     * @throws ResourceNotFoundException if patient with given id does not exist
     * @throws DataAccessException       if a database access error occurs
     */
    @Transactional(readOnly = true)
    public UserResponse getUserById(Long userId) {
        // attempt to find the Patient entity by ID
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("user not found with userId: " + userId));
        // convert found entity to DTO
        return mapToDto(user);
    }


    /**
     * Create a new user record in the database.
     * <p>
     * Ensures username/email uniqueness, encodes password, populates
     * default fields, and returns the saved user.
     *
     * @param req the validated request DTO
     * @return the newly created user's response DTO
     */
    @Transactional
    public UserResponse createUser(UserRequest req) {
        // 1. Uniqueness checks (race still possible; catch on save below)
        if (userRepository.existsByUsername(req.getUsername())) {
            throw new UsernameAlreadyExistsException(req.getUsername());
        }
        if (userRepository.existsByEmail(req.getEmail())) {
            throw new EmailAlreadyExistsException(req.getEmail());
        }

        // 2. Build the user with defaults
        User user = User.builder()
                .employeeId("Test-ID")
                .fullName(req.getFullName())
                .username(req.getUsername())
                .email(req.getEmail())
                .password(passwordEncoder.encode(req.getTemporaryPassword()))
                .hireDate(LocalDateTime.now())
                .accountNonLocked(true)
                .accountNonExpired(true)
                .credentialsNonExpired(true)
                .enabled(true)
                .credentialsExpiryDate(LocalDate.now().plusMonths(credentialsExpiryMonths))
                .accountExpiryDate(LocalDate.now().plusMonths(accountExpiryMonths))
                .signUpMethod(SIGNUP_METHOD_EMAIL)
                // leaving twoFactorSecret null until real secret is generated
                .build();

        User saved;
        try {
            saved = userRepository.save(user);
        } catch (DataIntegrityViolationException ex) {
            // translate any unique-index violation into a 409
            String msg = ex.getRootCause() != null
                    ? ex.getRootCause().getMessage()
                    : ex.getMessage();
            if (msg.contains("users_username_key")) {
                throw new UsernameAlreadyExistsException(req.getUsername());
            }
            if (msg.contains("users_email_key")) {
                throw new EmailAlreadyExistsException(req.getEmail());
            }
            throw ex;  // fallback to 500
        }

        return mapToDto(saved);
    }

    /**
     * Update an existing user’s details.
     *
     * <p>Fetches the User by {@code userId}, applies the changes from
     * the given {@link UserRequest}, and persists the updated entity.
     * Returns a {@link UserResponse} reflecting the new state.</p>
     *
     * @param userId       the database ID of the user to update
     * @param userRequest  the validated DTO containing new user data
     * @return the updated user as a response DTO
     * @throws ResourceNotFoundException      if no user exists with the given ID
     * @throws UsernameAlreadyExistsException if the new username is already taken by another user
     * @throws EmailAlreadyExistsException    if the new email is already taken by another user
     */
    public UserResponse updateUser(Long userId, @Valid UserRequest userRequest) {
        // TODO: Look up the existing User entity (or throw ResourceNotFoundException)
        // TODO: Check for username/email uniqueness (skip the same user)
        // TODO: Apply changes from userRequest to the User
        // TODO: Save and flush the updated User
        // TODO: Map to UserResponse and return
        return null;
    }

    /**
     * Delete a user by their database ID.
     *
     * <p>Performs a soft delete by disabling the account and setting
     * an expiry date, or a hard delete if your policy dictates.</p>
     *
     * @param userId  the database ID of the user to delete
     * @throws ResourceNotFoundException if no user exists with the given ID
     */
    public void deleteUser(Long userId) {
        // TODO: Retrieve the User (or throw ResourceNotFoundException)
        // TODO: Decide on soft-delete vs. hard-delete
        //       - For soft-delete: set enabled=false, accountExpiryDate=now(), save
        //       - For hard-delete: call userRepository.delete(user)
        // TODO: (Optional) Audit the deletion or emit an event
        return;
    }

    // Helper method to map user entity to userResponse
    private UserResponse mapToDto(User user) {
        return new UserResponse(
                user.getUserId(),
                user.getEmployeeId(),
                user.getFullName(),
                user.getUserName(),
                user.getEmail(),
                user.getHireDate(),
                user.getCredentialsExpiryDate(),
                user.getAccountExpiryDate(),
                user.getUserRoles(),
                user.getCreatedDate(),
                user.getUpdatedDate()
        );
    }

    // Helper method to map UserRequest to user entity
    private User mapToEntity(UserRequest dto) {
        User user = new User();
        user.setFullName(dto.getFullName());
        user.setUserName(dto.getUsername());
        user.setEmail(dto.getEmail());
        user.setPassword(dto.getTemporaryPassword());
        return user;
    }
}
