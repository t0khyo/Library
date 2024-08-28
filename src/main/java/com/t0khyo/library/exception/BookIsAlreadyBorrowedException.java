package com.t0khyo.library.exception;

import java.text.MessageFormat;

public class BookIsAlreadyBorrowedException extends RuntimeException {
    public BookIsAlreadyBorrowedException(String message) {
        super(message);
    }

    public BookIsAlreadyBorrowedException(Long id) {
        super(MessageFormat.format("Book with id: {0} is already borrowed.", id));
    }
}
