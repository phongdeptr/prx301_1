package com.example.prx301.services;

import com.example.prx301.dto.StudentDTO;
import com.example.prx301.dto.StudentValidationResult;
import com.example.prx301.exceptions.StudentException;
import com.example.prx301.repositories.StudentXMLRepository;
import com.example.prx301.validators.StudentValidator;
import org.springframework.stereotype.Service;

import javax.xml.transform.TransformerException;
import javax.xml.xpath.XPathExpressionException;
import java.util.List;

@Service
public class StudentService {
    private final StudentXMLRepository<StudentDTO, StudentValidationResult> repository;
    private final StudentValidator validator;

    public StudentService(StudentXMLRepository<StudentDTO, StudentValidationResult> repository, StudentValidator validator) {
        this.repository = repository;
        this.validator = validator;
    }
    public List<StudentDTO> getStudent(){
        return repository.getAllStudent();
    }
    public StudentDTO addStudent(StudentDTO dto) throws StudentException  {
        StudentValidationResult studentValidationResult = null;
        try {
            studentValidationResult = validator.checkStudent(dto);
            if(studentValidationResult!=null){
                throw new StudentException(studentValidationResult);
            }
        } catch (XPathExpressionException e) {
            e.printStackTrace();
        }
        StudentDTO studentDTO = repository.addNewStudent(dto);
        return studentDTO;
    }
    public StudentDTO findStudentById(String id) throws XPathExpressionException {
       return repository.findStudentById(id);
    }
    public List<StudentDTO> searchStudent(String keyword){
        return repository.findStudentByName(keyword);
    }
    public StudentDTO updateStudent(StudentDTO dto) throws StudentException{
        StudentDTO result = null;
        try {
            StudentValidationResult error = validator.checkStudent(dto);
            if(error != null){
                throw new StudentException(error);
            }
            try {
               result = repository.updateStudentInfo(dto);
            } catch (XPathExpressionException e) {
                e.printStackTrace();
            }
        } catch (XPathExpressionException e) {
            e.printStackTrace();
        }
        return result;
    }
    public String removeStudent(String studentId){
        boolean isDeleteSucess = false;
        try {
            isDeleteSucess = repository.removeStudent(studentId);
        } catch (TransformerException transformerException) {
            transformerException.printStackTrace();
        }
        return isDeleteSucess?"SUCCESS":"STUDENT NOT EXIST";
    }
}
