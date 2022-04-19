package com.example.prx301.controllers;

import com.example.prx301.dto.StudentDTO;
import com.example.prx301.dto.StudentValidationResult;
import com.example.prx301.exceptions.StudentException;
import com.example.prx301.services.StudentService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class StudentController {
    private StudentService studentService;

    public StudentController(StudentService studentService) {
        this.studentService = studentService;
    }

    @GetMapping("/students")
    @ApiOperation(value = "find student by first name or lastname",
    response = StudentDTO.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = ""),
            @ApiResponse(code = 400, message = "not found keyword"),
            @ApiResponse(code = 404, message = "Not found student"),
    })
    public List<StudentDTO> findStudent(@RequestParam("keyword") String keyword) {
       return studentService.searchStudent(keyword);
    }

    @PostMapping("/students")
    @ApiOperation(value = "create student",
            response = StudentDTO.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Create Student SUCCESS"),
            @ApiResponse(code = 400, message = "not found keyword"),
            @ApiResponse(code = 404, message = "Not found student"),
    })
    public StudentDTO createStudent(StudentDTO studentDTO) throws StudentException {
        return studentService.addStudent(studentDTO);
    }

    @PutMapping("/students")
    @ApiOperation(value = "update student",
            response = StudentDTO.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Update student info "),
            @ApiResponse(code = 400, message = "Student update info wrong format"),
            @ApiResponse(code = 404, message = "Not found student to update"),
    })
    public StudentDTO updateStudent(StudentDTO dto) throws StudentException {
        StudentDTO updateStudent = studentService.updateStudent(dto);
        return (updateStudent);
    }

    @DeleteMapping("/students/{studentId}")
    @ApiOperation(value = "delete student",
            response = StudentDTO.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Delete Sucess"),
            @ApiResponse(code = 400, message = "Student is wrong"),
            @ApiResponse(code = 404, message = "Student not found"),
    })
    public String deteteStudent(@PathVariable("studentId") String studentId) {
        String deleteMessage = studentService.removeStudent(studentId);
        return deleteMessage;
    }
}
