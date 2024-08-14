package com.t0khyo.library.exception;

public class BookIsAlreadyBorrowedException extends RuntimeException {
    public BookIsAlreadyBorrowedException(String message) {
        super(message);
    }
}
