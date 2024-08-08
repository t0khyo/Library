package com.t0khyo.library.model.entity;

import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Patron extends BaseEntity {
    private String firstName;
    private String lastName;

    @Embedded
    private ContactInfo contactInfo;
}
