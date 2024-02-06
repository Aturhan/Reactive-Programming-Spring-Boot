package com.abdullah.dto.response;

import lombok.Builder;

@Builder
public record FirmDetailsResponse(
        String name,
        String email,
        String category
) {
}
