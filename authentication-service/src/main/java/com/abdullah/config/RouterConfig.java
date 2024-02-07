package com.abdullah.config;

import com.abdullah.handler.AuthServiceHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerResponse;

import static org.springframework.web.reactive.function.server.RequestPredicates.POST;
import static org.springframework.web.reactive.function.server.RequestPredicates.path;
import static org.springframework.web.reactive.function.server.RouterFunctions.route;

@Configuration
public class RouterConfig {
    private static final String AUTH_ROUTE = "/v1/auth";
    private final AuthServiceHandler authServiceHandler;

    public RouterConfig(AuthServiceHandler authServiceHandler) {
        this.authServiceHandler = authServiceHandler;
    }

    @Bean
    public RouterFunction<ServerResponse> authRoutes() {
        return RouterFunctions.nest(
                path(AUTH_ROUTE),
                route(POST("/register"),authServiceHandler::registerHandler)

        );
    }
}
