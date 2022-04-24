package com.example.prx301.repositories.implementations;

import com.example.prx301.dto.*;
import com.example.prx301.entitties.DB;
import com.example.prx301.entitties.Student;
import com.example.prx301.entitties.Students;
import com.example.prx301.exceptions.StudentException;
import com.example.prx301.repositories.StudentXMLRepository;
import com.example.prx301.utils.DomHelper;
import com.example.prx301.utils.XMLHelpers;
import com.example.prx301.validators.StudentValidator;
import org.springframework.stereotype.Repository;
import org.w3c.dom.*;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.transform.TransformerException;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpressionException;
import java.io.File;
import java.util.*;
import java.util.stream.Collectors;

@Repository
public class StudentRepository implements StudentXMLRepository<Student, StudentValidationResult> {
    private final Document document;
    private final XPath xPath;
    private final StudentValidator validator;
    private final File xmlDb;
    Marshaller marshaller;
    private com.example.prx301.entitties.DB db;

    public StudentRepository(Document document, XPath xPath, StudentValidator validator, File xmlDb, Marshaller marshaller, com.example.prx301.entitties.DB db) {
        this.document = document;
        this.xPath = xPath;
        this.validator = validator;
        this.xmlDb = xmlDb;
        this.marshaller = marshaller;
        this.db = db;
    }

    @Override
    public Student findStudentById(String studentId) throws XPathExpressionException,StudentException{
        Optional<Student> searchResults = db.getStudents().getStudents().stream().filter(student -> student.getId().equals(studentId)).findFirst();
        return searchResults.orElse(null);
    }

    @Override
    public Student addNewStudent(Student student) {
        Optional<Student> newStu = Optional.of(student);
        newStu.ifPresent((stu) ->{
            long count = 0;
            db.getStudents().getStudents();
            if(db.getStudents() == null || db.getStudents().getStudents() == null){
                db.setStudents(new Students(new ArrayList<>()));
            }else{
                count = db.getStudents().getStudents().stream().filter(student1 -> student1.getMajorId().equalsIgnoreCase(student.getMajorId())).count();
            }
            stu.setId(stu.getMajorId()+count);
            db.getStudents().getStudents().add(stu);
            try {
                marshaller.marshal(db,xmlDb);
            } catch (JAXBException e) {
                e.printStackTrace();
            }
        });
        return student;
    }

    @Override
    public Student updateStudentInfo(Student student) throws TransformerException, JAXBException {
        Optional<Student> first = db.getStudents().getStudents().stream().filter(student1 -> student1.getId().equals(student.getId())).findFirst();
        first.ifPresent(stu -> {
            stu.setId(student.getId());
            stu.setFirstName(student.getFirstName());
            stu.setLastName(student.getLastName());
            stu.setDob(student.getDob());
            stu.setMajorId(student.getMajorId());
            stu.setEmail(student.getEmail());
            stu.setSex(student.isSex());
            stu.setStatus(student.getStatus());
            stu.setPhoneNumber(student.getPhoneNumber());
        });
        marshaller.marshal(db,xmlDb);
        return first.orElse(null);
    }

    @Override
    public Student changeStudentMajor(String studentId, MajorDTO newMajor) throws TransformerException {
        NodeList students = document.getElementsByTagName("student");
        boolean foundStudent = false;
        for (int i = 0; i < students.getLength(); i++) {
            Element currStudent = (Element) students.item(i);
            NamedNodeMap attributes = currStudent.getAttributes();
            Node id = attributes.getNamedItem("ID");
            if(id.getNodeValue().trim().equals(studentId.trim())){
                attributes.getNamedItem("majorId").setNodeValue(newMajor.getId());
                foundStudent = true;
                i = students.getLength()+2;
            }
        }
        XMLHelpers.saveXMLContent(document,xmlDb);
        return null;
    }

    @Override
    public boolean removeStudent(String studentId){
        Optional<Student> first = db.getStudents().getStudents().stream()
                .filter(student -> student.getId().equals(studentId))
                .findFirst();
        first.ifPresent(
                (stu) ->
                db.getStudents().getStudents().remove(stu)
        );
        return first.isPresent();
    }

    @Override
    public List<Student> findStudentByName(String keyword) throws JAXBException {
        JAXBContext context = JAXBContext.newInstance(com.example.prx301.entitties.DB.class);
        com.example.prx301.entitties.DB db = (com.example.prx301.entitties.DB) context.createUnmarshaller().unmarshal(xmlDb);
        List<Student> studentList = db.getStudents().getStudents();
        return studentList.stream().filter(student -> student.getFirstName().toLowerCase().contains(keyword.toLowerCase()) ||
                student.getLastName().toLowerCase().contains(keyword.toLowerCase())).collect(Collectors.toList());
    }

    @Override
    public List<Student> getAllStudent() {
            return db.getStudents().getStudents();
    }
}
