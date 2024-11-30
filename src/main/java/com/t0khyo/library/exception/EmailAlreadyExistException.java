package com.t0khyo.library.exception;

public class EmailAlreadyExistException extends RuntimeException {
    public EmailAlreadyExistException() {
        super("That email already exists.");
    }
}
