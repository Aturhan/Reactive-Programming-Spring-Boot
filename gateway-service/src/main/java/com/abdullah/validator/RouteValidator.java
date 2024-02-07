package com.abdullah.validator;

import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.function.Predicate;

@Service
public class RouteValidator {
    public static final List<String> publicEndpoints = List.of(
            "/v1/auth/register",
            "/eureka"
    );

    public Predicate<ServerHttpRequest> isSecured = request -> publicEndpoints.stream()
            .noneMatch(uri -> request.getURI().getPath().contains(uri));
}
