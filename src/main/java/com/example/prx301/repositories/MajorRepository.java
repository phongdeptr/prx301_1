package com.example.prx301.repositories;
import com.example.prx301.errors.MajorError;
import com.example.prx301.exceptions.MajorException;

import java.util.List;

public interface MajorRepository <T>{
    T createMajor(T dto) throws MajorException;
    List<T> loadMajor();
    T updateMajor(T dto) throws MajorException;
    T removeMajor(String majorId) throws MajorException;
}
