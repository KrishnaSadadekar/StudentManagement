package com.schoolofcoding.app.exception;

public class AttendanceException  extends RuntimeException{
    private  String message ;

    public AttendanceException(String message) {
        this.message=message;
    }

    @Override
    public String getMessage() {
        return super.getMessage();
    }
}
