package com.t0khyo.library.exception.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@AllArgsConstructor
@Builder
public class ValidationSubError extends SubErrorResponse {
    private String object;
    private String field;
    private Object rejectedValue;
    private String message;
}
