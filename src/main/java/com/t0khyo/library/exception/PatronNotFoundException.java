package com.t0khyo.library.exception;

import jakarta.persistence.EntityNotFoundException;

import java.text.MessageFormat;

public class PatronNotFoundException extends EntityNotFoundException {
    public PatronNotFoundException(String message) {
        super(message);
    }

    public PatronNotFoundException(Long id) {
        super(MessageFormat.format("Patron with id: {0} not found.", id));
    }
}
