package com.example.prx301.repositories.implementations;

import com.example.prx301.dto.*;
import com.example.prx301.repositories.StudentXMLRepository;
import com.example.prx301.utils.DomHelper;
import com.example.prx301.utils.XMLHelpers;
import com.example.prx301.validators.StudentValidator;
import org.springframework.stereotype.Repository;
import org.w3c.dom.*;

import javax.xml.transform.TransformerException;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpressionException;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Repository
public class StudentRepository implements StudentXMLRepository<StudentDTO, StudentValidationResult> {

    private final Document document;
    private final XPath xPath;
    private final StudentValidator validator;
    private final File xmlDb;

    public StudentRepository(Document document, XPath xPath, StudentValidator validator, File xmlDb) {
        this.document = document;
        this.xPath = xPath;
        this.validator = validator;
        this.xmlDb = xmlDb;
    }

    @Override
    public StudentDTO findStudentById(String studentId) throws XPathExpressionException {
        StudentDTO searchResults = null;
        Node student = (Node) xPath.evaluate("//student[@id='" + studentId + "']", document, XPathConstants.NODE);
        NodeList childNodes = student.getChildNodes();
        for (int i = 0; i < childNodes.getLength(); i++) {
            String stuID ="",firstName = "",lastName = "", email = "", dob = "", phone = "", sex ="", status = "", majorId;
            Node tmp = childNodes.item(i);
            String nodeName = tmp.getNodeName();
            stuID = DomHelper.getNodeAttributes("ID", tmp);
            majorId = DomHelper.getNodeAttributes("majorId", tmp);
            String nodeValue = DomHelper.getNodeValue(nodeName, tmp);
            switch (nodeName){
                case "firstName":{
                    firstName  = nodeValue;
                    break;
                } case "lastName":{
                    lastName = nodeValue;
                    break;
                } case "email":{
                    email = nodeValue;
                    break;
                } case "dob":{
                    dob = nodeValue;
                    break;
                } case "sex":{
                    sex = nodeValue;
                    break;
                } case "phone":{
                    phone = nodeValue;
                    break;
                } case "status":{
                    break;
                }
            }
            MajorDTO majorDTO = new MajorDTO();
            majorDTO.setId(majorId);
            searchResults = new StudentDTO(stuID, firstName, lastName, email, dob, sex.equalsIgnoreCase("true"), phone, EStudentStatus.valueOf(status).getName(), majorDTO);

        }
        return searchResults;
    }

    @Override
    public StudentDTO addNewStudent(StudentDTO student) {
        StudentDTO dto = student;
        try {
            Node students = (Node)xPath.evaluate("//students", document, XPathConstants.NODE);
            DB db = DomHelper.loadXML(document);
            if(students == null){
                students = document.createElement("students");
                Element studentElement = XMLHelpers.createStudentElement(student, document);
                students.appendChild(studentElement);
            }else{
                Optional<Integer> maxID = db.getStudents().stream()
                        .filter(studentDTO -> studentDTO.getId().contains(dto.getMajor().getId()))
                        .map(studentDTO -> studentDTO.getId().substring(2)).max(String::compareTo).map(s -> Integer.parseInt(s) + 1);
                dto.setId(dto.getMajor().getId() + maxID.orElse(1));
                students.appendChild(XMLHelpers.createStudentElement(dto,document));
            }
            XMLHelpers.saveXMLContent(document, xmlDb);
        } catch (XPathExpressionException | TransformerException e) {
            e.printStackTrace();
        }
        return student;
    }

    @Override
    public StudentDTO updateStudentInfo(StudentDTO student) throws XPathExpressionException {
        NodeList students = document.getElementsByTagName("student");
        boolean foundStudent = false;
        for (int i = 0; i < students.getLength(); i++) {
            Element currStudent = (Element) students.item(i);
            NamedNodeMap attributes = currStudent.getAttributes();
            Node id = attributes.getNamedItem("ID");
            if(id.getNodeValue().trim().equals(student.getId().trim())){
                Element studentElement = XMLHelpers.createStudentElement(student, document);
                studentElement.setAttribute("ID", currStudent.getAttribute("ID"));
                Node parentNode = currStudent.getParentNode();
                parentNode.replaceChild(currStudent, studentElement);
                foundStudent = true;
                i = students.getLength() + 2;
            }
        }
        return foundStudent? student:null;
    }

