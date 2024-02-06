package com.abdullah.entity;

import lombok.*;

import java.util.UUID;
@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class FirmDetails {
    private String name;
    private String email;
    private String category;

}
