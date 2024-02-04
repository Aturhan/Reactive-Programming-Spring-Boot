package com.abdullah.dto.response;

import lombok.Builder;
import lombok.Getter;

@Builder
public record CreateFirmDto(
        String message,
        Boolean success,
        CreateFirmResponse response
){
}
