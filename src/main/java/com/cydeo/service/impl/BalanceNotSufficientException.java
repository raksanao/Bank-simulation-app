package com.cydeo.service.impl;

public class BalanceNotSufficientException extends Throwable {
    public BalanceNotSufficientException(String message ) {
        super(message);
    }
}
