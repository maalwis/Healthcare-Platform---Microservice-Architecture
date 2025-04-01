package com.healthcareplatform.AuthenticationService.repository;

import com.healthcareplatform.AuthenticationService.model.RolePermission;
import com.healthcareplatform.AuthenticationService.model.RolePermissionId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface RolePermissionRepository extends JpaRepository<RolePermission, RolePermissionId> {

    List<RolePermission> findByRole_RoleId(Long roleId);

    List<RolePermission> findByPermission_PermissionId(Long permissionId);

}
