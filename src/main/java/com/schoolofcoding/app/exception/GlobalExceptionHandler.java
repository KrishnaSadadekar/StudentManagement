package com.schoolofcoding.app.exception;

import org.springframework.http.HttpRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<?> catchUserNotFoundException(UserNotFoundException message){
        return new ResponseEntity<>(message.getMessage(),HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(PaymentNotCompletedException.class)
    public ResponseEntity<?> catchPaymentNotCompleted(PaymentNotCompletedException message)
    {
        return  new ResponseEntity<>(message.getMessage(),HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(BatchNotFoundException.class)
    public ResponseEntity<?> catchBatchNotFound(BatchNotFoundException message)
    {
        return  new ResponseEntity<>(message.getMessage(),HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<?> catchException(RuntimeException message)
    {
        return  new ResponseEntity<>(message.getMessage(),HttpStatus.BAD_REQUEST);
    }
}
