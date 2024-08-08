package com.t0khyo.library.model.entity;

import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Patron extends BaseEntity {
    private String firstName;
    private String lastName;

    @Embedded
    private ContactInfo contactInfo;
}
