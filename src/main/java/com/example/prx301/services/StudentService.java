package com.example.prx301.services;

import com.example.prx301.dto.StudentDTO;
import com.example.prx301.dto.StudentValidationResult;
import com.example.prx301.entitties.Student;
import com.example.prx301.exceptions.StudentException;
import com.example.prx301.repositories.StudentXMLRepository;
import com.example.prx301.validators.StudentValidator;
import lombok.SneakyThrows;
import org.springframework.stereotype.Service;

import javax.xml.bind.JAXBException;
import javax.xml.transform.TransformerException;
import javax.xml.xpath.XPathExpressionException;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class StudentService {
    private final StudentXMLRepository<Student, StudentValidationResult> repository;
    private final StudentValidator validator;

    public StudentService(StudentXMLRepository<Student, StudentValidationResult> repository, StudentValidator validator) {
        this.repository = repository;
        this.validator = validator;
    }
    public List<StudentDTO> getStudent(){
        return repository.getAllStudent().stream().map(student -> StudentDTO.fromEntityToDto(student).orElse(null)).collect(Collectors.toList());
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
        Student student = Student.fromDtoToEntity(dto).orElse(null);
        StudentDTO studentDTO = StudentDTO.fromEntityToDto(repository.addNewStudent(student)).orElse(null);
        return studentDTO;
    }

    public StudentDTO findStudentById(String id) throws XPathExpressionException, StudentException {
       return StudentDTO.fromEntityToDto(repository.findStudentById(id)).orElse(null);
    }

    public List<StudentDTO> searchStudent(String keyword) throws JAXBException {
        return repository.findStudentByName(keyword).stream().map(student -> StudentDTO.fromEntityToDto(student).orElse(null)).collect(Collectors.toList());
    }
    public StudentDTO updateStudent(StudentDTO dto){
        System.out.println(dto.toString());
        StudentDTO result = null;
        StudentValidationResult error = null;
        try {
            error = validator.checkStudent(dto);
            if(error != null){
                throw new StudentException(error);
            }
                Student studentById = repository.findStudentById(dto.getId());
                if(studentById == null){
                    StudentValidationResult validationResult = new StudentValidationResult();
                    validationResult.setIdError("ID for updated student not exist");
                    throw new StudentException(validationResult);
                }
            Optional<Student> optionalStudent = Student.fromDtoToEntity(dto);
            result = StudentDTO.fromEntityToDto(repository.updateStudentInfo(optionalStudent.get())).orElse(null);
        }catch (XPathExpressionException | TransformerException | JAXBException exception){
            error = new StudentValidationResult();
            error.setIdError("Not found with id: " + dto.getId());
            throw new StudentException(error);
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
