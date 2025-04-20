package com.healthcareplatform.Gateway.filters;

import com.healthcareplatform.Gateway.security.jwt.JwtUtils;
import io.github.bucket4j.Bandwidth;
import io.github.bucket4j.Bucket;
import io.github.bucket4j.Refill;
import org.springframework.cloud.gateway.filter.*;
import org.springframework.core.Ordered;
import org.springframework.http.*;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.nio.charset.StandardCharsets;
import java.time.Duration;
import java.util.Objects;
import java.util.concurrent.*;

@Component
public class RateLimitGlobalFilter implements GlobalFilter, Ordered {

    private final ConcurrentMap<String, Bucket> buckets = new ConcurrentHashMap<>();
    private final JwtUtils jwtUtils;

    public RateLimitGlobalFilter(JwtUtils jwtUtils) {
        this.jwtUtils = jwtUtils;
    }

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {

        String clientToken = extractClientToken(exchange);

        if (clientToken == null) {
            // stop right here with 401 Unauthorized
            exchange.getResponse().setStatusCode(HttpStatus.UNAUTHORIZED);
            byte[] bytes = "Missing or invalid JWT".getBytes(StandardCharsets.UTF_8);
            return exchange.getResponse()
                    .writeWith(Mono.just(exchange.getResponse()
                            .bufferFactory()
                            .wrap(bytes)));

        }

        Bucket bucket = buckets.computeIfAbsent(clientToken,
                key -> Bucket.builder()
                        .addLimit(Bandwidth.classic(10, Refill.greedy(10, Duration.ofMinutes(1)))).build());

        if (bucket.tryConsume(1)) {
            return chain.filter(exchange);
        }

        exchange.getResponse().setStatusCode(HttpStatus.TOO_MANY_REQUESTS);
        byte[] body = "Too many requests — Rate limit exceeded".getBytes(StandardCharsets.UTF_8);
        return exchange.getResponse().writeWith(Mono.just(exchange.getResponse().bufferFactory().wrap(body)));
    }

    private String extractClientToken(ServerWebExchange exchange) {

        ServerHttpRequest request = exchange.getRequest();
        String jwt = jwtUtils.parseJwt(request);
        String path = request.getURI().getPath();

        if (jwt == null) {
            if (path.startsWith("/login")) {
                return Objects.requireNonNull(exchange.getRequest().getRemoteAddress()).getHostString();

            } else {
                return null;
            }
        }
        return jwt;
    }

    @Override
    public int getOrder() {

        return -50; // Higher than AuthenticationGlobalFilter's new value
    }
}

