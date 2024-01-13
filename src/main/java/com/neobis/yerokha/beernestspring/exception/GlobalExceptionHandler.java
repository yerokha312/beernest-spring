package com.neobis.yerokha.beernestspring.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler({BeerDoesNotExistException.class})
    public ResponseEntity<String> handleBeerDoesNotExist(BeerDoesNotExistException exception) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(exception.getMessage());
    }

    @ExceptionHandler({EmailAlreadyTakenException.class})
    public ResponseEntity<String> handleEmailAlreadyTaken(EmailAlreadyTakenException exception) {
        return ResponseEntity.status(HttpStatus.CONFLICT).body(exception.getMessage());
    }

    @ExceptionHandler({UserDoesNotExistException.class})
    public ResponseEntity<String> handleCustomerDoesNotExist(UserDoesNotExistException exception) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(exception.getMessage());
    }

    @ExceptionHandler({PhoneNumberAlreadyTakenException.class})
    public ResponseEntity<String> handlePhoneNumberAlreadyTaken(PhoneNumberAlreadyTakenException exception) {
        return ResponseEntity.status(HttpStatus.CONFLICT).body(exception.getMessage());
    }

    @ExceptionHandler({OrderDoesNotExistException.class})
    public ResponseEntity<String> handleOrderDoesNotExist(OrderDoesNotExistException exception) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(exception.getMessage());
    }

    @ExceptionHandler({ContactInfoException.class})
    public ResponseEntity<String> handleContactInfo(ContactInfoException exception) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(exception.getMessage());
    }

    @ExceptionHandler({InvalidPasswordException.class})
    public ResponseEntity<String> handleInvalidPassword(InvalidPasswordException exception) {
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(exception.getMessage());
    }

    @ExceptionHandler({UnableToCancelException.class})
    public ResponseEntity<String> handleUnableToCancel(UnableToCancelException exception) {
        return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body(exception.getMessage());
    }

    @ExceptionHandler({OutOfStockException.class})
    public ResponseEntity<String> handleInsufficientBeerAmount(OutOfStockException exception) {
        return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body(exception.getMessage());
    }
}
