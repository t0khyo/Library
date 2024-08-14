package com.t0khyo.library.model.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.*;

@ToString
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
@Embeddable
public class ContactInfo {
    @Column(nullable=false)
    private String phone;
    private String email;
    private String address;
}
