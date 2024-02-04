package com.abdullah.dto.response;

import lombok.Builder;
import org.hibernate.validator.constraints.UniqueElements;


import java.util.UUID;

@Builder
public record CreateFirmResponse (
   UUID id,
    @UniqueElements
    String name,
    Integer employeeCount,
    String category,
    String email
    ){
}
