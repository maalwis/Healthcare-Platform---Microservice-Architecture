package com.healthcareplatform.AuthenticationService.user;

import com.healthcareplatform.AuthenticationService.dto.UserResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class UserServiceTest {

    private static final String SIGNUP_METHOD_EMAIL = "EMAIL";
    private UserService userServiceTest;
    @Mock
    private UserRepository userRepository;
    @Mock
    private PasswordEncoder passwordEncoder;
    private int credentialsExpiryMonths;
    private int accountExpiryMonths;

    private final Instant fixedInstant = Instant.parse("2025-05-12T10:00:00Z");

    @BeforeEach
    public void setUp() {
        userServiceTest = new UserService(userRepository, passwordEncoder,
                credentialsExpiryMonths, accountExpiryMonths );
    }

    @Test
    public void getAllUsersTest() {
        // 1. Prepare a fake User with all fields set
        User user = new User();
        user.setUserId(42L);
        user.setEmployeeId("emp-42");
        user.setFullName("Dora Explorer");
        user.setUserName("dora");
        user.setEmail("dora@example.com");

        LocalDateTime now = LocalDateTime.of(2025, 5, 12, 10, 0);
        user.setHireDate(now);

        // Account status flags
        user.setAccountNonLocked(true);
        user.setAccountNonExpired(true);
        user.setCredentialsNonExpired(true);
        user.setEnabled(true);

        // Expiry dates
        user.setCredentialsExpiryDate(LocalDate.now().plusMonths(3));
        user.setAccountExpiryDate(LocalDate.now().plusMonths(3));

        // 2FA settings
        user.setTwoFactorSecret("manual");
        user.setTwoFactorEnabled(false);

        // Sign-up method
        user.setSignUpMethod("custom email");

        // roles
        user.setUserRoles(Set.of(new UserRole()));

        // 2. Stub the repository to return our fake user
        when(userRepository.findAll())
                .thenReturn(Collections.singletonList(user));

        // 3. Execute the service call
        List<UserResponse> result = userServiceTest.getAllUsers();

        // 4. Verify that findAll() was called exactly once
        verify(userRepository, times(1)).findAll();

        // 5. Assert that the DTO has all the same values
        assertEquals(1, result.size());
        UserResponse userResponse = result.get(0);

        assertEquals(42L, userResponse.getUserId());
        assertEquals("emp-42", userResponse.getEmployeeId());
        assertEquals("Dora Explorer", userResponse.getFullName());
        assertEquals("dora", userResponse.getUsername());
        assertEquals("dora@example.com", userResponse.getEmail());

        assertEquals(now, userResponse.getHireDate());


        // Expiry dates
        assertEquals(LocalDate.now().plusMonths(3), userResponse.getCredentialsExpiryDate());
        assertEquals(LocalDate.now().plusMonths(3), userResponse.getAccountExpiryDate());


        // mapped roles into the DTO:
        assertNotNull(userResponse.getUserRoles());
        assertEquals(1, userResponse.getUserRoles().size());
    }

    @Test
    void getUserByIdTest() {
        // 1. Prepare a fake User
        User user = User.builder()
                .userId(42L)
                .employeeId("emp-42")
                .fullName("Dora Explorer")
                .username("dora")
                .email("dora@example.com")
                .hireDate(LocalDateTime.ofInstant(fixedInstant, ZoneOffset.UTC))
                .accountNonLocked(true)
                .accountNonExpired(true)
                .credentialsNonExpired(true)
                .enabled(true)
                .credentialsExpiryDate(LocalDate.of(2025,5,12).plusMonths(3))
                .accountExpiryDate(LocalDate.of(2025,5,12).plusMonths(12))
                .twoFactorSecret("manual")
                .isTwoFactorEnabled(false)
                .signUpMethod("custom email")
                .build();

        // 2. Stub repository
        when(userRepository.findById(42L)).thenReturn(Optional.of(user));

        // 3. Execute
        UserResponse resp = userServiceTest.getUserById(42L);

        // 4. Verify
        verify(userRepository, times(1)).findById(42L);

        // 5. Assert mapping
        assertEquals(42L, resp.getUserId());
        assertEquals("emp-42", resp.getEmployeeId());
        assertEquals("Dora Explorer", resp.getFullName());
        assertEquals("dora", resp.getUsername());
        assertEquals("dora@example.com", resp.getEmail());
        assertEquals(LocalDateTime.ofInstant(fixedInstant, ZoneOffset.UTC), resp.getHireDate());
        assertEquals(LocalDate.of(2025,5,12).plusMonths(3), resp.getCredentialsExpiryDate());
        assertEquals(LocalDate.of(2025,5,12).plusMonths(12), resp.getAccountExpiryDate());

    }

}
