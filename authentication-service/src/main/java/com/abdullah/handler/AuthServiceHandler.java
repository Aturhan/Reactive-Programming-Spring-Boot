package com.abdullah.handler;


import com.abdullah.dto.CreateUserRequest;
import com.abdullah.dto.AuthResponse;
import com.abdullah.service.AuthService;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.server.ServerRequest;

import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

@Service
public class AuthServiceHandler {
    private final AuthService authService;

    public AuthServiceHandler(AuthService authService) {
        this.authService = authService;
    }

    public Mono<ServerResponse> registerHandler(ServerRequest request) {
        Mono<CreateUserRequest> userRequestMono = request.bodyToMono(CreateUserRequest.class);
        return ServerResponse.ok().body(userRequestMono.flatMap(authService::register), AuthResponse.class);
    }

}
