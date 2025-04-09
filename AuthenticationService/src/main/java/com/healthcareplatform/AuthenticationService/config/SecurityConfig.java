package com.healthcareplatform.AuthenticationService.config;

import com.healthcareplatform.AuthenticationService.security.jwt.AuthEntryPointJwt;
import com.healthcareplatform.AuthenticationService.security.jwt.AuthTokenFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.csrf.CookieCsrfTokenRepository;

import static org.springframework.security.config.Customizer.withDefaults;


@Configuration
@EnableWebSecurity
public class SecurityConfig {

    private AuthEntryPointJwt authEntryPointJwt;

    @Bean
    public SecurityFilterChain defaultSecurityFilterChain(HttpSecurity http) throws Exception {
        http.csrf(csrf ->
                csrf.csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse())
                        .ignoringRequestMatchers("/api/v1/auth/public/login"));

        http.authorizeHttpRequests(requests ->
                requests
                        .requestMatchers("/api/v1/auth/public/login").permitAll()
                        .requestMatchers("/api/csrf-token").permitAll()
                        .requestMatchers("/api/v1/auth/logout").authenticated()
                        .requestMatchers("/api/v1/auth/token/refresh").authenticated()
                        .requestMatchers("/api/v1/auth/change-password").authenticated()
                        .requestMatchers("/api/v1/permissions/**").hasAuthority("MANAGE_PERMISSIONS")
                        .requestMatchers("/api/v1/roles/**").hasAnyAuthority("MANAGE_PERMISSIONS", "ASSIGN_ROLES")
                        .requestMatchers("/api/v1/users").hasAuthority("MANAGE_USER_ACCOUNTS")
                        .requestMatchers("/api/v1/users/{id}").hasAuthority("MANAGE_USER_ACCOUNTS")
                        .requestMatchers("/api/v1/users/{userId}/profile").authenticated() // Further check in controller
                        .requestMatchers("/api/v1/users/{userId}/roles/**").hasAuthority("ASSIGN_ROLES")
                        .anyRequest().authenticated());

        http.exceptionHandling(exception -> exception.authenticationEntryPoint(authEntryPointJwt));
        http.addFilterBefore(authenticationJwtTokenFilter(), UsernamePasswordAuthenticationFilter.class);
        http.formLogin(withDefaults());
        http.httpBasic(withDefaults());
        return http.build();
    }

    @Bean
    public AuthTokenFilter authenticationJwtTokenFilter() {
        return new AuthTokenFilter();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
