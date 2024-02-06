package com.abdullah.service;

import com.abdullah.dto.request.CreateFirmRequest;

import com.abdullah.dto.response.CreateFirmResponse;


import com.abdullah.dto.response.FirmDetailsResponse;
import com.abdullah.entity.Firm;
import com.abdullah.repository.FirmRepository;
import jakarta.ws.rs.NotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;


@Service
@Slf4j
public class FirmService implements IFirmService{
    private final FirmRepository firmRepository;
    private final UserService userService;

    public FirmService(FirmRepository firmRepository, UserService userService) {
        this.firmRepository = firmRepository;
        this.userService = userService;
    }

    @Override
    public Mono<CreateFirmResponse> create(CreateFirmRequest request) {

        return userService.checkUser(request.email())
                        .flatMap(result -> {
                            log.info("result == " + result);
                            if (result) {
                                return firmRepository.save(Firm.builder()
                                                .id(UUID.randomUUID())
                                                .name(request.name())
                                                .employeeCount(request.employeeCount())
                                                .creationDate(LocalDate.now())
                                                .category(request.category())
                                                .email(request.email())
                                                .isActive(true)
                                                .build())
                                        .log()
                                        .map(saved -> CreateFirmResponse.builder()
                                                .id(saved.getId())
                                                .name(saved.getName())
                                                .email(saved.getEmail())
                                                .employeeCount(saved.getEmployeeCount())
                                                .category(saved.getCategory())
                                                .build());
                            } else {
                                log.error("User check failed for email: " + request.email());
                                return Mono.error(new RuntimeException("User check failed"));
                            }
                        });

        };







    @Override
    public Mono<List<CreateFirmResponse>> findAll() {
        return firmRepository.findAll()
                .filter(Firm::getIsActive)
                .map(firm
                        -> CreateFirmResponse.builder()
                        .id(firm.getId())
                        .name(firm.getName())
                        .category(firm.getCategory())
                        .email(firm.getEmail())
                        .employeeCount(firm.getEmployeeCount())
                        .build()).collect(Collectors.toList());

    }

    @Override
    public Mono<CreateFirmResponse> findById(UUID id) {
        return firmRepository.findById(id)
                .filter(Firm::getIsActive)
                .map(firm -> CreateFirmResponse.builder()
                        .id(firm.getId())
                        .name(firm.getName())
                        .email(firm.getEmail())
                        .category(firm.getCategory())
                        .employeeCount(firm.getEmployeeCount())
                        .build())
                        .log()
                        .switchIfEmpty(Mono.defer(() -> Mono.error(new RuntimeException("Firm not found"))));

    }

    @Override
    public Mono<Void> delete(UUID id) {
        return firmRepository.deleteById(id)
                .onErrorResume(NotFoundException.class, ex -> ServerResponse.notFound().build().then());
    }
    @Override
    public Mono<FirmDetailsResponse> getFirmDetailsByName(String name) {
        log.info("request incoming: "+name);
        return firmRepository.findByName(name)
                .log(log.getName())
                .flatMap(firm ->{
                    log.info("firm: "+firm);
                    if (firm != null){

                        return Mono.just(FirmDetailsResponse.builder()
                                .name(firm.getName())
                                .email(firm.getEmail())
                                .category(firm.getCategory())
                                .build());
                    } else {
                        return null;
                    }
                    });
                }

}
