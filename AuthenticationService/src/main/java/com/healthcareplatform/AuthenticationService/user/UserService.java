package com.healthcareplatform.AuthenticationService.user;

import com.healthcareplatform.AuthenticationService.dto.*;
import com.healthcareplatform.AuthenticationService.exception.EmailAlreadyExistsException;
import com.healthcareplatform.AuthenticationService.exception.ResourceNotFoundException;
import com.healthcareplatform.AuthenticationService.exception.UsernameAlreadyExistsException;
import com.healthcareplatform.AuthenticationService.userdetails.UserDetailsImpl;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserService {

    private static final String SIGNUP_METHOD_EMAIL = "EMAIL";
    @Autowired
    private final UserRepository userRepository;
    @Autowired
    private final PasswordEncoder passwordEncoder;
    private final int credentialsExpiryMonths;
    private final int accountExpiryMonths;

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
     */
    public UserResponse getUserById(Long userId) {
        // call the helper method to retrieve the user
        User user = getUserByIdHelper(userId);
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
     * {@inheritDoc}
     * <p>Ensures the new username is unique before updating.</p>
     *
     * @param userDetails     the authenticated principal
     * @param updateUsername  validated DTO with the new username
     * @return updated user as {@link UserResponse}
     * @throws UsernameAlreadyExistsException if the username is taken
     * @throws ResourceNotFoundException      if the user does not exist
     * @throws DataAccessException            on DB errors
     */
    @Transactional
    public UserResponse updateUsername(@AuthenticationPrincipal UserDetailsImpl userDetails,
                                       @Valid UpdateUsername updateUsername) {
        String newUserName = updateUsername.getUsername();

        // 1. Uniqueness checks (race still possible; catch on save below)
        if (userRepository.existsByUsername(newUserName)) {
            throw new UsernameAlreadyExistsException(newUserName);
        }

        User user = getUserByIdHelper(userDetails.getId());
        user.setUserName(newUserName);

        User saved = userRepository.save(user);

        return mapToDto(saved);
    }

    /**
     * Updates the user's full name.
     *
     * @param userDetails     the authenticated principal
     * @param updateFullName  validated DTO with the new full name
     * @return updated user as {@link UserResponse}
     * @throws ResourceNotFoundException if the user does not exist
     * @throws DataAccessException       on DB errors
     */
    @Transactional
    public UserResponse updateFullName(@AuthenticationPrincipal UserDetailsImpl userDetails,
                                       @Valid UpdateFullName updateFullName) {
        String newFullName = updateFullName.getFullName();

        User user = getUserByIdHelper(userDetails.getId());
        user.setFullName(newFullName);

        User saved = userRepository.save(user);

        return mapToDto(saved);
    }

    /**
     * Changes the user's password (stored as a BCrypt hash).
     *
     * @param userDetails     the authenticated principal
     * @param updatePassword  validated DTO with the new password
     * @return updated user as {@link UserResponse}
     * @throws ResourceNotFoundException if the user does not exist
     * @throws DataAccessException       on DB errors
     */
    @Transactional
    public UserResponse updatePassword(@AuthenticationPrincipal UserDetailsImpl userDetails,
                                       @Valid  UpdatePassword updatePassword) {

        User user = getUserByIdHelper(userDetails.getId());
        user.setPassword(passwordEncoder.encode(updatePassword.getNewPassword()));

        User saved = userRepository.save(user);

        return mapToDto(saved);
    }

    /**
     * Updates the user's email after ensuring uniqueness.
     *
     * @param userDetails  the authenticated principal
     * @param updateEmail  validated DTO with the new email
     * @return updated user as {@link UserResponse}
     * @throws EmailAlreadyExistsException if the email is already used
     * @throws ResourceNotFoundException   if the user does not exist
     * @throws DataAccessException         on DB errors
     */
    @Transactional
    public UserResponse updateEmail(@AuthenticationPrincipal UserDetailsImpl userDetails,
                                       @Valid UpdateEmail updateEmail) {

        String newEmail = updateEmail.getEmail();

        if (userRepository.existsByEmail(updateEmail.getEmail())) {
            throw new EmailAlreadyExistsException(newEmail);
        }

        User user = getUserByIdHelper(userDetails.getId());
        user.setEmail(newEmail);

        User saved = userRepository.save(user);

        return mapToDto(saved);
    }

    /**
     * Delete a user by their database ID.
     *
     * <p>Performs a soft delete by disabling the account and setting
     * an expiry date, or a hard delete if your policy dictates.</p>
     *
     * @param userId the database ID of the user to delete
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

    /**
     * Fetches a {@link User} by ID in a read-only transaction.
     *
     * @param userId the database ID of the user
     * @return the matching {@link User} entity
     * @throws ResourceNotFoundException if no user is found
     * @throws DataAccessException       on DB errors
     */
    @Transactional(readOnly = true)
    private User getUserByIdHelper(Long userId) {
        // attempt to find the Patient entity by ID
        return userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("user not found with userId: " + userId));
    }

    /**
     * Maps a {@link User} entity to its external {@link UserResponse} representation.
     *
     * @param user the entity to map
     * @return the DTO containing user data for clients
     */
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

    /**
     * Maps a creation or update request DTO to a new User entity.
     * <p>
     * This helper method populates a fresh User instance with values
     * from the provided {@link UserRequest} DTO. It does not perform any
     * persistence or validation beyond the DTO’s own constraints.
     * </p>
     *
     * @param dto the incoming request containing user details:
     *            full name, username, email, and temporary password
     * @return a new {@link User} entity ready for persistence
     */
    private User mapToEntity(UserRequest dto) {
        User user = new User();
        user.setFullName(dto.getFullName());
        user.setUserName(dto.getUsername());
        user.setEmail(dto.getEmail());
        user.setPassword(dto.getTemporaryPassword());
        return user;
    }
}
