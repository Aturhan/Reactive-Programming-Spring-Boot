package com.abdullah.dto.request;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.Builder;

import org.hibernate.validator.constraints.UniqueElements;

import java.util.UUID;

@Builder
public record CreateFirmRequest(
        @UniqueElements
        @NotBlank
        String name,
        @Min(value = 5,message = "A firm should has at least 5 employees")
        Integer employeeCount,
        @NotBlank
        String category,
        @NotBlank
        String email
) {
}
