package com.healthcareplatform.AuthenticationService.repository;

import com.healthcareplatform.AuthenticationService.model.Permission;
import com.healthcareplatform.AuthenticationService.model.PermissionEnum;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
public interface PermissionRepository extends JpaRepository<Permission, Long> {

    Optional<Permission> findByPermission(PermissionEnum permission);
}
