package com.neobis.yerokha.beernestspring.exception;

public class UnableToCancelException extends RuntimeException {
    public UnableToCancelException(String message) {
        super(message);
    }
}
