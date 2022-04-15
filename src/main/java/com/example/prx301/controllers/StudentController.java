package com.example.prx301.controllers;

import com.example.prx301.dto.StudentDTO;
import com.example.prx301.dto.StudentError;
import com.example.prx301.services.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.SessionAttributes;

@Controller("students")
@SessionAttributes({"student","studentError"})
public class StudentController {
    @ModelAttribute("student")
    public StudentDTO student(){
        return new StudentDTO();
    }
    @ModelAttribute("studentError")
    public StudentError error(){
        return null;
    }

    private StudentService studentService;

    public StudentController(StudentService studentService) {
        this.studentService = studentService;
    }

    @PostMapping
    public String CreateStudent(StudentDTO student, ModelMap modelMap){

        StudentError studentError = studentService.addStudent(student);
        modelMap.addAttribute("studentError", studentError);
        return "studentManager";
    }
}
