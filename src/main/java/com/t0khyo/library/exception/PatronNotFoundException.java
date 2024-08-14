package com.t0khyo.library.exception;

import jakarta.persistence.EntityNotFoundException;

public class PatronNotFoundException extends EntityNotFoundException {
    public PatronNotFoundException(String message) {
        super(message);
    }
}
