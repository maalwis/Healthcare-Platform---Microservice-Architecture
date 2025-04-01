package com.healthcareplatform.AuthenticationService.repository;

import com.healthcareplatform.AuthenticationService.model.UserRole;
import com.healthcareplatform.AuthenticationService.model.UserRoleId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface UserRoleRepository extends JpaRepository<UserRole, UserRoleId> {

    List<UserRole> findByUser_UserId(Long userId);

    List<UserRole> findByRole_RoleId(Long roleId);
}
