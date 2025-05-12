package com.healthcareplatform.AuthenticationService.permission;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PermissionRepository extends JpaRepository<Permission, Long> {

    Permission findByPermission(PermissionEnum permission);

    long count();
}
