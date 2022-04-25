package com.example.prx301.utils;

import com.example.prx301.dto.DB;
import com.example.prx301.dto.EStudentStatus;
import com.example.prx301.dto.MajorDTO;
import com.example.prx301.dto.StudentDTO;
import com.example.prx301.entitties.Major;
import com.example.prx301.entitties.Majors;
import com.example.prx301.entitties.Student;
import com.example.prx301.entitties.Students;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.w3c.dom.Attr;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;
import java.io.File;
import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

@Component
public class XmlGenerator {
    List<StudentDTO> studentDTOList = null;
    List<MajorDTO> majorDTOList = null;
    @Value("${spring.application.name}")
    private String appName;
    private Document document;
    private File xmlDb;
    private XPathFactory xPathFactory;

    public XmlGenerator(Document document, File xmlDb, XPathFactory xPathFactory) {
        this.document = document;
        this.xmlDb = xmlDb;
        this.xPathFactory = xPathFactory;
    }

    public static DB initXMLData() {
        List<StudentDTO> studentDTOList = null;
        List<MajorDTO> majorDTOList = null;
        if (studentDTOList == null) {
            studentDTOList = new ArrayList<>();
        }
        if (studentDTOList.isEmpty()) {
            for (int i = 1; i <= 1000; i++) {
                StudentDTO dto = new StudentDTO();
                dto.setId("SE" + i);
                if (i % 2 == 0) {
                    MajorDTO newSEMajor = new MajorDTO();
                    newSEMajor.setName("Software Engineer");
                    newSEMajor.setId("SE");
                    dto.setFirstName("Phong");
                    dto.setLastName("Hoang Thanh");
                    dto.setDob("2000-06-02");
                    dto.setMajor(newSEMajor);
                    dto.setEmail("phonght"+i+"@gmail.com");
                    dto.setPhoneNumber("02222222");
                } else {
                    MajorDTO newGDMajor = new MajorDTO();
                    newGDMajor.setName("Graphic Design");
                    newGDMajor.setId("GD");
                    dto.setFirstName("Tin");
                    dto.setLastName("Nguyen Thanh");
                    dto.setDob("2000-04-02");
                    dto.setEmail("tinnt"+i+"@gmail.com");
                    dto.setMajor(newGDMajor);
                    dto.setPhoneNumber("0010101");
                }
                dto.setSex(true);
                dto.setStatus(EStudentStatus.STUDYING.getName());
                studentDTOList.add(dto);
            }
        }
        if (majorDTOList == null) {
            majorDTOList = new ArrayList<>();
        }
        if (majorDTOList.isEmpty()) {
            for (int i = 1; i <= 2; i++) {
                MajorDTO dto = new MajorDTO();
                if (i % 2 == 0) {
                    dto.setId("SE");
                    dto.setName("Software Engineer");
                } else {
                    dto.setId("GD");
                    dto.setName("Graphic Design");
                }
                majorDTOList.add(dto);
            }
        }
        return new DB(studentDTOList, majorDTOList);
    }

