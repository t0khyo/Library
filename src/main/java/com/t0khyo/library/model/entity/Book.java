package com.t0khyo.library.model.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

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

    @OneToMany(mappedBy="book", fetch=FetchType.LAZY, cascade=CascadeType.ALL, orphanRemoval=true)
    private List<BorrowingRecord> borrowingRecords;
}
