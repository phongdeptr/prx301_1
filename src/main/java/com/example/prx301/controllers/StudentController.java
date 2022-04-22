package com.example.prx301.controllers;

import com.example.prx301.dto.StudentDTO;
import com.example.prx301.dto.StudentValidationResult;
import com.example.prx301.exceptions.StudentException;
import com.example.prx301.services.StudentService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.xml.bind.JAXBException;
import javax.xml.xpath.XPathExpressionException;
import java.util.List;

@RestController
@RequestMapping("api/v1/students")
public class StudentController {
    private StudentService studentService;

    public StudentController(StudentService studentService) {
        this.studentService = studentService;
    }

    @GetMapping
    public List<StudentDTO> findStudent(@RequestParam("keyword") String keyword) throws JAXBException {
       return studentService.searchStudent(keyword);
    }

    @PostMapping
    public StudentDTO createStudent(@RequestBody StudentDTO studentDTO) throws StudentException {
        return studentService.addStudent(studentDTO);
    }

    @PutMapping
    public ResponseEntity<StudentDTO> updateStudent(@RequestBody StudentDTO dto) {
        System.out.println("DTO on controller: " + dto);
        StudentDTO updateStudent = null;
            updateStudent = studentService.updateStudent(dto);
        return ResponseEntity.ok(updateStudent);
    }

    @DeleteMapping("/{studentId}")
    public ResponseEntity<?> deteteStudent(@PathVariable("studentId") String studentId) {
        String deleteMessage = studentService.removeStudent(studentId);
        return ResponseEntity.ok(deleteMessage);
    }

    @GetMapping("/{studentId}")
    public ResponseEntity<?> findByStudentId(@PathVariable("studentId") String studentId) {
        StudentDTO studentDTO = null;
        try {
            studentDTO = studentService.findStudentById(studentId);
        } catch (XPathExpressionException e) {
            e.printStackTrace();
        }
        return ResponseEntity.ok(studentDTO);
    }
}