    @Override
    public StudentDTO changeStudentMajor(String studentId, MajorDTO newMajor) throws TransformerException {
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
    public boolean removeStudent(String studentId) throws TransformerException {
        NodeList students = document.getElementsByTagName("student");
        boolean foundStudent = false;
        for (int i = 0; i < students.getLength(); i++) {
            Element currStudent = (Element) students.item(i);
            NamedNodeMap attributes = currStudent.getAttributes();
            Node id = attributes.getNamedItem("ID");
            if(id.getTextContent().trim().equals(studentId)){
                foundStudent = true;
                currStudent.getParentNode().removeChild(currStudent);
            }
            document.removeChild(currStudent);
        }
        XMLHelpers.saveXMLContent(document,xmlDb);
        return foundStudent;
    }

    @Override
    public List<StudentDTO> findStudentByName(String keyword) {
        List<StudentDTO> searchResults = null;
        NodeList students = null;
        try {
            students = (NodeList) xPath.evaluate("//student[contains(firstName ,'"+keyword+"') or contains(lastName,'"+keyword+"')]", document, XPathConstants.NODESET);
        } catch (XPathExpressionException e) {
            e.printStackTrace();
        }
        if(students != null){
            String studentId, majorId;
            String firstName="",lastName="",email="", dob="", status="", phone="";
            boolean sex = false;
            for (int i = 0; i < students.getLength(); i++) {
                Node student = students.item(i);
                NodeList childNodes = student.getChildNodes();
                studentId = DomHelper.getNodeAttributes("ID", student);
                majorId = DomHelper.getNodeAttributes("majorId",student);
                for (int j = 0; j < childNodes.getLength(); j++) {
                    Node item = childNodes.item(j);
                    String elementName = item.getNodeName();
                    switch (elementName){
                        case "firstName":{
                            firstName  = DomHelper.getNodeValue(elementName,item);
                            break;
                        } case "lastName":{
                            lastName = DomHelper.getNodeValue(elementName,item);
                            break;
                        } case "email":{
                            email = DomHelper.getNodeValue(elementName,item);
                            break;
                        } case "dob":{
                            dob = DomHelper.getNodeValue(elementName,item);
                            break;
                        } case "sex":{
                            sex = DomHelper.getNodeValue(elementName, item).equalsIgnoreCase("true");
                            break;
                        } case "phone":{
                            phone = DomHelper.getNodeValue(elementName,item);
                            break;
                        } case "status":{
                            status = DomHelper.getNodeValue(elementName,item);
                            break;
                        }
                    }// end switch;
                }//end for student sub element list;
                MajorDTO majorDTO = new MajorDTO();
                majorDTO.setId(majorId);
                try {
                    NodeList majors = (NodeList)xPath.evaluate("//major", document, XPathConstants.NODESET);
                    for (int j = 0; j < majors.getLength(); j++) {
                        Node major = majors.item(j);
                        if(major.getNodeName().equals("major")){
                            NodeList childs = major.getChildNodes();
                            for (int k = 0; k < childs.getLength(); k++) {
                                String name = childs.item(k).getNodeName();
                                if(name.equals("majorId") && childs.item(k).getTextContent().contains(majorId)){
                                    majorDTO.setId(childs.item(k).getTextContent());
                                }
                                if(name.equals("majorName")){
                                    majorDTO.setName(childs.item(k).getTextContent());
                                }
                            }
                        }
                    }
                } catch (XPathExpressionException e) {
                    e.printStackTrace();
                }//end creation of major on student;
                StudentDTO dto = new StudentDTO(studentId, firstName, lastName, email, dob, sex == true, phone, status, majorDTO);
                if(searchResults == null){
                    searchResults = new ArrayList<>();
                }
                searchResults.add(dto);
            }// end for student elements list;
        }//end if;
        return searchResults;
    }

    @Override
    public List<StudentDTO> getAllStudent() {
            DB db = null;
            try {
                db = DomHelper.loadXML(document);
                System.out.println(db.getStudents().size());
            } catch (XPathExpressionException e) {
                e.printStackTrace();
            }
            return db.getStudents();
    }
}
