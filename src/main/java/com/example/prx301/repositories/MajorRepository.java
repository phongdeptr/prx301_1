package com.example.prx301.repositories;

import com.example.prx301.dto.MajorDTO;
import com.example.prx301.dto.StudentError;
import com.example.prx301.errors.MajorError;

import java.util.List;

public interface MajorRepository <T, E extends MajorError>{
    T createMajor(T dto);
    List<T> loadMajor();
    T updateMajor(T dto);
    T removeMajor(String majorId);
}
