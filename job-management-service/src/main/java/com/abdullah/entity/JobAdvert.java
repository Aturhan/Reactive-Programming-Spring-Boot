package com.abdullah.entity;

import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDate;

@Document("job_adverts")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class JobAdvert {
    @Id
    private String id;
    private String title;
    private String description;
    private FirmDetails firmDetails;
    private Integer dayCount;
    @CreatedDate
    private LocalDate createdAt;
    private LocalDate closingDate;
}
