package com.example.prx301.services;

import com.example.prx301.dto.DB;
import com.example.prx301.dto.StudentDTO;
import com.example.prx301.dto.StudentError;
import com.example.prx301.repositories.StudentXMLRepository;
import com.example.prx301.utils.DomHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.w3c.dom.Document;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.xpath.XPathExpressionException;
import java.io.File;
import java.io.IOException;
import java.util.List;

@Service
public class StudentService {
    @Autowired
    private DomHelper helper;
    @Autowired
    private StudentXMLRepository<StudentDTO, StudentError> repository;


    public StudentService(DomHelper helper) {
        this.helper = helper;
    }

    public DB getStudent(){
        DB db = null;
        try {
            Document doc = helper.getDoc(new File("src/main/xml/xmlprx301_ass.xml"));
            db = DomHelper.loadXML(doc);
            System.out.println(db.getStudents().size());
        } catch (ParserConfigurationException parserConfigurationException) {
            parserConfigurationException.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (SAXException e) {
            e.printStackTrace();
        } catch (XPathExpressionException e) {
            e.printStackTrace();
        }
        return db;
    }
    public StudentError addStudent(StudentDTO dto){
        StudentError studentError = null;
        try {
            Document doc = helper.getDoc(new File("src/main/xml"));
            DB db = DomHelper.loadXML(doc);
            System.out.println(db.getStudents().size());
        } catch (ParserConfigurationException parserConfigurationException) {
            parserConfigurationException.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (SAXException e) {
            e.printStackTrace();
        } catch (XPathExpressionException e) {
            e.printStackTrace();
        }
        return studentError;
    }

    public List<StudentDTO> searchStudent(String keyword){
        return repository.findStudentByName(keyword);
    }
}
