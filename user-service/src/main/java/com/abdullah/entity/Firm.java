package com.abdullah.entity;

import lombok.*;
import org.hibernate.validator.constraints.UniqueElements;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.domain.Persistable;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.UUID;


@Table(name = "firms")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
@ToString
public class Firm implements Serializable, Persistable<UUID> {
    @Id
    @Column("firm_id")
    private UUID id;
    @UniqueElements
    private String name;
    private Integer employeeCount;
    private String category;
    private Boolean isActive = true;
    private String email;
    @CreatedDate
    private LocalDate creationDate;


    public UUID getId(){
        return this.id;
    }

    @Transient
    @Builder.Default
    private boolean isNewFirm = true;

    public boolean isNew() {
        return isNewFirm;
    }

}
