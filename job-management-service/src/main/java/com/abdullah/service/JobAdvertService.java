package com.abdullah.service;

import com.abdullah.dto.request.CreateJobAdvertRequest;
import com.abdullah.dto.response.FirmDetailsResponse;
import com.abdullah.dto.response.JobAdvertResponse;
import com.abdullah.entity.FirmDetails;
import com.abdullah.entity.JobAdvert;
import com.abdullah.repository.JobAdvertRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.mongodb.core.ReactiveMongoTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import java.time.LocalDate;
import java.util.concurrent.TimeUnit;


import static org.springframework.data.mongodb.core.query.Criteria.where;
import static org.springframework.data.mongodb.core.query.Query.query;


@Service
@Slf4j
public class JobAdvertService {
    private final JobAdvertRepository jobAdvertRepository;
    private final ReactiveMongoTemplate template;
    private final WebClient webClient;


    public JobAdvertService(JobAdvertRepository jobAdvertRepository, ReactiveMongoTemplate template, WebClient.Builder webClientBuilder) {
        this.jobAdvertRepository = jobAdvertRepository;
        this.template = template;

        this.webClient = webClientBuilder.baseUrl("http://localhost:8087").build();
    }

    public Mono<JobAdvertResponse> save(CreateJobAdvertRequest request){
        LocalDate closingDate = LocalDate.now().plusDays(request.dayCount());
        return webClient
                .get()
                .uri("/v1/firm/name/{name}",request.firmName())
                .retrieve()
                .bodyToMono(FirmDetailsResponse.class)
                .log(log.getName())
                .flatMap(response -> {
                    if (response != null){

                        return jobAdvertRepository.save(JobAdvert.builder()
                                        .title(request.title())
                                        .description(request.description())
                                        .createdAt(LocalDate.now())
                                        .firmDetails(FirmDetails.builder()
                                                .name(response.name())
                                                .category(response.category().toLowerCase())
                                                .email(response.email())
                                                .build())
                                        .closingDate(closingDate)
                                        .dayCount(request.dayCount())
                                .build())
                                .map(saved -> JobAdvertResponse.builder()
                                        .id(saved.getId())
                                        .firmDetails(saved.getFirmDetails())
                                        .closingDate(saved.getClosingDate())
                                        .description(saved.getDescription())
                                        .title(saved.getTitle())
                                        .build());
                    }else {
                        return Mono.error(new RuntimeException("failed"));
                    }
                });

    }

    public Mono<JobAdvertResponse> findAdvertById(String id) {
        return jobAdvertRepository.findById(id)
                .log()
                .flatMap(job -> {
                    if (job != null){
                         return Mono.just(JobAdvertResponse.builder()
                                .id(job.getId())
                                .title(job.getTitle())
                                .firmDetails(job.getFirmDetails())
                                .description(job.getDescription())
                                .closingDate(job.getClosingDate())
                                .build());

                    }else {
                        return Mono.error(new RuntimeException("Job Not Found"));
                    }

                });
    }


    public Flux<JobAdvertResponse> getAll(){
        return jobAdvertRepository.findAll()
                .map(jobs ->
                     JobAdvertResponse.builder()
                            .id(jobs.getId())
                            .title(jobs.getTitle())
                            .description(jobs.getDescription())
                            .firmDetails(jobs.getFirmDetails())
                            .closingDate(jobs.getClosingDate())
                            .build());

    }


    public Flux<JobAdvertResponse>  findByTitle(String title){
        return template.find(query(where("title").regex(title,"i")), JobAdvert.class)
                .map(jobs -> JobAdvertResponse.builder()
                         .id(jobs.getId())
                         .title(jobs.getTitle())
                         .description(jobs.getDescription())
                         .firmDetails(jobs.getFirmDetails())
                         .closingDate(jobs.getClosingDate())
                         .build());


    }
    @Scheduled(cron = "0 20 0 * * *")
    private void checkJobCloseDate(){
        jobAdvertRepository.findAll()
                .filter(jobs -> jobs.getClosingDate().getDayOfMonth() == LocalDate.now().getDayOfMonth())
                .flatMap(job ->{
                    deleteById(job.getId());
                    return Mono.just("Job advert deleted with id: " + job.getId());
                }).subscribe(System.out::println);
    }

    private void deleteById(String id) {
         jobAdvertRepository.deleteById(id);
    }

    public Mono<Void> deleteJobAdvertById(String id) {
        return jobAdvertRepository.deleteById(id);
    }
}




