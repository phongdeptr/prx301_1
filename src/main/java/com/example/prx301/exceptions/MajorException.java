package com.example.prx301.exceptions;

import com.example.prx301.errors.MajorValidationResult;

public class MajorException extends RuntimeException {
    private MajorValidationResult message;

    public MajorException(MajorValidationResult message) {
        this.message = message;
    }
}
