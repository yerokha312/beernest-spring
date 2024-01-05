package com.neobis.yerokha.beernestspring.exception;

public class BeerDoesNotExistException extends RuntimeException {

    public BeerDoesNotExistException(String message) {
        super(message);
    }
}
