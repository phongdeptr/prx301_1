package com.example.prx301.repositories.implementations;

import com.example.prx301.dto.EStudentStatus;
import com.example.prx301.dto.MajorDTO;
import com.example.prx301.dto.StudentDTO;
import com.example.prx301.dto.StudentError;
import com.example.prx301.repositories.StudentXMLRepository;
import com.example.prx301.utils.DomHelper;
import org.springframework.stereotype.Repository;
import org.w3c.dom.Document;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpressionException;
import java.util.ArrayList;
import java.util.List;

@Repository
public class StudentRepository implements StudentXMLRepository<StudentDTO, StudentError> {

    private DomHelper helper;
    private Document document;
    private XPath xPath;

    public StudentRepository(DomHelper helper, Document document, XPath xPath) {
        this.helper = helper;
        this.document = document;
        this.xPath = xPath;
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
            searchResults = new StudentDTO(stuID, firstName, lastName, email, dob, sex.equalsIgnoreCase("true") ? true : false, phone, EStudentStatus.valueOf(status), majorId);

        }
        return searchResults;
    }

    @Override
    public StudentError addNewStudent(StudentDTO student) {
        return null;
    }

    @Override
    public StudentError updateStudentInfo(StudentDTO student) {
        return null;
    }

    @Override
    public StudentError changeStudentMajor(String studentId, MajorDTO newMajor) {
        return null;
    }

    @Override
    public StudentError removeStudent(String studentId) {
        return null;
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
        if(students != null ){
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
                            sex = DomHelper.getNodeValue(elementName,item).equalsIgnoreCase("true") ? true: false;
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
                StudentDTO dto = new StudentDTO(studentId, firstName, lastName, email, dob, sex == true ? true : false, phone, EStudentStatus.valueOfStr(status), majorId);
                if(searchResults == null){
                    searchResults = new ArrayList<>();
                }
                searchResults.add(dto);
            }// end for student elements list;
        }//end if;
        return searchResults;
    }
}
