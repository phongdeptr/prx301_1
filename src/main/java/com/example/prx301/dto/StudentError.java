package com.example.prx301.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class StudentError {
    private String firstNameError;
    private String lastNameError;
    private String dobErr;
    private String emailErr;
    private String sexErr;
    private String majorErr;

}
