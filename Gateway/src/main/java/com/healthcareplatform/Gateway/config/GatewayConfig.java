package com.healthcareplatform.Gateway.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class GatewayConfig {

    @Value("${gateway.services.authenticationService}")
    private String authenticationService;

    @Value("${gateway.services.appointmentSchedulingService}")
    private String appointmentSchedulingService;

    @Value("${gateway.services.prescriptionService}")
    private String prescriptionService;

    @Value("${gateway.services.patientManagementService}")
    private String patientManagementService;

    @Bean
    public RouteLocator customRouteLocator(RouteLocatorBuilder builder) {
        return builder.routes()

                // AuthController Routes (AuthenticationService, port 8080)
                .route("authentication_login_route", r -> r
                        .path("/login")
                        .filters(f -> f.rewritePath("/login", "/api/v1/auth/public/login"))
                        .uri("lb://" + authenticationService))

                .route("auth_logout_route", r -> r
                        .path("/auth/logout")
                        .filters(f -> f.rewritePath("/auth/logout", "/api/v1/auth/logout"))
                        .uri("lb://" + authenticationService))

                .route("auth_token_refresh_route", r -> r
                        .path("/auth/token/refresh")
                        .filters(f -> f.rewritePath("/auth/token/refresh", "/api/v1/auth/token/refresh"))
                        .uri("lb://" + authenticationService))

                // CsrfController Route (AuthenticationService, port 8080)
                .route("csrf_token_route", r -> r
                        .path("/csrf-token")
                        .filters(f -> f.rewritePath("/csrf-token", "/api/csrf-token"))
                        .uri("lb://" + authenticationService))

                // PermissionController Routes (AuthenticationService, port 8080)
                .route("permissions_get_all_route", r -> r
                        .path("/permissions")
                        .filters(f -> f.rewritePath("/permissions", "/api/v1/permissions"))
                        .uri("lb://" + authenticationService))

                .route("permissions_by_id_route", r -> r
                        .path("/permissions/{permissionId}")
                        .filters(f -> f.rewritePath("/permissions/(?<permissionId>.*)", "/api/v1/permissions/${permissionId}"))
                        .uri("lb://" + authenticationService))

                // RoleController Routes (AuthenticationService, port 8080)
                .route("roles_get_all_route", r -> r
                        .path("/roles")
                        .filters(f -> f.rewritePath("/roles", "/api/v1/roles"))
                        .uri("lb://" + authenticationService))

                .route("roles_by_id_route", r -> r
                        .path("/roles/{roleId}")
                        .filters(f -> f.rewritePath("/roles/(?<roleId>.*)", "/api/v1/roles/${roleId}"))
                        .uri("lb://" + authenticationService))

                // RolePermissionController Routes (AuthenticationService, port 8080)
                .route("roles_assign_permissions_route", r -> r
                        .path("/roles/{roleId}/permissions")
                        .filters(f -> f.rewritePath("/roles/(?<roleId>.*)/permissions", "/api/v1/roles/${roleId}/permissions"))
                        .uri("lb://" + authenticationService))

                .route("roles_remove_permission_route", r -> r
                        .path("/roles/{roleId}/permissions/{permissionId}")
                        .filters(f -> f.rewritePath("/roles/(?<roleId>.*)/permissions/(?<permissionId>.*)", "/api/v1/roles/${roleId}/permissions/${permissionId}"))
                        .uri("lb://" + authenticationService))

                // UserController Routes (AuthenticationService, port 8080)
                .route("users_create_route", r -> r
                        .path("/users")
                        .filters(f -> f.rewritePath("/users", "/api/v1/users"))
                        .uri("lb://" + authenticationService))

                .route("users_by_id_route", r -> r
                        .path("/users/{id}")
                        .filters(f -> f.rewritePath("/users/(?<id>.*)", "/api/v1/users/${id}"))
                        .uri("lb://" + authenticationService))

                // UserProfileController Routes (AuthenticationService, port 8080)
                .route("users_profile_route", r -> r
                        .path("/users/{userId}/profile")
                        .filters(f -> f.rewritePath("/users/(?<userId>.*)/profile", "/api/v1/users/${userId}/profile"))
                        .uri("lb://" + authenticationService))

                // UserRoleController Routes (AuthenticationService, port 8080)
                .route("users_roles_assign_route", r -> r
                        .path("/users/{userId}/roles")
                        .filters(f -> f.rewritePath("/users/(?<userId>.*)/roles", "/api/v1/users/${userId}/roles"))
                        .uri("lb://" + authenticationService))

                .route("users_roles_remove_route", r -> r
                        .path("/users/{userId}/roles/{roleId}")
                        .filters(f -> f.rewritePath("/users/(?<userId>.*)/roles/(?<roleId>.*)", "/api/v1/users/${userId}/roles/${roleId}"))
                        .uri("lb://" + authenticationService))

                // AppointmentController Routes (AppointmentSchedulingService, port 8082)
                .route("appointments_create_route", r -> r
                        .path("/appointments")
                        .filters(f -> f.rewritePath("/appointments", "/api/v1/appointments"))
                        .uri("lb://" + appointmentSchedulingService))

                .route("appointments_get_all_route", r -> r
                        .path("/appointments")
                        .filters(f -> f.rewritePath("/appointments", "/api/v1/appointments"))
                        .uri("lb://" + appointmentSchedulingService))

                .route("appointments_by_id_route", r -> r
                        .path("/appointments/{appointmentId}")
                        .filters(f -> f.rewritePath("/appointments/(?<appointmentId>.*)", "/api/v1/appointments/${appointmentId}"))
                        .uri("lb://" + appointmentSchedulingService))

                // PrescriptionController Routes (PrescriptionService, port 8084)
                .route("prescriptions_create_route", r -> r
                        .path("/prescriptions")
                        .filters(f -> f.rewritePath("/prescriptions", "/api/v1/prescriptions"))
                        .uri("lb://" + prescriptionService))

                .route("prescriptions_get_all_route", r -> r
                        .path("/prescriptions")
                        .filters(f -> f.rewritePath("/prescriptions", "/api/v1/prescriptions"))
                        .uri("lb://" + prescriptionService))

                .route("prescriptions_by_id_route", r -> r
                        .path("/prescriptions/{prescriptionId}")
                        .filters(f -> f.rewritePath("/prescriptions/(?<prescriptionId>.*)", "/api/v1/prescriptions/${prescriptionId}"))
                        .uri("lb://" + prescriptionService))

                // PatientController Routes (PatientManagementService, port 8086)
                .route("patients_create_route", r -> r
                        .path("/patients")
                        .filters(f -> f.rewritePath("/patients", "/api/v1/patients"))
                        .uri("lb://" + patientManagementService))

                .route("patients_get_all_route", r -> r
                        .path("/patients")
                        .filters(f -> f.rewritePath("/patients", "/api/v1/patients"))
                        .uri("lb://" + patientManagementService))

                .route("patients_by_id_route", r -> r
                        .path("/patients/{patientId}")
                        .filters(f -> f.rewritePath("/patients/(?<patientId>.*)", "/api/v1/patients/${patientId}"))
                        .uri("lb://" + patientManagementService))

                .build();
    }
}