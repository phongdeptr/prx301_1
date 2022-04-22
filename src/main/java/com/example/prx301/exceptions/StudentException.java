package com.example.prx301.exceptions;

import com.example.prx301.dto.StudentValidationResult;
import lombok.Data;

@Data
public class StudentException extends RuntimeException {
    public StudentException(StudentValidationResult error) {
        this.error = error;
    }
    private StudentValidationResult error;
}
