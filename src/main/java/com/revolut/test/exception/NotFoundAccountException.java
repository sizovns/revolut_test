package com.revolut.test.exception;

public class NotFoundAccountException extends RuntimeException {

    public NotFoundAccountException() {
        super("Not found account exception");
    }

    public NotFoundAccountException(String text) {
        super(text);
    }
}
