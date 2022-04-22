package com.example.prx301.repositories;

import com.example.prx301.dto.DB;
import com.example.prx301.dto.MajorDTO;
import com.example.prx301.dto.StudentDTO;
import com.example.prx301.dto.StudentValidationResult;
import com.example.prx301.exceptions.StudentException;
import com.example.prx301.utils.DomHelper;
import org.w3c.dom.Document;
import org.xml.sax.SAXException;

import javax.xml.bind.JAXBException;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.xpath.XPathExpressionException;
import java.io.IOException;
import java.util.List;

public interface StudentXMLRepository<T, E extends StudentValidationResult> {
    T findStudentById(String studentId) throws XPathExpressionException, StudentException;
    T addNewStudent(T student);
    T updateStudentInfo(T student) throws XPathExpressionException, TransformerException, JAXBException;
    T changeStudentMajor(String studentId, MajorDTO newMajor) throws TransformerException;
    boolean removeStudent(String studentId) throws TransformerException;
    List<T> findStudentByName(String keyword) throws JAXBException;
    List<T> getAllStudent();
}
