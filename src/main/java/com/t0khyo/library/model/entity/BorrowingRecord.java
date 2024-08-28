package com.t0khyo.library.model.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.time.LocalDate;

@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class BorrowingRecord extends BaseEntity {
    @ManyToOne(optional=false)
    private Book book;

    @ManyToOne(optional=false)
    private Patron patron;

    private boolean returned = false;

    private LocalDate borrowDate;
    private LocalDate returnDate;
}
