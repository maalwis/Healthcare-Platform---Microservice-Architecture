package com.healthcareplatform.AuthenticationService.role;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface RolePermissionRepository extends JpaRepository<RolePermission, RolePermissionId> {

    List<RolePermission> findByRole_RoleId(Long roleId);

    List<RolePermission> findByPermission_PermissionId(Long permissionId);

}
