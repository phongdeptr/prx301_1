package com.example.prx301.errors;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class MajorValidationResult {
    private String majorIdErr;
    private String majorNameErr;

    public boolean hasErr(){
        return (majorIdErr != null || majorNameErr != null) ? true:false;
    }
}
