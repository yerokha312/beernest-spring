package com.neobis.yerokha.beernestspring.exception;

public class CustomerIdDoesNotMatch extends RuntimeException {
    public CustomerIdDoesNotMatch(String message) {
        super(message);
    }
}
