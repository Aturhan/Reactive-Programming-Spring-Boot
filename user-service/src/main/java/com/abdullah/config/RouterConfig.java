package com.abdullah.config;

import com.abdullah.handler.FirmHandler;
import com.abdullah.handler.UserHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerResponse;

import static org.springframework.web.reactive.function.server.RequestPredicates.*;
import static org.springframework.web.reactive.function.server.RouterFunctions.route;

@Configuration
public class RouterConfig {
    private static final String USER_ROUTE = "/v1/user";
    private static final String FIRM_ROUTE = "/v1/firm";

    private final UserHandler userHandler;
    private final FirmHandler firmHandler;

    public RouterConfig(UserHandler userHandler, FirmHandler firmHandler) {
        this.userHandler = userHandler;
        this.firmHandler = firmHandler;
    }

    @Bean
    public RouterFunction<ServerResponse> userRoutes() {

        return RouterFunctions.nest(
                path(USER_ROUTE),
                route(GET("/"),userHandler::findAll)
                        .andRoute(GET("/{id}"),userHandler::findById)
                        .andRoute(POST("/"), userHandler::create)
                        .andRoute(DELETE("/{id}"), userHandler::delete));

    }

    @Bean
    public RouterFunction<ServerResponse> firmRoutes() {

        return RouterFunctions.nest(
                path(FIRM_ROUTE),
                route(GET("/"),firmHandler::findAll)
                        .andRoute(GET("/{id}"),firmHandler::findById)
                        .andRoute(GET("/name/{name}"),firmHandler::getFirmDetailsByName)
                        .andRoute(POST("/"), firmHandler::create)
                        .andRoute(DELETE("/{id}"), firmHandler::delete));

    }

}
