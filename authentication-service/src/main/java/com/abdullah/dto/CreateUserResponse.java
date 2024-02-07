package com.abdullah.dto;

import java.time.LocalDate;
import java.util.UUID;

public record CreateUserResponse(
        UUID id,
        String title,
        String name,
        String email,
        String interest,
        LocalDate createdAt
) {
}
