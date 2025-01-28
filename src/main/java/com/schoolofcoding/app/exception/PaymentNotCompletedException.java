package com.schoolofcoding.app.exception;

public class PaymentNotCompletedException extends  RuntimeException{
    private  String message;

    public PaymentNotCompletedException(String message) {
        this.message=message;
    }

    @Override
    public String getMessage() {
        return super.getMessage();
    }
}
