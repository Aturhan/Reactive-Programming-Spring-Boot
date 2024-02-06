package com.abdullah.repository;

import com.abdullah.entity.Firm;
import org.springframework.data.r2dbc.repository.R2dbcRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;

import java.util.UUID;

@Repository
public interface FirmRepository extends R2dbcRepository<Firm, UUID> {

    Mono<Firm> findByName(String name);
}
