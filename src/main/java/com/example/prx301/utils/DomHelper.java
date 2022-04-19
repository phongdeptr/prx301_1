package com.example.prx301.utils;

import com.example.prx301.dto.DB;
import com.example.prx301.dto.EStudentStatus;
import com.example.prx301.dto.MajorDTO;
import com.example.prx301.dto.StudentDTO;
import org.springframework.stereotype.Component;
import org.w3c.dom.*;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Component
public class DomHelper {
    private Document document;
    private TransformerFactory factory;

    public Document getDoc(File xmlFile) throws ParserConfigurationException, IOException, SAXException {
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = factory.newDocumentBuilder();
        Document document = builder.parse(xmlFile);
        return document;
    }

    public void saveXMLFile(File xmlFile, Document document) throws TransformerException, IOException {
        factory = TransformerFactory.newInstance();
        Transformer transformer = factory.newTransformer();
        DOMSource source = new DOMSource(document);
        StreamResult result = new StreamResult(new FileWriter(xmlFile));
        transformer.transform(source,result);
    }

    public static DB loadXML(Document srcDoc) throws XPathExpressionException {
        DB db = null;
        List<StudentDTO> studentList = null;
        List<MajorDTO> majorList = null;
        String id="",majorId = "",firstName = "", lastName="", email="", dob="", phone="",sex="";
        String status = null;
        String xpathStudent = "//student";
        String xpathMajor = "//major";
        XPathFactory factory = XPathFactory.newInstance();
        XPath xPath = factory.newXPath();
        NodeList students = (NodeList)xPath.compile(xpathStudent).evaluate(srcDoc, XPathConstants.NODESET);
        NodeList majors = (NodeList) xPath.compile(xpathMajor).evaluate(srcDoc, XPathConstants.NODESET);
        for (int i = 0; i < students.getLength(); i++) {
            Node item = students.item(i);
            NamedNodeMap attributes = item.getAttributes();
            NodeList childNodes = item.getChildNodes();
            id = attributes.getNamedItem("ID").getNodeValue();
            System.out.println(id);
            for (int j = 0; j < childNodes.getLength(); j++) {
                Node subElement = childNodes.item(j);
                String elementName = subElement.getNodeName();
                switch (elementName){
                    case "firstName":{
                        firstName  = subElement.getTextContent();
                        break;
                    } case "lastName":{
                        lastName = subElement.getTextContent();
                        break;
                    } case "email":{
                        email = subElement.getTextContent();
                        break;
                    } case "dob":{
                        dob = subElement.getTextContent();
                        break;
                    } case "sex":{
                        sex = subElement.getTextContent();
                        break;
                    } case "phone":{
                        phone = subElement.getTextContent();
                        break;
                    } case "status":{
                        status = subElement.getTextContent();
                        break;
                    }
                }
            }//end for student
            MajorDTO majorDTO = new MajorDTO();
            majorDTO.setId(majorId);
            StudentDTO dto = new StudentDTO(id, firstName, lastName, email, dob, sex.equalsIgnoreCase("male") ? true : false, phone, status, majorDTO);
            if(studentList == null){
                studentList = new ArrayList<>();
            }
            studentList.add(dto);
        }//end for student ele list
        for (int i = 0; i < majors.getLength(); i++) {
            Element major= (Element)majors.item(i);
            MajorDTO dto = XMLHelpers.createMajorDtoFromElement(major);
            if(majorList == null){
                majorList = new ArrayList<>();
            }
            majorList.add(dto);
        }
        if(db == null){
            db = new DB();
        }
        db.setStudents(studentList);
        db.setMajors(majorList);
        return db;
    }

    public static String getNodeValue(String nodeName, Node target){
       return target.getTextContent();
    }

    public static String getNodeAttributes(String attrName, Node target){
        return target.getAttributes().getNamedItem(attrName).getNodeValue();
    }
}
