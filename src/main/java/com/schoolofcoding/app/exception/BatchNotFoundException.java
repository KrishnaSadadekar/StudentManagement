package com.schoolofcoding.app.exception;

public class BatchNotFoundException extends RuntimeException{

    private  String message;


    public BatchNotFoundException(String message) {

        this.message = message;
    }

    @Override
    public String getMessage() {
        return super.getMessage();
    }
}
