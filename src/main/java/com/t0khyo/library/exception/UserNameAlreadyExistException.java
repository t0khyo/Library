package com.t0khyo.library.exception;

public class UserNameAlreadyExistException extends RuntimeException {
    public UserNameAlreadyExistException() {
        super("That username already exists.");
    }
}
