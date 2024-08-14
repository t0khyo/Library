package com.t0khyo.library.exception;

import jakarta.persistence.EntityNotFoundException;

public class BorrowingRecordNotFoundException extends EntityNotFoundException {
    public BorrowingRecordNotFoundException(String message) {
        super(message);
    }
}
