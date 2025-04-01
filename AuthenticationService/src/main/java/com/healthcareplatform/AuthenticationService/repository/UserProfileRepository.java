package com.healthcareplatform.AuthenticationService.repository;

import com.healthcareplatform.AuthenticationService.model.User;
import com.healthcareplatform.AuthenticationService.model.UserProfile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
public interface UserProfileRepository extends JpaRepository<UserProfile, Long> {

    Optional<UserProfile> findByUser(User user);

    Optional<UserProfile> findByEmployeeId(String employeeId);
}
