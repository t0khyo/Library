package com.t0khyo.library.model.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Builder;

@Builder
public record AuthRequest(
        @NotBlank
        String username,
        @NotBlank
        String password
) {
}
