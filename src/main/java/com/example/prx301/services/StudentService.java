package com.example.prx301.services;

import com.example.prx301.dto.StudentDTO;
import com.example.prx301.dto.StudentError;
import com.example.prx301.utils.StudentDOM;
import org.springframework.stereotype.Service;
import org.xml.sax.SAXException;

import javax.servlet.ServletContext;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;
import java.io.IOException;
import java.util.List;

@Service
public class StudentService {
    public StudentError addStudent(StudentDTO dto){

        StudentError studentError = null;
        StudentDOM dom = new StudentDOM();
        try {
            dom.Add(dto);
        } catch (TransformerException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (SAXException e) {
            e.printStackTrace();
        } catch (ParserConfigurationException e) {
            e.printStackTrace();
        }

        return studentError;
    }

    public List<StudentDTO> getALlStudent(){

        return null;
    }
}
