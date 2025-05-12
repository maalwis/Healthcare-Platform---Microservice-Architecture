package com.healthcareplatform.AuthenticationService.jwtSecurityFilter;

import io.jsonwebtoken.*;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import jakarta.servlet.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.security.Key;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class JwtUtils {
    private static final Logger logger = LoggerFactory.getLogger(JwtUtils.class);

    @Value("${spring.app.jwtSecret}")
    private String jwtSecret;

    @Value("${spring.app.jwtExpirationMs}")
    private int jwtExpirationMs;

    public String getJwtFromHeader(HttpServletRequest request) {
        String bearerToken = request.getHeader("Authorization");
        logger.debug("Authorization Header: {}", bearerToken);
        if (bearerToken != null && bearerToken.startsWith("Bearer ")) {
            return bearerToken.substring(7); // Remove Bearer prefix
        }
        return null;
    }

    public String generateTokenFromUsername(UserDetails userDetails) {
        // Retrieve the list of permissions/authorities from the user details
        // Collect roles from the UserDetails
        List<String> permissions = userDetails.getAuthorities().stream()
                .map(item -> item.getAuthority())
                .collect(Collectors.toList());

        // Build the JWT token and include the roles as a custom claim
        return Jwts.builder()
                .setSubject(userDetails.getUsername())
                .claim("permissions", permissions) // adding roles as a claim
                .setIssuedAt(new Date())
                .setExpiration(new Date((new Date()).getTime() + jwtExpirationMs))
                .signWith(key())
                .compact();
    }

    // Extract permissions from JWT token as List<GrantedAuthority>
    public List<GrantedAuthority> getAuthoritiesFromJwtToken(String token) {
        try {
            // Parse the JWT token and get the claims
            Claims claims = Jwts.parser()
                    .setSigningKey(key())
                    .build()
                    .parseSignedClaims(token)
                    .getPayload();

            // Extract the "permissions" claim as a List of Strings
            @SuppressWarnings("unchecked")
            List<String> permissions = (List<String>) claims.get("permissions");

            if (permissions == null) {
                logger.warn("No permissions found in JWT token");
                return List.of(); // Return empty list if no permissions
            }

            // Convert each permission string to a SimpleGrantedAuthority
            return permissions.stream()
                    .map(SimpleGrantedAuthority::new)
                    .collect(Collectors.toList());

        } catch (JwtException | IllegalArgumentException e) {
            return List.of(); // Return empty list on failure
        }
    }

    public String getUserNameFromJwtToken(String token) {
        return Jwts.parser()
                .verifyWith((SecretKey) key())
                .build().parseSignedClaims(token)
                .getPayload().getSubject();
    }

    private Key key() {
        return Keys.hmacShaKeyFor(Decoders.BASE64.decode(jwtSecret));
    }

    public boolean validateJwtToken(String authToken) {
        try {
            System.out.println("Validate");
            Jwts.parser().verifyWith((SecretKey) key()).build().parseSignedClaims(authToken);
            return true;
        } catch (MalformedJwtException e) {
            logger.error("Invalid JWT token: {}", e.getMessage());
        } catch (ExpiredJwtException e) {
            logger.error("JWT token is expired: {}", e.getMessage());
        } catch (UnsupportedJwtException e) {
            logger.error("JWT token is unsupported: {}", e.getMessage());
        } catch (IllegalArgumentException e) {
            logger.error("JWT claims string is empty: {}", e.getMessage());
        }
        return false;
    }
}
