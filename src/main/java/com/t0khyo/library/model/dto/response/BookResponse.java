package com.t0khyo.library.model.dto.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.t0khyo.library.model.dto.common.BorrowingRecordDTO;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;

import java.util.List;

@Builder
public record BookResponse(
        Long id,
        String title,
        String author,
        int publicationDate,
        String ISBN,
        boolean borrowed,

        @Schema(description="This field is not included when you get all books.")
        @JsonInclude(JsonInclude.Include.NON_NULL)
        List<BorrowingRecordDTO> borrowingRecords
) {
}
