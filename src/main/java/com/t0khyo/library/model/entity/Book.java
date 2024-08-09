package com.t0khyo.library.model.entity;

import jakarta.persistence.Column;
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
public class Book extends BaseEntity {
    @Column(unique=true, nullable=false)
    private String title;
    private String author;
    private int publicationDate;
    private String ISBN;
    private boolean borrowed = false;
}
