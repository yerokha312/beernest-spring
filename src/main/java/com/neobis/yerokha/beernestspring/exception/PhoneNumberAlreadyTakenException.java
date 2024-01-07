package com.neobis.yerokha.beernestspring.exception;

public class PhoneNumberAlreadyTakenException extends RuntimeException {
    public PhoneNumberAlreadyTakenException(String message) {
        super(message);
    }
}
