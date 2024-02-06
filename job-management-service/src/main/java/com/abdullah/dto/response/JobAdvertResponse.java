package com.abdullah.dto.response;

import com.abdullah.entity.FirmDetails;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.Builder;

import java.time.LocalDate;

@Builder
public record JobAdvertResponse(
        String id,
        String title,
        String description,
        FirmDetails firmDetails,
        LocalDate closingDate
) {
}
