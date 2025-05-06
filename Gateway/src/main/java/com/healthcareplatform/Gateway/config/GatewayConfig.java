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

    @Bean
    public RouteLocator customRouteLocator(RouteLocatorBuilder builder) {
        return builder.routes()

                // AuthController Routes (AuthenticationService)
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

                .build();
    }
}