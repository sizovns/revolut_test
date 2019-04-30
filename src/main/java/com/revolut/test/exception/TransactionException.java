package com.revolut.test.exception;

public class TransactionException extends RuntimeException {

    public TransactionException() {
        super("On transaction exception");
    }

    public TransactionException(String text) {
        super(text);
    }
}
