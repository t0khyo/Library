package com.t0khyo.library.model.dto.request;

import com.t0khyo.library.model.dto.common.ContactInfoDTO;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;

@Builder
public record PatronRequest(
        String firstName,
        String lastName,
        @Valid
        @NotNull
        ContactInfoDTO contactInfo
) {
}
