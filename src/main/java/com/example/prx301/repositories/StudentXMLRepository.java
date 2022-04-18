package com.example.prx301.repositories;

import com.example.prx301.dto.MajorDTO;
import com.example.prx301.dto.StudentDTO;
import com.example.prx301.dto.StudentError;

import javax.xml.xpath.XPathExpressionException;
import java.util.List;

public interface StudentXMLRepository<T, E extends StudentError> {
    T findStudentById(String studentId) throws XPathExpressionException;
    E addNewStudent(T student);
    E updateStudentInfo(T student);
    E changeStudentMajor(String studentId, MajorDTO newMajor);
    E removeStudent(String studentId);
    List<T> findStudentByName(String keyword);
}
