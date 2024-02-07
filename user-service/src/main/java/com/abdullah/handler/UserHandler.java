package com.abdullah.handler;


import com.abdullah.dto.request.CreateUserRequest;

import com.abdullah.dto.response.CreateUserResponse;
import com.abdullah.service.IUserService;

import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;

import reactor.core.publisher.Mono;

import java.util.List;
import java.util.UUID;

@Service
public class UserHandler {
    private final IUserService userService;

    public UserHandler(IUserService userService) {
        this.userService = userService;
    }

    public Mono<ServerResponse> create(ServerRequest request){
        Mono<CreateUserRequest> userRequestMono = request.bodyToMono(CreateUserRequest.class);
        return ServerResponse.ok().body(userRequestMono.flatMap(userService::create), CreateUserResponse.class);
    }

    public Mono<ServerResponse> findById(ServerRequest request) {
        UUID id = UUID.fromString(request.pathVariable("id"));
        return ServerResponse.ok()
                .body(userService.findById(id), CreateUserResponse.class);
    }

    public Mono<ServerResponse> findAll(ServerRequest request){
        return ServerResponse.ok().body(userService.findAll(), List.class)
                .switchIfEmpty(Mono.defer(() -> Mono.error(new RuntimeException("No user found"))));
    }

    public Mono<ServerResponse> delete(ServerRequest request) {
        UUID id = UUID.fromString(request.pathVariable("id"));
        return ServerResponse.ok().body(userService.delete(id),Void.class);

    }


}
