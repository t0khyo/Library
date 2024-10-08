package com.t0khyo.library.model.dto.common;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.Builder;

@Builder
public record ContactInfoDTO(
        @Pattern(regexp="^[+]?[(]?[0-9]{3}[)]?[-\\s.]?[0-9]{3}[-\\s.]?[0-9]{4,6}$")
        @NotNull
        String phone,
        @Email String email,
        String address
) {
}
