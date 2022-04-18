package com.example.prx301.controllers;

import com.example.prx301.dto.StudentDTO;
import com.example.prx301.dto.StudentError;
import com.example.prx301.entities.Student;
import com.example.prx301.services.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@Controller("/students")
@SessionAttributes({"students","studentError","student"})
public class StudentController {
    @ModelAttribute("student")
    public StudentDTO student(){
        return new StudentDTO();
    }
    @ModelAttribute("studentError")
    public StudentError error(){
        return null;
    }

    @ModelAttribute("students")
    public List<StudentDTO>students(){
        List<StudentDTO> tmps = new ArrayList<>();

        return tmps;
    }

    private StudentService studentService;

    public StudentController(StudentService studentService) {
        this.studentService = studentService;
    }

    @PostMapping("/create")
    public String CreateStudent(StudentDTO student, ModelMap modelMap){

        StudentError studentError = studentService.addStudent(student);
        modelMap.addAttribute("studentError", studentError);
        return "createStudent";
    }

    @GetMapping("/getall")

    public  String getStudents(){
        StudentDTO dto = new StudentDTO();
        StudentError studentError = studentService.addStudent(dto);
        return "studentManagement";
    }
}
