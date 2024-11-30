package com.t0khyo.library.model.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Builder;

@Builder
public record SignUpRequest(
        @NotBlank
        @Size(min = 3, max = 50)
        String username,
        @Email
        @NotBlank
        String email,
        @NotBlank
        @Size(min = 8, max = 255)
        String password
) {
}
