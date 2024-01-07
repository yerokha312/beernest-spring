package com.neobis.yerokha.beernestspring.exception;

public class CustomerDoesNotExistException extends RuntimeException {
    public CustomerDoesNotExistException(String message) {
        super(message);
    }
}
