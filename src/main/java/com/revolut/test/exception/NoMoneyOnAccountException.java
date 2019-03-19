package com.revolut.test.exception;

public class NoMoneyOnAccountException extends RuntimeException {

    public NoMoneyOnAccountException() {
        super("No money on account exception");
    }

    public NoMoneyOnAccountException(String text) {
        super(text);
    }
}
