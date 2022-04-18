package com.example.prx301.validators;

import com.example.prx301.dto.StudentDTO;
import com.example.prx301.dto.StudentError;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.w3c.dom.Document;

@Component
public class StudentValidator {
    Document document;

    @Autowired
    public StudentValidator(Document document) {
        this.document = document;
    }

    public StudentError checkStudent(StudentDTO dto) {
        document.getElementsByTagName("student");
        return null;
    }
}
