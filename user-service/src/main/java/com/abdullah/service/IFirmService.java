package com.abdullah.service;

import com.abdullah.dto.request.CreateFirmRequest;
import com.abdullah.dto.response.CreateFirmDto;
import com.abdullah.dto.response.CreateFirmResponse;
import com.abdullah.dto.response.CreateUserDto;
import com.abdullah.dto.response.FirmDetailsResponse;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.UUID;

@Service
public interface IFirmService {
    Mono<CreateFirmResponse> create(CreateFirmRequest request);
    Mono<List<CreateFirmResponse>> findAll();
    Mono<CreateFirmResponse> findById(UUID id);
    Mono<Void> delete(UUID id);
    Mono<FirmDetailsResponse> getFirmDetailsByName(String name);
}
