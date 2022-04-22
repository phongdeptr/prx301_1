package com.example.prx301.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.HashMap;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class StudentValidationResult {
    private String idError;
    private String firstNameError;
    private String lastNameError;
    private String dobErr;
    private String emailErr;
    private String sexErr;
    private String phoneErr;
    private String majorErr;

    public boolean hasError() {
        boolean hasErr = false;
        HashMap<String, Boolean> result = new HashMap<>();
        result.put("firstNameError", firstNameError.isBlank());
        result.put("lastNameError", lastNameError.isBlank());
        result.put("dobErr", dobErr.isBlank());
        result.put("emailErr", emailErr.isBlank());
        result.put("sexErr", sexErr.isBlank());
        result.put("majorErr", majorErr.isBlank());
        for (String key : result.keySet()) {
            if (!result.get(key)) {
                hasErr = true;
            }
        }
        return hasErr;
    }

    public HashMap<String, String> getValidationResult() {
        HashMap<String, String> errorList = new HashMap<>();
        if (firstNameError != null && !firstNameError.isBlank()) {
            errorList.put(firstNameError, firstNameError);
        }
        if (lastNameError != null && !lastNameError.isBlank()) {
            errorList.put(lastNameError, lastNameError);
        }
        if (dobErr != null && !dobErr.isBlank()) {
            errorList.put(dobErr, lastNameError);
        }
        if (emailErr != null && !emailErr.isBlank()) {
            errorList.put(emailErr, emailErr);
        }
        if (sexErr != null && !sexErr.isBlank()) {
            errorList.put(sexErr, sexErr);
        }
        if (majorErr != null && !majorErr.isBlank()) {
            errorList.put(majorErr, majorErr);
        }
        return errorList;
    }
}
