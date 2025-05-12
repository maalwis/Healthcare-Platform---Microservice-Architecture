package com.healthcareplatform.AuthenticationService.user;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class UserServiceTest {

    private UserService userServiceTest;

    @Mock
    private UserRepository userRepository;

    @BeforeEach
    public void setUp() {
        userServiceTest = new UserService(userRepository);
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
}
