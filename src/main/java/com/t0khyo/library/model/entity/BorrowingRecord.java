package com.t0khyo.library.model.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class BorrowingRecord extends BaseEntity {
    @ManyToOne
    private Book book;
    @ManyToOne
    private Patron patron;

    private LocalDate borrowDate;
    private LocalDate returnDate;
}
