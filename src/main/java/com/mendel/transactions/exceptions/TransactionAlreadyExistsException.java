package com.mendel.transactions.exceptions;

public class TransactionAlreadyExistsException extends RuntimeException {
    public TransactionAlreadyExistsException(String message) {
        super(message);
    }
}
