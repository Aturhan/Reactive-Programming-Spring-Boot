package com.abdullah.handler;

import com.abdullah.dto.request.CreateFirmRequest;
import com.abdullah.dto.response.CreateFirmResponse;
import com.abdullah.dto.response.CreateUserResponse;
import com.abdullah.service.IFirmService;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.UUID;

@Service
public class FirmHandler {
    private final IFirmService firmService;

    public FirmHandler(IFirmService firmService) {
        this.firmService = firmService;

    }

    public Mono<ServerResponse> create(ServerRequest request) {
        Mono<CreateFirmRequest> firmRequestMono = request.bodyToMono(CreateFirmRequest.class);
        return ServerResponse.ok().body(firmRequestMono.flatMap(firmService::create), CreateFirmResponse.class);
    }

    public Mono<ServerResponse> findById(ServerRequest request) {
        UUID id = UUID.fromString(request.pathVariable("id"));
        return ServerResponse.ok()
                .body(firmService.findById(id), CreateUserResponse.class);
    }

    public Mono<ServerResponse> findAll(ServerRequest request) {
        return ServerResponse.ok().body(firmService.findAll(), List.class)
                .switchIfEmpty(Mono.defer(() -> Mono.error(new RuntimeException("No firm found"))));
    }

    public Mono<ServerResponse> delete(ServerRequest request) {
        UUID id = UUID.fromString(request.pathVariable("id"));
        return ServerResponse.ok().body(firmService.delete(id), Void.class);

    }
}
