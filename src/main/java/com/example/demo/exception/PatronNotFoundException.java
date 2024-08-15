package com.example.demo.exception;

public class PatronNotFoundException extends RuntimeException {
    public PatronNotFoundException(Long id) {
        super("Could not find patron " + id);
    }
}
