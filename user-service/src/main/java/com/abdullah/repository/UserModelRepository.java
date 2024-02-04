package com.abdullah.repository;

import com.abdullah.entity.UserModel;
import org.springframework.data.r2dbc.repository.Modifying;
import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.r2dbc.repository.R2dbcRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;

import java.util.UUID;

@Repository
public interface UserModelRepository extends R2dbcRepository<UserModel, UUID> {


    Mono<UserModel> findByEmail(String email);

    @Modifying
    @Query("UPDATE users u SET u.isActive = false WHERE u.id = :id")
    Mono<Void> softDeleteById(@Param("id") UUID id);
}
