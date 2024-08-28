package com.t0khyo.library.exception;

import jakarta.persistence.EntityNotFoundException;

public class BorrowingRecordNotFoundException extends EntityNotFoundException {
    public BorrowingRecordNotFoundException() {
        super("No active borrowing record found for this book and patron.");
    }
}
