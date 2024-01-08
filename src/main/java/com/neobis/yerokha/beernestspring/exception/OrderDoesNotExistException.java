package com.neobis.yerokha.beernestspring.exception;

public class OrderDoesNotExistException extends RuntimeException {
    public OrderDoesNotExistException(String message) {
        super(message);
    }
}
