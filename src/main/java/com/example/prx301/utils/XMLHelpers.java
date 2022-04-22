package com.example.prx301.utils;

import com.example.prx301.dto.EStudentStatus;
import com.example.prx301.dto.MajorDTO;
import com.example.prx301.dto.StudentDTO;
import com.example.prx301.exceptions.StudentException;
import org.springframework.stereotype.Component;
import org.w3c.dom.*;
import org.xml.sax.SAXException;

import javax.xml.XMLConstants;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.*;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;
import javax.xml.validation.Validator;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Component
public class XMLHelpers {
    public static List<MajorDTO> majorDTOList;
    public static List<StudentDTO> studentDTOList;
    Document document;
    File xmlDd;

    public XMLHelpers(Document document, File xmlDd) {
        this.document = document;
        this.xmlDd = xmlDd;
    }

    public static String validateXml(File schemaFile, File xmlFile) {
        String result = "xml file is valid";
        System.out.println("path of xml schema upload: " + schemaFile.getAbsolutePath());
        System.out.println("path of xml upload: " + xmlFile.getAbsolutePath());
        try {
            DocumentBuilderFactory factoryDoc = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factoryDoc.newDocumentBuilder();
            builder.parse(xmlFile.getAbsolutePath());
            SchemaFactory factory = SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);
            Schema schema = factory.newSchema(schemaFile);
            Validator validator = schema.newValidator();
            validator.validate(new StreamSource(xmlFile));
        } catch (org.xml.sax.SAXException e1) {
            System.out.println("SAX Exception: " + e1.getMessage());
            return "File " + xmlFile.getName() + " is not valid with schema in file " + schemaFile.getName();
        } catch (IOException e) {
            System.out.println("Exception: " + e.getMessage());
            return "";
        } catch (ParserConfigurationException parserConfigurationException) {
            return "File " + xmlFile.getName() + " is not wellformed";
        }
        return result;
    }

    public static void saveXMLContent(Document d, File xmlDb) throws TransformerException {
        try {
            TransformerFactory tff = TransformerFactory.newInstance();
            Transformer tf = tff.newTransformer();
            tf.setOutputProperty(OutputKeys.INDENT, "no");
            DOMSource source = new DOMSource(d);
            StreamResult rs = new StreamResult(xmlDb);
            tf.transform(source, rs);
        } catch (TransformerConfigurationException ex) {
            System.out.println("save xml transform exception " + ex.getMessage());
        }
    }

    public static Element createStudentElement(StudentDTO dto, Document document) {
        Element firstName = document.createElement("firstName");
        Element lastName = document.createElement("lastName");
        Element email = document.createElement("email");
        Element dob = document.createElement("dob");
        Element sex = document.createElement("sex");
        Element phone = document.createElement("phone");
        Element status = document.createElement("status");
        firstName.appendChild(document.createTextNode(dto.getFirstName()));
        lastName.appendChild(document.createTextNode(dto.getLastName()));
        email.appendChild(document.createTextNode(dto.getEmail()));
        dob.appendChild(document.createTextNode(dto.getDob()));
        sex.appendChild(document.createTextNode(dto.isSex() ? "true" : "false"));
        phone.appendChild(document.createTextNode(dto.getPhoneNumber()));
        status.appendChild(document.createTextNode(dto.getStatus()));
        Element student = document.createElement("student");
        student.appendChild(firstName);
        student.appendChild(lastName);
        student.appendChild(email);
        student.appendChild(dob);
        student.appendChild(sex);
        student.appendChild(phone);
        student.appendChild(status);
        student.setAttribute("majorId", dto.getMajor().getId());
        student.setAttribute("ID", dto.getId());
        return student;
    }

    public static Element createMajorElement(MajorDTO dto, Document document) {
        Element major = document.createElement("major");
        Element majorId = document.createElement("majorId");
        Element majorName = document.createElement("majorName");
        majorId.setTextContent(dto.getId());
        majorName.setTextContent(dto.getName());
        major.appendChild(majorId);
        major.appendChild(majorName);
        return major;
    }

    public static MajorDTO createMajorDtoFromElement(Element major) {
        MajorDTO dto = new MajorDTO();
        String currId = "", currName = "";
        NodeList majorChildNodes = major.getChildNodes();
        for (int j = 0; j < majorChildNodes.getLength(); j++) {
            Node curr = majorChildNodes.item(j);
            String nodeName = majorChildNodes.item(j).getNodeName();
            switch (nodeName) {
                case "majorId": {
                    currId = DomHelper.getNodeValue(curr);
                    break;
                }
                case "majorName": {
                    currName = DomHelper.getNodeValue(curr);
                    break;
                }
            }
        }
        dto.setName(currName);
        dto.setId(currId);
        return dto;
    }


    public String importXMLToDB(File tempFile) throws ParserConfigurationException, IOException, SAXException, XPathExpressionException, StudentException, TransformerException {
        String message = "";
        Document tmp = DocumentBuilderFactory.newInstance().newDocumentBuilder().parse(tempFile);
        NodeList tmpStudents = tmp.getElementsByTagName("student");
        NodeList tmpStudentsEmail = tmp.getElementsByTagName("email");
        Node dbStudents = document.getElementsByTagName("students").item(0);
        Node dbMajors = document.getElementsByTagName("majors").item(0);
        NodeList tmpMajors = tmp.getElementsByTagName("majorId");
        XPath xPath = XPathFactory.newInstance().newXPath();
        DOMSource domSource = new DOMSource(document);
        NodeList lastNameElement = (NodeList) xPath.evaluate("//student/lastName", tmp, XPathConstants.NODESET);
        NodeList firstNameElement = (NodeList) xPath.evaluate("//student/firstName", tmp, XPathConstants.NODESET);
        NodeList emailElement = (NodeList) xPath.evaluate("//student/email", tmp, XPathConstants.NODESET);
        NodeList dobElement = (NodeList) xPath.evaluate("//student/dob", tmp, XPathConstants.NODESET);
        NodeList sexElement = (NodeList) xPath.evaluate("//student/sex", tmp, XPathConstants.NODESET);
        NodeList phoneElement = (NodeList) xPath.evaluate("//student/phone", tmp, XPathConstants.NODESET);
        NodeList statusElement = (NodeList) xPath.evaluate("//student/status", tmp, XPathConstants.NODESET);
        NodeList majorIds = (NodeList) xPath.evaluate("//major/majorId", tmp, XPathConstants.NODESET);
        NodeList majorNames = (NodeList) xPath.evaluate("//major/majorId", tmp, XPathConstants.NODESET);
        boolean canImport = true;
        for (int i = 0; i < tmpStudents.getLength(); i++) {
            Node student = tmpStudents.item(i);
            NamedNodeMap namedNodeMap = student.getAttributes();
            String targetId = namedNodeMap.getNamedItem("ID").getNodeValue();
            String majorId = namedNodeMap.getNamedItem("majorId").getNodeValue();
            Boolean check = (Boolean) xPath.evaluate("//student/email='" + tmpStudentsEmail.item(i).getTextContent() + "'"
                    , document, XPathConstants.BOOLEAN);
            if (check.booleanValue()) {
                System.out.println("duplicate email" + tmpStudentsEmail.item(i).getTextContent());
                canImport = false;
                i = tmpStudents.getLength() + 100;
            } else {
                String lastname = lastNameElement.item(i).getTextContent();
                String firstName = firstNameElement.item(i).getTextContent();
                String email = emailElement.item(i).getTextContent();
                String dob = dobElement.item(i).getTextContent();
                Boolean sex = Boolean.valueOf(sexElement.item(i).getTextContent());
                String phone = phoneElement.item(i).getTextContent();
                String status = statusElement.item(i).getTextContent();
                StudentDTO dto = new StudentDTO(targetId, firstName, lastname, email, dob, sex, phone, status, new MajorDTO());
                dto.getMajor().setId(majorId);
                System.out.println("index i: " + i + " firstName: " + dto.getFirstName() + " on " + firstNameElement.item(i).getNodeName() + " total " + firstNameElement.getLength());
                System.out.println("value: " + firstName);
                System.out.println(tempFile.getAbsolutePath());
                Element studentElement = createStudentElement(dto, document);
                document.getElementsByTagName("students").item(0).appendChild(studentElement);
            }
        }

        for (int i = 0; i < tmpMajors.getLength(); i++) {
            String currId = tmpMajors.item(i).getTextContent().trim();
            Boolean isDuplicateMajorId = (Boolean) xPath.evaluate("//majorId=" + currId, document, XPathConstants.BOOLEAN);
            if (isDuplicateMajorId.booleanValue()) {
                canImport = false;
                i = tmpMajors.getLength() + 100;
            }
            String majorId = majorIds.item(i).getTextContent();
            String majorName = majorNames.item(i).getTextContent();
            MajorDTO dto = new MajorDTO();
            dto.setId(majorId);
            dto.setName(majorName);
            Element majorElement = createMajorElement(dto, document);
            document.getElementsByTagName("majors").item(0).appendChild(majorElement);
        }
        if (canImport) {
            saveXMLContent(document, xmlDd);
            tempFile.delete();
            message = "SUCCESS";
        } else {
            message = "your file contains duplicate student or majors";
            boolean delete = tempFile.delete();
            System.out.println("delete file " + delete);
        }
        return message;
    }
}
