package com.neobis.yerokha.beernestspring.exception;

public class CustomerIdDoesNotMatchException extends RuntimeException {
    public CustomerIdDoesNotMatchException(String message) {
        super(message);
    }
}
