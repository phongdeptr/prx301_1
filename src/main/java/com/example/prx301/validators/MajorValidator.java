package com.example.prx301.validators;

import com.example.prx301.dto.MajorDTO;
import com.example.prx301.errors.MajorValidationResult;
import com.example.prx301.repositories.MajorRepository;
import lombok.Data;
import org.springframework.stereotype.Component;

import java.util.regex.Pattern;

@Data
@Component
public class MajorValidator {
    private boolean hasErr;
    MajorRepository<MajorDTO> repository;
    public MajorValidationResult checkValidation(MajorDTO dto){
        String majorIdError = null;
        String majorNameError = null;
        Pattern idPattern = null;
        Pattern namePattern = null;
        if(dto.getId() == null || dto.getId().isBlank()){
            majorIdError = "Id of major is empty";
            hasErr = true;
        }else{
            idPattern = Pattern.compile("[A-Z]{2,2}[0-9]{1,}");
            boolean matches = idPattern.matcher(dto.getId()).matches();
            majorIdError = matches ?null: "Id must be with 2 letter and follow be digit from 0-9";
        }
        if(dto.getName() == null || dto.getName().isBlank()){
            majorIdError = "name of major is empty";
        }else{
            namePattern = Pattern.compile("[A-Za-z]");
            boolean matches = namePattern.matcher(dto.getName()).matches();
            majorNameError = matches ? null :"major must contain only alphabet";
        }
        return new MajorValidationResult(majorIdError,majorNameError);
    }
}
