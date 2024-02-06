package com.abdullah.handler;

import com.abdullah.dto.request.CreateJobAdvertRequest;
import com.abdullah.dto.response.JobAdvertResponse;
import com.abdullah.service.JobAdvertService;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

import java.util.List;

import static org.springframework.web.reactive.function.BodyInserters.fromValue;

@Component
public class JobAdvertHandler {
    private final JobAdvertService jobAdvertService;

    public JobAdvertHandler(JobAdvertService jobAdvertService) {
        this.jobAdvertService = jobAdvertService;
    }

    public Mono<ServerResponse> save(ServerRequest request) {
        Mono<CreateJobAdvertRequest> mono = request.bodyToMono(CreateJobAdvertRequest.class);
        return ServerResponse.ok().body(mono.flatMap(jobAdvertService::save), JobAdvertResponse.class);
    }

    public Mono<ServerResponse> getJobAdvertById(ServerRequest request) {
        String id = request.pathVariable("id");
        return ServerResponse.status(HttpStatus.FOUND).body(jobAdvertService.findAdvertById(id), JobAdvertResponse.class);
    }

    public Mono<ServerResponse> getAllJobAdvert(ServerRequest request) {
        return jobAdvertService.getAll().collectList()
                .flatMap(jobList -> {
                    if (jobList.isEmpty()){
                        return ServerResponse.noContent().build();
                    }
                    return ServerResponse.ok().body(jobAdvertService.getAll(), JobAdvertResponse.class);
                });
    }

    public Mono<ServerResponse> getJobAdvertByTitle(ServerRequest request) {
        String title = request.pathVariable("title");
        return jobAdvertService.findByTitle(title).collectList()
                .flatMap(jobList -> {
                    if (jobList.isEmpty()){
                        return ServerResponse.noContent().build();
                    }
                    return ServerResponse.ok().body(jobAdvertService.findByTitle(title), JobAdvertResponse.class);
                });
    }

    public Mono<ServerResponse> deleteById(ServerRequest request) {
        String id = request.pathVariable("id");
        return jobAdvertService.deleteJobAdvertById(id)
                .then(ServerResponse.noContent().build())
                .switchIfEmpty(ServerResponse.notFound().build());
    }
}