    public static String generateXMLDataFromApi(List<StudentDTO> studentDTOList, List<MajorDTO> majorDTOList) throws ParserConfigurationException, TransformerException, XPathExpressionException, JAXBException {
        com.example.prx301.entitties.DB db = new com.example.prx301.entitties.DB();
        String pathForGeneratedXML = "src/main/xml/" + "temp.xml";
        File xmlFile = new File(pathForGeneratedXML);
        if(!xmlFile.exists()){
            try {
                xmlFile.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        List<Student> students = studentDTOList.stream()
                .map(Student::fromDtoToEntity)
                .map(stu -> {
                    Student student = stu.get();
                    student.setId(student.getMajorId()+student.getId());
                    return student;
                }).collect(Collectors.toList());
        List<Major> majors = majorDTOList.stream().map(Major::fromDtoToEntity).map(Optional::get).collect(Collectors.toList());
        Marshaller marshaller = JAXBContext.newInstance(com.example.prx301.entitties.DB.class).createMarshaller();
        db.setStudents(new Students(students));
        db.setMajors(new Majors(majors));
        marshaller.marshal(db, xmlFile);
        return pathForGeneratedXML;
    }


    public static void initDoc2(com.example.prx301.entitties.DB db, List<StudentDTO> dtos, List<MajorDTO> majorDTOList) throws JAXBException {
        String pathForGeneratedXML = "src/main/xml/" + "xmlprx301.xml";
        File xmlFile = new File(pathForGeneratedXML);
        Marshaller marshaller = null;
        if (!xmlFile.exists()) {
            try {
                xmlFile.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        List<Student> students = dtos.stream().map(Student::fromDtoToEntity).map(Optional::get).collect(Collectors.toList());
        List<Major> majors = majorDTOList.stream().map(Major::fromDtoToEntity).map(Optional::get).collect(Collectors.toList());
        marshaller = JAXBContext.newInstance(com.example.prx301.entitties.DB.class).createMarshaller();
        db.setStudents(new Students(students));
        db.setMajors(new Majors(majors));
        marshaller.marshal(db, xmlFile);
    }

    public static void initDoc(Document document, List<StudentDTO> studentDTOList, List<MajorDTO> majorDTOList) throws XPathExpressionException {
        XPath xPath = XPathFactory.newInstance().newXPath();
        Element db = null;
        Node dbNode = (Node) xPath.evaluate("//db", document, XPathConstants.NODE);
        Node studentNode = (Node) xPath.evaluate("//students", document, XPathConstants.NODE);
        Node majorNode = (Node) xPath.evaluate("//majors", document, XPathConstants.NODE);
        if (dbNode == null) {
            db = document.createElement("db");
            db.setAttribute("xmlns", "https://phonght.com");
            db.setAttribute("xmlns:xsi", "http://www.w3.org/2001/XMLSchema-instance");
            db.setAttribute("xsi:schemaLocation", "https://phonght.com db.xsd");
            document.appendChild(db);
        } else {
            db = (Element) dbNode;
            if (studentNode != null) {
                db.removeChild(studentNode);
            }
            if (majorNode != null) {
                db.removeChild(majorNode);
            }
        }
        System.out.println("XML 118 db value : " + db);
        Element majors = document.createElement("majors");
        Element students = document.createElement("students");
        HashMap<String, Integer> majorTotalStudent = new HashMap<>();
        majorDTOList.stream().forEach((majorDTO) -> {
            Element major = document.createElement("major");
            Element majorName = document.createElement("majorName");
            Element majorId = document.createElement("majorId");
            majorName.appendChild(document.createTextNode(majorDTO.getName()));
            majorId.appendChild(document.createTextNode(majorDTO.getId()));
            majorId.setNodeValue(majorDTO.getId());
            major.appendChild(majorId);
            major.appendChild(majorName);
            majors.appendChild(major);
            majorTotalStudent.put(majorDTO.getId(), 1);
        });
        studentDTOList = studentDTOList.stream().sorted(Comparator.comparing(stu -> stu.getMajor().getId())).collect(Collectors.toList());
        studentDTOList.stream().forEach((studentDTO) -> {
            Element student = document.createElement("student");
            Attr majorId = document.createAttribute("majorId");
            Attr id = document.createAttribute("ID");
            majorId.setValue(studentDTO.getMajor().getId());
            id.setValue(studentDTO.getMajor().getId() + majorTotalStudent.get(studentDTO.getMajor().getId()));
            majorTotalStudent.put(studentDTO.getMajor().getId(), majorTotalStudent.get(studentDTO.getMajor().getId()) + 1);
            student.setAttributeNode(id);
            student.setAttributeNode(majorId);
            Element firstName = document.createElement("firstName");
            firstName.appendChild(document.createTextNode(studentDTO.getFirstName()));
            Element lastName = document.createElement("lastName");
            lastName.appendChild(document.createTextNode(studentDTO.getLastName()));
            Element email = document.createElement("email");
            email.appendChild(document.createTextNode(studentDTO.getEmail()));
            Element dob = document.createElement("dob");
            dob.appendChild(document.createTextNode(studentDTO.getDob()));
            Element sex = document.createElement("sex");
            sex.appendChild(document.createTextNode(studentDTO.isSex() ? "true" : "false"));
            Element phone = document.createElement("phone");
            phone.appendChild(document.createTextNode(studentDTO.getPhoneNumber()));
            Element status = document.createElement("status");
            status.appendChild(document.createTextNode(studentDTO.getStatus()));
            student.appendChild(firstName);
            student.appendChild(lastName);
            student.appendChild(email);
            student.appendChild(dob);
            student.appendChild(sex);
            student.appendChild(phone);
            student.appendChild(status);
            students.appendChild(student);
        });
        db.appendChild(majors);
        db.appendChild(students);
    }

    public String generateDBXMLData() throws TransformerException, XPathExpressionException, JAXBException {
        DB db = initXMLData();
        initDoc2(new com.example.prx301.entitties.DB(), db.getStudents(), db.getMajors());
        //XMLHelpers.saveXMLContent(document, xmlDb);
        return xmlDb.getAbsolutePath();
    }
}
