package com.healthcareplatform.PharmacyService.authenticationClient;

import com.healthcareplatform.PharmacyService.dto.UserDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;

/**
 * <p>
 * The {@code AuthenticationClient} is a Spring Cloud OpenFeign client that
 * declaratively defines REST calls to the Authentication Service. At runtime,
 * Spring Boot’s auto-configuration and Feign’s dynamic proxy mechanism generate
 * an implementation of this interface, converting method invocations into HTTP
 * requests and deserializing responses into {@link UserDTO} instances.
 * </p>
 *
 * <p>
 * <u>Foundation:</u> Feign uses Java dynamic proxies and Spring MVC annotations
 * to define the HTTP contract. The {@code @FeignClient} annotation registers this
 * interface as a bean, while Feign’s Contract and RequestTemplate components interpret
 * the {@code @GetMapping} and {@code @RequestHeader} annotations to build the request.
 * </p>
 */


@FeignClient(name = "AuthenticationService")
public interface AuthenticationClient {

    @GetMapping("/api/v1/private/validateToken")
    ResponseEntity<UserDTO> getUserDto(@RequestHeader("Authorization") String authHeader);
}
