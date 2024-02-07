package com.abdullah.service;


import com.abdullah.dto.response.CreateUserResponse;
import com.abdullah.dto.request.CreateUserRequest;

import org.springframework.stereotype.Service;

import reactor.core.publisher.Mono;

import java.util.List;
import java.util.UUID;

@Service
public interface IUserService {
    Mono<CreateUserResponse> create(CreateUserRequest request);
    Mono<List<CreateUserResponse>> findAll();
    Mono<CreateUserResponse> findById(UUID id);
    Mono<Void> delete(UUID id);

}
