package com.abdullah.config;

import com.abdullah.handler.JobAdvertHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerResponse;

import static org.springframework.web.reactive.function.server.RequestPredicates.*;
import static org.springframework.web.reactive.function.server.RouterFunctions.route;

@Configuration
public class RouterConfig {
    private static final String ROUTE = "/v1/job/";

    private final JobAdvertHandler jobAdvertHandler;

    public RouterConfig(JobAdvertHandler jobAdvertHandler) {
        this.jobAdvertHandler = jobAdvertHandler;
    }
    @Bean
    public RouterFunction<ServerResponse> jobRoutes(){
        return RouterFunctions.nest(
                path(ROUTE),
                        route(POST("/"),jobAdvertHandler::save)
                        .andRoute(GET("/{id}"),jobAdvertHandler::getJobAdvertById)
                                .andRoute(GET("/"),jobAdvertHandler::getAllJobAdvert)
                                .andRoute(GET("/title/{title}"),jobAdvertHandler::getJobAdvertByTitle)
                                .andRoute(DELETE("/{id}"),jobAdvertHandler::deleteById)

        );
    }
}
