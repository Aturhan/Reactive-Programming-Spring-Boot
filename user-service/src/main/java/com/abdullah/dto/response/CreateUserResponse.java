package com.abdullah.dto.response;

import lombok.Builder;

import java.time.LocalDate;
import java.util.UUID;


@Builder
public record CreateUserResponse(
        UUID id,
        String title,
        String name,
        String email,
        String interest,
        LocalDate createdAt
) {
}
