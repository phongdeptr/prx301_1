package com.example.prx301.controllers;

import com.example.prx301.dto.DB;
import com.example.prx301.dto.StudentDTO;
import com.example.prx301.dto.StudentError;
import com.example.prx301.services.StudentService;
import com.example.prx301.utils.DomHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.w3c.dom.Document;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.xpath.XPathExpressionException;
import java.io.File;
import java.io.IOException;
import java.util.List;

@Controller
@SessionAttributes({"students","student","studentError"})
public class StudentController {
    @Autowired
    private StudentService studentService;
    @ModelAttribute("lastKeyWord")
    private String lastKeyword(){
        return "";
    }
    @ModelAttribute("student")
    public StudentDTO student(){
        return new StudentDTO();
    }
    @ModelAttribute("studentError")
    public StudentError error(){
        return null;
    }
    @ModelAttribute("students")
    public List<StudentDTO> studentList() {
        return studentService.getStudent().getStudents();
    }

    public StudentController(StudentService studentService) {
        this.studentService = studentService;
    }

    @PostMapping
    public String createStudent(StudentDTO student, ModelMap modelMap){

        StudentError studentError = studentService.addStudent(student);
        modelMap.addAttribute("studentError", studentError);
        modelMap.addAttribute("newStudent", student);
        return "createStudent";
    }

    @GetMapping("students/{page}")
    public String redirect(@PathVariable("page") String location){
        switch (location){
            case "createStudent":{
                return "createStudent";
            } case "updateStudent":{
                return "updateStudent";
            }
        }
        return "redirect:students";
    }

    @GetMapping("/students")
    public String viewStudent(){
        return "studentManagement";
    }

    @GetMapping("/find/students")
    public String findStudent(@RequestParam("keyword") String keyword, @ModelAttribute("lastKeyWord") String lastKeyword, @ModelAttribute("students") List<StudentDTO> studentResult){
        lastKeyword = keyword;
        List<StudentDTO> studentDTOS = studentService.searchStudent(keyword);
        studentDTOS.stream().map(student -> student.getFirstName()).forEach(System.out::println);
        return "studentManagement";
    }
}
