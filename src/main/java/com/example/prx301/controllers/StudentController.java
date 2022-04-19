package com.example.prx301.controllers;

import com.example.prx301.dto.StudentDTO;
import com.example.prx301.dto.StudentValidationResult;
import com.example.prx301.exceptions.StudentException;
import com.example.prx301.services.StudentService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class StudentController {
    private StudentService studentService;

    public StudentController(StudentService studentService) {
        this.studentService = studentService;
    }

    @GetMapping("/students")
    public List<StudentDTO> findStudent(@RequestParam("keyword") String keyword) {
       return studentService.searchStudent(keyword);
    }

    @PostMapping("/students")
    public StudentDTO createStudent(StudentDTO studentDTO) throws StudentException {
        return studentService.addStudent(studentDTO);
    }

    @PutMapping("/students")
    public StudentDTO updateStudent(StudentDTO dto) throws StudentException {
        StudentDTO updateStudent = studentService.updateStudent(dto);
        return (updateStudent);
    }

    @DeleteMapping("/students/{studentId}")
    public String deteteStudent(@PathVariable("studentId") String studentId) {
        String deleteMessage = studentService.removeStudent(studentId);
        return deleteMessage;
    }
}
