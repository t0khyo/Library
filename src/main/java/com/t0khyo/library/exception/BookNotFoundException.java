package com.t0khyo.library.exception;

import jakarta.persistence.EntityNotFoundException;

import java.text.MessageFormat;

public class BookNotFoundException extends EntityNotFoundException {
    public BookNotFoundException(String message) {
        super(message);
    }
    public BookNotFoundException(Long id) {
        super(MessageFormat.format("Book with id: {0} not found.", id));
    }
}
