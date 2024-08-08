package com.t0khyo.library.model.dto.response;

import com.t0khyo.library.model.dto.common.ContactInfoDTO;
import lombok.Builder;

@Builder
public record PatronResponse(
        Long id,
        String firstName,
        String lastName,
        ContactInfoDTO contactInfo
) {
}
