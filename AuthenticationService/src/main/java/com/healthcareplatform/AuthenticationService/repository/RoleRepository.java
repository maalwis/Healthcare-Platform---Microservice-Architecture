package com.healthcareplatform.AuthenticationService.repository;

import com.healthcareplatform.AuthenticationService.model.Role;
import com.healthcareplatform.AuthenticationService.model.RoleEnum;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {

    Optional<Role> findByRole(RoleEnum role);
}
