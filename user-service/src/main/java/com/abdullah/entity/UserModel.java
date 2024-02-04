package com.abdullah.entity;


import lombok.*;
import org.hibernate.validator.constraints.UniqueElements;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;

import org.springframework.data.domain.Persistable;


import org.springframework.data.relational.core.mapping.Table;


import java.io.Serializable;
import java.time.LocalDate;

import java.util.UUID;

@Table(name = "users")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
@ToString
public class UserModel implements Serializable, Persistable<UUID> {
    @Id
    private UUID id;
    private String name;
    private String title;
    @UniqueElements
    private String email;
    private String password;
    private String interest;
    private String role;
    @CreatedDate
    private LocalDate createdAt;
    private Boolean isActive = true;


    public UUID getId(){
         return this.id;
    }
/*
    @Transient
    private boolean isUpdated;

    @Override
    @Transient
    public boolean isNew() {
        return !this.isUpdated && this.id == null;
    }

 */

    @Transient
    @Builder.Default
    private boolean isNewUser = true;

    public boolean isNew() {
        return isNewUser;
    }

}
