package com.t0khyo.library.model.dto.common;

import com.t0khyo.library.model.dto.response.BookResponse;
import com.t0khyo.library.model.dto.response.PatronResponse;
import lombok.Builder;

import java.time.LocalDate;

@Builder
public record BorrowingRecordDTO(
        Long id,
        BookResponse book,
        PatronResponse patron,
        boolean returned,
        LocalDate borrowDate,
        LocalDate returnDate
) {
}
