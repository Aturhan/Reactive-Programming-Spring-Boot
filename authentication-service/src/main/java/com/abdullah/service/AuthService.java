package com.abdullah.service;


import com.abdullah.dto.CreateUserRequest;
import com.abdullah.dto.CreateUserResponse;
import com.abdullah.dto.AuthResponse;
import org.mindrot.jbcrypt.BCrypt;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Service
public class AuthService {

    private final WebClient webClient;
    private final JwtUtil jwtUtil;

    public AuthService(WebClient.Builder webClientBuilder, JwtUtil jwtUtil) {
        this.webClient = webClientBuilder.baseUrl("http://localhost:8087").build();
        this.jwtUtil = jwtUtil;
    }

    public Mono<AuthResponse> register(CreateUserRequest userRequest) {
         userRequest.setPassword(BCrypt.hashpw(userRequest.getPassword(),BCrypt.gensalt()));
        return webClient.post()
                .uri("/v1/user/")
                .body(BodyInserters.fromValue(userRequest))
                .retrieve()
                .bodyToMono(CreateUserResponse.class)
                .map(user -> {
                    String accessToken = jwtUtil.generate(user.id().toString(),user.email(),"ACCESS");
                    String refreshToken = jwtUtil.generate(user.id().toString(),user.email(),"REFRESH");
                    return new AuthResponse(accessToken,refreshToken);
                })
                .onErrorResume(error -> Mono.error(new RuntimeException("User registration failed")));
    }


}
