package com.t0khyo.library.model.dto.request;

import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import org.hibernate.validator.constraints.ISBN;

@Builder
public record BookRequest(
        @NotNull String title,
        @NotNull String author,
        @NotNull int publicationDate,
        @NotNull @ISBN String isbn
) {
}