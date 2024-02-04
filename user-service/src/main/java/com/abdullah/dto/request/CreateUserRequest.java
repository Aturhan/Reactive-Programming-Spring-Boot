package com.abdullah.dto.request;


import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Builder;



@Builder
public record CreateUserRequest(
        @NotBlank
        String name,
        @NotBlank
        String title,
        @Email
        @NotBlank
        String email,
        @NotBlank
        String password,
        @NotBlank
        String interest,
        @NotBlank
        String role
) {
}
