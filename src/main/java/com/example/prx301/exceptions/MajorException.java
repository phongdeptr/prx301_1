package com.example.prx301.exceptions;

public class MajorException extends Throwable{
    private String message;

    public MajorException(String message) {
        this.message = message;
    }
}
