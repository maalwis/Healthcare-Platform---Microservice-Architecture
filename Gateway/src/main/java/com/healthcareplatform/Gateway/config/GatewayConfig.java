package com.healthcareplatform.Gateway.config;

import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class GatewayConfig {

    @Bean
    public RouteLocator customRouteLocator(RouteLocatorBuilder builder) {
        return builder.routes()

                // AuthController Routes (AuthenticationService, port 8080)
                .route("authentication_login_route", r -> r
                        .path("/login")
                        .filters(f -> f.rewritePath("/login", "/api/v1/auth/public/login"))
                        .uri("http://localhost:8080"))

                .route("auth_logout_route", r -> r
                        .path("/auth/logout")
                        .filters(f -> f.rewritePath("/auth/logout", "/api/v1/auth/logout"))
                        .uri("http://localhost:8080"))

                .route("auth_token_refresh_route", r -> r
                        .path("/auth/token/refresh")
                        .filters(f -> f.rewritePath("/auth/token/refresh", "/api/v1/auth/token/refresh"))
                        .uri("http://localhost:8080"))

                // CsrfController Route (AuthenticationService, port 8080)
                .route("csrf_token_route", r -> r
                        .path("/csrf-token")
                        .filters(f -> f.rewritePath("/csrf-token", "/api/csrf-token"))
                        .uri("http://localhost:8080"))

                // PermissionController Routes (AuthenticationService, port 8080)
                .route("permissions_get_all_route", r -> r
                        .path("/permissions")
                        .filters(f -> f.rewritePath("/permissions", "/api/v1/permissions"))
                        .uri("http://localhost:8080"))

                .route("permissions_by_id_route", r -> r
                        .path("/permissions/{permissionId}")
                        .filters(f -> f.rewritePath("/permissions/(?<permissionId>.*)", "/api/v1/permissions/${permissionId}"))
                        .uri("http://localhost:8080"))

                // RoleController Routes (AuthenticationService, port 8080)
                .route("roles_get_all_route", r -> r
                        .path("/roles")
                        .filters(f -> f.rewritePath("/roles", "/api/v1/roles"))
                        .uri("http://localhost:8080"))

                .route("roles_by_id_route", r -> r
                        .path("/roles/{roleId}")
                        .filters(f -> f.rewritePath("/roles/(?<roleId>.*)", "/api/v1/roles/${roleId}"))
                        .uri("http://localhost:8080"))

                // RolePermissionController Routes (AuthenticationService, port 8080)
                .route("roles_assign_permissions_route", r -> r
                        .path("/roles/{roleId}/permissions")
                        .filters(f -> f.rewritePath("/roles/(?<roleId>.*)/permissions", "/api/v1/roles/${roleId}/permissions"))
                        .uri("http://localhost:8080"))

                .route("roles_remove_permission_route", r -> r
                        .path("/roles/{roleId}/permissions/{permissionId}")
                        .filters(f -> f.rewritePath("/roles/(?<roleId>.*)/permissions/(?<permissionId>.*)", "/api/v1/roles/${roleId}/permissions/${permissionId}"))
                        .uri("http://localhost:8080"))

                // UserController Routes (AuthenticationService, port 8080)
                .route("users_create_route", r -> r
                        .path("/users")
                        .filters(f -> f.rewritePath("/users", "/api/v1/users"))
                        .uri("http://localhost:8080"))

                .route("users_by_id_route", r -> r
                        .path("/users/{id}")
                        .filters(f -> f.rewritePath("/users/(?<id>.*)", "/api/v1/users/${id}"))
                        .uri("http://localhost:8080"))

                // UserProfileController Routes (AuthenticationService, port 8080)
                .route("users_profile_route", r -> r
                        .path("/users/{userId}/profile")
                        .filters(f -> f.rewritePath("/users/(?<userId>.*)/profile", "/api/v1/users/${userId}/profile"))
                        .uri("http://localhost:8080"))

                // UserRoleController Routes (AuthenticationService, port 8080)
                .route("users_roles_assign_route", r -> r
                        .path("/users/{userId}/roles")
                        .filters(f -> f.rewritePath("/users/(?<userId>.*)/roles", "/api/v1/users/${userId}/roles"))
                        .uri("http://localhost:8080"))

                .route("users_roles_remove_route", r -> r
                        .path("/users/{userId}/roles/{roleId}")
                        .filters(f -> f.rewritePath("/users/(?<userId>.*)/roles/(?<roleId>.*)", "/api/v1/users/${userId}/roles/${roleId}"))
                        .uri("http://localhost:8080"))

                // AppointmentController Routes (AppointmentSchedulingService, port 8082)
                .route("appointments_create_route", r -> r
                        .path("/appointments")
                        .filters(f -> f.rewritePath("/appointments", "/api/v1/appointments"))
                        .uri("http://localhost:8082"))

                .route("appointments_get_all_route", r -> r
                        .path("/appointments")
                        .filters(f -> f.rewritePath("/appointments", "/api/v1/appointments"))
                        .uri("http://localhost:8082"))

                .route("appointments_by_id_route", r -> r
                        .path("/appointments/{appointmentId}")
                        .filters(f -> f.rewritePath("/appointments/(?<appointmentId>.*)", "/api/v1/appointments/${appointmentId}"))
                        .uri("http://localhost:8082"))

                // PrescriptionController Routes (PrescriptionService, port 8084)
                .route("prescriptions_create_route", r -> r
                        .path("/prescriptions")
                        .filters(f -> f.rewritePath("/prescriptions", "/api/v1/prescriptions"))
                        .uri("http://localhost:8084"))

                .route("prescriptions_get_all_route", r -> r
                        .path("/prescriptions")
                        .filters(f -> f.rewritePath("/prescriptions", "/api/v1/prescriptions"))
                        .uri("http://localhost:8084"))

                .route("prescriptions_by_id_route", r -> r
                        .path("/prescriptions/{prescriptionId}")
                        .filters(f -> f.rewritePath("/prescriptions/(?<prescriptionId>.*)", "/api/v1/prescriptions/${prescriptionId}"))
                        .uri("http://localhost:8084"))

                // PatientController Routes (PatientManagementService, port 8086)
                .route("patients_create_route", r -> r
                        .path("/patients")
                        .filters(f -> f.rewritePath("/patients", "/api/v1/patients"))
                        .uri("http://localhost:8086"))

                .route("patients_get_all_route", r -> r
                        .path("/patients")
                        .filters(f -> f.rewritePath("/patients", "/api/v1/patients"))
                        .uri("http://localhost:8086"))

                .route("patients_by_id_route", r -> r
                        .path("/patients/{patientId}")
                        .filters(f -> f.rewritePath("/patients/(?<patientId>.*)", "/api/v1/patients/${patientId}"))
                        .uri("http://localhost:8086"))

                .build();
    }
}