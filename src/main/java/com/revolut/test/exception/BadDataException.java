package com.revolut.test.exception;

public class BadDataException extends RuntimeException {

    public BadDataException() {
        super("Bad data exception");
    }

    public BadDataException(String text) {
        super(text);
    }
}
