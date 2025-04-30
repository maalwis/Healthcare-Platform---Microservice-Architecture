package com.healthcareplatform.AppointmentService.config;


import com.healthcareplatform.AppointmentService.security.AuthEntryPointJwt;
import com.healthcareplatform.AppointmentService.security.AuthTokenFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Autowired
    private AuthEntryPointJwt authEntryPointJwt;

    @Bean
    public SecurityFilterChain defaultSecurityFilterChain(HttpSecurity http) throws Exception {
        http.csrf(AbstractHttpConfigurer::disable);
        http.authorizeHttpRequests(requests ->
                requests
                        .requestMatchers("/api/v1/appointments/**")
                        .hasAnyAuthority(
                               "VIEW_SCHEDULING",
                                "MANAGE_APPOINTMENTS",
                                "EDIT_SCHEDULING")
                        .anyRequest().authenticated());

        http.exceptionHandling(exception ->
                        exception.authenticationEntryPoint(authEntryPointJwt))
                .addFilterBefore(authenticationJwtTokenFilter(), UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }

    @Bean
    public AuthTokenFilter authenticationJwtTokenFilter() {
        return new AuthTokenFilter();
    }
}