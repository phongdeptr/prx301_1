package com.example.prx301.services;

import com.example.prx301.dto.MajorDTO;
import com.example.prx301.errors.MajorError;
import com.example.prx301.repositories.MajorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MajorService {
    @Autowired
    private MajorRepository<MajorDTO, MajorError> majorRepository;

    public List<MajorDTO> loadMajor(){
       return majorRepository.loadMajor();
    }
}