package com.example.prx301.services;

import com.example.prx301.dto.MajorDTO;
import com.example.prx301.errors.MajorValidationResult;
import com.example.prx301.exceptions.MajorException;
import com.example.prx301.repositories.MajorRepository;
import com.example.prx301.validators.MajorValidator;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class MajorService {
    private MajorRepository<MajorDTO> majorRepository;
    private MajorValidator validator;

    public MajorService(MajorRepository<MajorDTO> majorRepository, MajorValidator validator) {
        this.majorRepository = majorRepository;
        this.validator = validator;
    }

    public List<MajorDTO> loadMajor(){
       return majorRepository.loadMajor();
    }

    public MajorDTO createMajor(MajorDTO majorDTO){
        MajorValidationResult validationResult = validator.checkValidation(majorDTO);
        if(validationResult.hasErr()){
            throw new MajorException(validationResult);
        }
        MajorDTO major = majorRepository.createMajor(majorDTO);
        return major;
    }

    public MajorDTO updateMajor(MajorDTO dto) throws MajorException{
        MajorValidationResult validationResult = validator.checkValidation(dto);
        Optional<MajorDTO> updateMajorOption = majorRepository.loadMajor().stream().filter(major -> major.getId().equals(dto.getId())).findFirst();
        if(validationResult.hasErr()){
            throw new MajorException(validationResult);
        }
        if(!updateMajorOption.isPresent()){
            throw new MajorException(new MajorValidationResult("Id for target major not exist",null));
        }
        MajorDTO major = majorRepository.updateMajor(dto);
        return major;
    }

    public MajorDTO deleteMajor(MajorDTO dto) throws MajorException{
        Optional<MajorDTO> updateMajorOption = majorRepository.loadMajor().stream().filter(major -> major.getId().equals(dto.getId())).findFirst();
        if(!updateMajorOption.isPresent()){
            throw new MajorException(new MajorValidationResult("Id for target major not exist",null));
        }
        MajorDTO major = majorRepository.updateMajor(dto);
        return major;
    }
}
