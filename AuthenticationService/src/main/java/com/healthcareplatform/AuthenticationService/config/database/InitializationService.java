package com.healthcareplatform.AuthenticationService.config.database;

import com.healthcareplatform.AuthenticationService.model.*;
import com.healthcareplatform.AuthenticationService.repository.PermissionRepository;
import com.healthcareplatform.AuthenticationService.repository.RolePermissionRepository;
import com.healthcareplatform.AuthenticationService.repository.RoleRepository;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class InitializationService {

    private final PermissionRepository permissionRepository;

    private final RoleRepository roleRepository;

    private final RolePermissionRepository rolePermissionRepository;

    public InitializationService(PermissionRepository permissionRepository,
                                 RoleRepository roleRepository,
                                 RolePermissionRepository rolePermissionRepository) {

        this.permissionRepository = permissionRepository;
        this.roleRepository = roleRepository;
        this.rolePermissionRepository = rolePermissionRepository;
    }

    /**
     * Initializes roles and permissions in the database, connecting roles to their respective permissions.
     */
    public void initializeRolesAndPermissions() {
        // Step 1: Insert all permissions if not already present
        if (permissionRepository.count() == 0) {
            List<Permission> permissions = new ArrayList<>();
            for (PermissionEnum permissionEnum : PermissionEnum.values()) {
                Permission permission = new Permission();
                permission.setPermission(permissionEnum);
                permissions.add(permission);
            }
            permissionRepository.saveAll(permissions);
        }

        // Step 2: Insert all roles if not already present
        if (roleRepository.count() == 0) {
            List<Role> roles = new ArrayList<>();
            for (RoleEnum roleEnum : RoleEnum.values()) {
                Role role = new Role();
                role.setRole(roleEnum);
                roles.add(role);
            }
            roleRepository.saveAll(roles);
        }

        // Step 3: Connect roles to permissions
        List<Permission> allPermissions = permissionRepository.findAll();
        Map<PermissionEnum, Permission> permissionMap = allPermissions.stream()
                .collect(Collectors.toMap(Permission::getPermission, p -> p));

        List<Role> allRoles = roleRepository.findAll();
        Map<RoleEnum, Role> roleMap = allRoles.stream()
                .collect(Collectors.toMap(Role::getRole, r -> r));

        List<RolePermission> rolePermissions = new ArrayList<>();

        for (RoleEnum roleEnum : RoleEnum.values()) {
            String roleName = roleEnum.getRoleName();
            List<PermissionEnum> permissionEnums = getPermissionsForRole(roleName);
            Role role = roleMap.get(roleEnum);

            for (PermissionEnum permissionEnum : permissionEnums) {
                Permission permission = permissionMap.get(permissionEnum);
                if (permission != null && role != null) {
                    RolePermission rolePermission = new RolePermission();
                    rolePermission.setRole(role);
                    rolePermission.setPermission(permission);
                    rolePermission.setId(new RolePermissionId(role.getRoleId(), permission.getPermissionId()));
                    rolePermissions.add(rolePermission);
                }
            }
        }

        // Step 4: Save all role-permission relationships
        rolePermissionRepository.saveAll(rolePermissions);
    }

    /**
     * Determines the list of permissions for a given role based on its name.
     * @param roleName The name of the role from RoleEnum.
     * @return List of PermissionEnum values applicable to the role.
     */
    private List<PermissionEnum> getPermissionsForRole(String roleName) {
        // Chief/Officer roles (e.g., CEO, COO, CFO, CMO) - broad administrative permissions
        if (roleName.contains("Chief") || roleName.contains("Officer")) {
            return Arrays.asList(
                    PermissionEnum.MANAGE_USER_ACCOUNTS,
                    PermissionEnum.ASSIGN_ROLES,
                    PermissionEnum.VIEW_FINANCIAL_REPORTS,
                    PermissionEnum.ACCESS_ADMINISTRATION_PANEL,
                    PermissionEnum.MANAGE_PERMISSIONS,
                    PermissionEnum.ACCESS_AUDIT_LOGS,
                    PermissionEnum.CONFIGURE_SYSTEM_SETTINGS
            );
        }
        // Department Heads - department management plus clinical permissions
        else if (roleName.contains("Head")) {
            return Arrays.asList(
                    PermissionEnum.EDIT_DEPARTMENT_DATA,
                    PermissionEnum.MANAGE_STAFF_SCHEDULES,
                    PermissionEnum.VIEW_PATIENT_RECORDS,
                    PermissionEnum.EDIT_PATIENT_RECORDS,
                    PermissionEnum.MANAGE_APPOINTMENTS,
                    PermissionEnum.GENERATE_REPORTS
            );
        }
        // Physicians - clinical permissions
        else if (roleName.contains("Physician")) {
            return Arrays.asList(
                    PermissionEnum.VIEW_PATIENT_RECORDS,
                    PermissionEnum.EDIT_PATIENT_RECORDS,
                    PermissionEnum.MANAGE_APPOINTMENTS,
                    PermissionEnum.PROCESS_MEDICAL_ORDERS
            );
        }
        // Surgeons - surgical and clinical permissions
        else if (roleName.contains("Surgeon")) {
            return Arrays.asList(
                    PermissionEnum.VIEW_PATIENT_RECORDS,
                    PermissionEnum.EDIT_PATIENT_RECORDS,
                    PermissionEnum.AUTHORIZE_SURGERY,
                    PermissionEnum.APPROVE_MEDICAL_PROCEDURES
            );
        }
        // Nurses - patient care permissions
        else if (roleName.contains("Nurse")) {
            return Arrays.asList(
                    PermissionEnum.VIEW_PATIENT_RECORDS,
                    PermissionEnum.MANAGE_APPOINTMENTS,
                    PermissionEnum.PROCESS_MEDICAL_ORDERS
            );
        }
        // IT Staff - system-related permissions
        else if (roleName.contains("IT")) {
            return Arrays.asList(
                    PermissionEnum.CONFIGURE_SYSTEM_SETTINGS,
                    PermissionEnum.ACCESS_IT_RESOURCES,
                    PermissionEnum.ACCESS_AUDIT_LOGS
            );
        }
        // Pharmacists - inventory and medical order permissions
        else if (roleName.contains("Pharmacist")) {
            return Arrays.asList(
                    PermissionEnum.MANAGE_INVENTORY,
                    PermissionEnum.PROCESS_MEDICAL_ORDERS,
                    PermissionEnum.VIEW_INVENTORY
            );
        }
        // Laboratory/Pathologist/Radiologist - diagnostic permissions
        else if (roleName.contains("Laboratory") || roleName.contains("Pathologist") || roleName.contains("Radiologist")) {
            return Arrays.asList(
                    PermissionEnum.VIEW_PATIENT_RECORDS,
                    PermissionEnum.PROCESS_MEDICAL_ORDERS,
                    PermissionEnum.GENERATE_REPORTS
            );
        }
        // Administrative staff (Receptionist, Secretary, Billing) - administrative permissions
        else if (roleName.contains("Receptionist") || roleName.contains("Secretary") || roleName.contains("Billing")) {
            return Arrays.asList(
                    PermissionEnum.MANAGE_APPOINTMENTS,
                    PermissionEnum.VIEW_SCHEDULING,
                    PermissionEnum.EDIT_BILLING
            );
        }
        // Default case - minimal permissions for unclassified roles (e.g., Housekeeping, Transport)
        else {
            return List.of(
                    PermissionEnum.VIEW_SCHEDULING
            );
        }
    }
}