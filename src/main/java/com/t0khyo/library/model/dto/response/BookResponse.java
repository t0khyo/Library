package com.t0khyo.library.model.dto.response;

import lombok.Builder;

@Builder
public record BookResponse(
        Long id,
        String title,
        String author,
        int publicationDate,
        String ISBN
) {
}
