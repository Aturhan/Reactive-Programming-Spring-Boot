package com.abdullah.service;


import com.abdullah.dto.request.CreateUserRequest;
import com.abdullah.dto.response.CreateUserResponse;
import com.abdullah.entity.Enumerations.Role;
import com.abdullah.entity.UserModel;
import com.abdullah.repository.UserModelRepository;
import jakarta.ws.rs.NotFoundException;
import lombok.extern.slf4j.Slf4j;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;


import java.time.LocalDate;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@Slf4j
public class UserService implements IUserService {
    private final UserModelRepository userModelRepository;

    public UserService(UserModelRepository userModelRepository) {
        this.userModelRepository = userModelRepository;

    }
    @Transactional
    @Override
    public Mono<CreateUserResponse> create(CreateUserRequest request) {
            UserModel userModel = UserModel.builder()
                    .id(UUID.randomUUID())
                    .name(request.name())
                    .title(request.title())
                    .email(request.email())
                    .password(request.password())
                    .role(request.role())
                    .interest(request.interest())
                    .createdAt(LocalDate.now())
                    .isActive(false)
                    .build();
            return userModelRepository.save(userModel)
                    .log()
                    .map(saved -> CreateUserResponse.builder()
                               .id(saved.getId())
                               .name(saved.getName())
                               .title(saved.getTitle())
                               .email(saved.getEmail())
                               .interest(saved.getInterest())
                               .createdAt(saved.getCreatedAt())
                               .build());

    }

    @Override
    public Mono<CreateUserResponse> findById(UUID id) {
        return userModelRepository.findById(id)
                .filter(UserModel::getIsActive)
                .map(userModel -> CreateUserResponse.builder()
                        .id(userModel.getId())
                        .name(userModel.getName())
                        .title(userModel.getTitle())
                        .email(userModel.getEmail())
                        .interest(userModel.getInterest())
                        .createdAt(userModel.getCreatedAt())
                        .build())
                .log()
                .switchIfEmpty(Mono.defer(() ->
                        Mono.error(new RuntimeException("User not found"))));

    }



    @Override
    public Mono<List<CreateUserResponse>>findAll() {
            return userModelRepository.findAll()
                    .filter(UserModel::getIsActive)
                    .map(user ->
                          CreateUserResponse.builder()
                                  .id(user.getId())
                                  .title(user.getTitle())
                                  .name(user.getName())
                                  .email(user.getEmail())
                                  .interest(user.getInterest())
                                  .createdAt(user.getCreatedAt())
                                  .build()).collect(Collectors.toList());
    }


    @Override
    public Mono<Void> delete(UUID id) {
        return userModelRepository.deleteById(id)
                .onErrorResume(NotFoundException.class, ex -> ServerResponse.notFound().build().then());
    }

    protected Mono<Boolean> checkUser(String email) {
        log.info("User service check == "+email);
        return userModelRepository.findByEmail(email)
                .map(user -> user.getRole().equals(Role.OWNER.getValue()));

    }

    private Mono<CreateUserResponse> findByEmail(String email) {
        return userModelRepository.findByEmail(email)
                .map(user ->
                        CreateUserResponse.builder()
                        .id(user.getId())
                        .email(user.getEmail())
                        .title(user.getTitle())
                        .name(user.getName())
                        .interest(user.getInterest())
                        .createdAt(user.getCreatedAt())
                        .build());
    }


}
