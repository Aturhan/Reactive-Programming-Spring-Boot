package com.abdullah.dto.request;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.Builder;



@Builder
public record   CreateJobAdvertRequest(
        @NotBlank
        String title,
        @NotBlank
        String description,
        @NotBlank
        String firmName,
        @Min(2)
        @Max(14)
        Integer dayCount
) {
}
