package com.abdullah.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class CreateUserRequest{
        @NotBlank(message = "Name field is required")
        private String name;
        @NotBlank(message = "Title field is required")
        private String title;
        @Email(message = "Invalid email")
        @NotBlank(message = "Email field is required")
        private String email;
        @NotBlank(message = "Password field is required")
        private String password;
        @NotBlank(message = "Interests field is required")
        private String interest;
        @NotBlank(message = "Role field is required")
        private String role;

}
