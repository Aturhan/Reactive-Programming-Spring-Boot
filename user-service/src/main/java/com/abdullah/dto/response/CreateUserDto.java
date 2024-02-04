package com.abdullah.dto.response;

import lombok.Builder;

@Builder
public class CreateUserDto {
    private String message;
    private Boolean success;
    private CreateUserResponse response;
}
