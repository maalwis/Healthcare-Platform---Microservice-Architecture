package com.healthcareplatform.AuthenticationService.config.database;

import com.healthcareplatform.AuthenticationService.permission.*;
import com.healthcareplatform.AuthenticationService.role.*;
import com.healthcareplatform.AuthenticationService.user.*;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class InitializationService {

    private final PermissionRepository permissionRepository;
    private final RoleRepository roleRepository;
    private final RolePermissionRepository rolePermissionRepository;
    private final UserRepository userRepository;
    private final UserRoleRepository userRoleRepository;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public InitializationService(
            PermissionRepository permissionRepository,
            RoleRepository roleRepository,
            RolePermissionRepository rolePermissionRepository,
            UserRepository userRepository,
            UserRoleRepository userRoleRepository,
            PasswordEncoder passwordEncoder
            ) {

        this.permissionRepository = permissionRepository;
        this.roleRepository = roleRepository;
        this.rolePermissionRepository = rolePermissionRepository;
        this.userRepository = userRepository;
        this.userRoleRepository = userRoleRepository;
        this.passwordEncoder = passwordEncoder;

    }

    /**
     * Initializes roles, permissions, and users in the database.
     */
    public void initializeRolesPermissionsAndUsers() {
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
        rolePermissionRepository.saveAll(rolePermissions);

        // Step 4: Create users and assign roles
        createUsersAndAssignRoles(roleMap);
    }

    /**
     * Creates multiple users and assigns them to specific roles.
     * @param roleMap Map of RoleEnum to Role entities.
     */
    private void createUsersAndAssignRoles(Map<RoleEnum, Role> roleMap) {
        // Define users with professional names and additional fields
        List<UserCreationData> userDataList = Arrays.asList(
                new UserCreationData(
                        "dev-test",       // Full name
                        "dev-user",                // Keep original username
                        "dev@example.com",         // Keep original email
                        "pass",                    // Password
                        "dev-001",                 // Employee ID
                        LocalDateTime.now(),       // Hire date
                        RoleEnum.DEV_USER,
                        "custom email"
                ),

                new UserCreationData(
                        "Eleanor Prescott",       // Full name
                        "ceo_user",                // Keep original username
                        "ceo@example.com",         // Keep original email
                        "pass",                    // Password
                        "EMP-001",                 // Employee ID
                        LocalDateTime.now(),       // Hire date
                        RoleEnum.CHIEF_EXECUTIVE_OFFICER,
                        "custom email"
                ),
                new UserCreationData(
                        "Julian Whitmore",
                        "head_cardiology",
                        "head_cardiology@example.com",
                        "pass",
                        "EMP-002",
                        LocalDateTime.now(),
                        RoleEnum.DEPARTMENT_HEAD_CARDIOLOGY,
                        "custom email"
                ),
                new UserCreationData(
                        "Marcus Reynolds",
                        "physician_general",
                        "physician_general@example.com",
                        "pass",
                        "EMP-003",
                        LocalDateTime.now(),
                        RoleEnum.PHYSICIAN_GENERAL,
                        "custom email"
                ),
                new UserCreationData(
                        "Gabrielle Sinclair",
                        "surgeon_general",
                        "surgeon_general@example.com",
                        "pass",
                        "EMP-004",
                        LocalDateTime.now(),
                        RoleEnum.SURGEON_GENERAL,
                        "custom email"
                ),
                new UserCreationData(
                        "Clara Bennett",
                        "nurse_registered",
                        "nurse_registered@example.com",
                        "pass",
                        "EMP-005",
                        LocalDateTime.now(),
                        RoleEnum.NURSE_REGISTERED,
                        "custom email"
                ),
                new UserCreationData(
                        "Victor Nakamura",
                        "it_staff",
                        "it_staff@example.com",
                        "pass",
                        "EMP-006",
                        LocalDateTime.now(),
                        RoleEnum.IT_STAFF,
                        "custom email"
                ),
                new UserCreationData(
                        "Lydia Chen",
                        "pharmacist",
                        "pharmacist@example.com",
                        "pass",
                        "EMP-007",
                        LocalDateTime.now(),
                        RoleEnum.PHARMACIST,
                        "custom email"
                ),
                new UserCreationData(
                        "Oscar Fitzgerald",
                        "lab_technician",
                        "lab_technician@example.com",
                        "pass",
                        "EMP-008",
                        LocalDateTime.now(),
                        RoleEnum.LABORATORY_TECHNICIAN,
                        "custom email"
                ),
                new UserCreationData(
                        "Amelia Winslow",
                        "receptionist",
                        "receptionist@example.com",
                        "pass",
                        "EMP-009",
                        LocalDateTime.now(),
                        RoleEnum.RECEPTIONIST,
                        "custom email"
                )
        );

        for (UserCreationData userData : userDataList) {
            if (userRepository.findByUsername(userData.getUsername()).isEmpty()) {
                // Create user
                User user = new User();
                user.setEmployeeId(userData.getEmployeeId());
                user.setFullName(userData.getFullName());
                user.setUserName(userData.getUsername());
                user.setEmail(userData.getEmail());
                user.setPassword(passwordEncoder.encode(userData.getPassword()));
                user.setHireDate(userData.getHireDate());

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

                // Signed up method
                user.setSignUpMethod(userData.getSignUpMethod());

                userRepository.save(user);

                // Assign role
                Role role = roleMap.get(userData.getRoleEnum());
                if (role != null) {
                    UserRole userRole = new UserRole();
                    userRole.setUser(user);
                    userRole.setRole(role);
                    userRole.setAssignedAt(LocalDateTime.now());
                    userRole.setId(new UserRoleId(user.getUserId(), role.getRoleId()));
                    userRoleRepository.save(userRole);
                }
            }
        }
    }

    /**
     * Helper class to hold user creation data.
     */
    @Getter
    private static class UserCreationData {
        private final String fullName;
        private final String username;
        private final String email;
        private final String password;
        private final String employeeId;
        private final LocalDateTime hireDate;
        private final RoleEnum roleEnum;
        private final String signUpMethod;


        public UserCreationData(String fullName, String username, String email, String password,
                                String employeeId, LocalDateTime hireDate, RoleEnum roleEnum, String signUpMethod) {
            this.fullName = fullName;
            this.username = username;
            this.email = email;
            this.password = password;
            this.employeeId = employeeId;
            this.hireDate = hireDate;
            this.roleEnum = roleEnum;
            this.signUpMethod = signUpMethod;
        }

    }

    /**
     * Determines the list of permissions for a given role based on its name.
     * @param roleName The name of the role from RoleEnum.
     * @return List of PermissionEnum values applicable to the role.
     */
    private List<PermissionEnum> getPermissionsForRole(String roleName) {
        // Assigns all the permission to the DEV_USER
        if(roleName.contains("development_engineering")) {
            // EnumSet.of all values in PermissionEnum
            Set<PermissionEnum> allPerms = EnumSet.allOf(PermissionEnum.class);
            // If you really need a List:
            return new ArrayList<>(allPerms);
        }

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