package com.example.prx301.utils;

import com.example.prx301.dto.EStudentStatus;
import com.example.prx301.dto.MajorDTO;
import com.example.prx301.dto.StudentDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.w3c.dom.*;

import javax.servlet.ServletContext;
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
import javax.xml.xpath.XPathConstants;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Component
public class XMLHelpers {
    @Autowired
    ServletContext context;
    @Value("${spring.application.name}")
    private String appName;
    public static List<MajorDTO> majorDTOList;
    public static  List<StudentDTO> studentDTOList;
    public String generateXMLData(){
        initXMLData();
        String pathForGeneratedXML = "src/main/xml/" + appName + ".xml";
        try{
            DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder documentBuilder = documentBuilderFactory.newDocumentBuilder();
            Document document = documentBuilder.newDocument();
            Element db = document.createElement("db");
            db.setAttribute("xmlns","https://phonght.com/xsd");
            db.setAttribute("xmlns:xsi","http://www.w3.org/2001/XMLSchema-instance");
            db.setAttribute("xsi:schemaLocation","https://phonght.com/xsd");
            Element majors = document.createElement("majors");
            Element students = document.createElement("students");
            document.appendChild(db);
            HashMap<String,Integer> majorTotalStudent = new HashMap<>();
            majorDTOList.stream().forEach((majorDTO) ->{
                Element major = document.createElement("major");
                Element majorName = document.createElement("majorName");
                Element majorId = document.createElement("majorId");
                majorName.appendChild(document.createTextNode(majorDTO.getName()));
                majorId.appendChild(document.createTextNode(majorDTO.getId()));
                majorId.setNodeValue(majorDTO.getId());
                major.appendChild(majorId);
                major.appendChild(majorName);
                majors.appendChild(major);
                majorTotalStudent.put(majorDTO.getId(),1);
            });
            studentDTOList.stream().forEach((studentDTO) ->{
                Element student = document.createElement("student");
                Attr majorId = document.createAttribute("majorId");
                Attr id = document.createAttribute("ID");
                majorId.setValue(studentDTO.getMajorId());
                id.setValue(studentDTO.getMajorId() + majorTotalStudent.get(studentDTO.getMajorId()));
                majorTotalStudent.put(studentDTO.getMajorId(),majorTotalStudent.get(studentDTO.getMajorId())+1);
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
                sex.appendChild(document.createTextNode(studentDTO.isSex()?"true":"false"));
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
            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();
            transformer.setOutputProperty(OutputKeys.INDENT, "yes");
            DOMSource domSource = new DOMSource(document);
            StreamResult streamResult = new StreamResult(new File(pathForGeneratedXML));
            transformer.transform(domSource, streamResult);
        }
        catch (ParserConfigurationException parserConfigurationException){
            parserConfigurationException.printStackTrace();
        }catch (TransformerException transformerException){
            transformerException.printStackTrace();
        }
        return pathForGeneratedXML;
    }
    public static String validateXml(File schemaFile, File xmlFile){
        String result = "xml file is valid";
        System.out.println("path of xml schema upload: "+schemaFile.getAbsolutePath());
        System.out.println("path of xml upload: " + xmlFile.getAbsolutePath());
        try {
            DocumentBuilderFactory factoryDoc = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factoryDoc.newDocumentBuilder();
            Document parse = builder.parse(xmlFile.getAbsolutePath());
            SchemaFactory factory = SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);
            Schema schema = factory.newSchema(schemaFile);
            Validator validator = schema.newValidator();
            validator.validate(new StreamSource(xmlFile));
        }catch(org.xml.sax.SAXException e1){
            System.out.println("SAX Exception: "+e1.getMessage());
            return result = "File " + xmlFile.getName() + " is not valid with schema in file " + schemaFile.getName();
        }
        catch (IOException e){
            System.out.println("Exception: "+e.getMessage());
            return "";
        } catch (ParserConfigurationException parserConfigurationException) {
             return result = "File " + xmlFile.getName() + " is not wellformed";
        }
        return result;
    }
    private static void initXMLData(){
        if(studentDTOList == null){
            studentDTOList = new ArrayList<>();
        }
        if(studentDTOList.isEmpty()){
            for(int i = 1; i <= 1000; i++){
                StudentDTO dto = new StudentDTO();
                dto.setId("SE"+i);
                if(i % 2 == 0){
                    dto.setFirstName("Phong");
                    dto.setLastName("Hoang Thanh");
                    dto.setDob("2000-06-02");
                    dto.setMajorId("SE");
                    dto.setEmail("phonght@gmail.com");
                    dto.setPhoneNumber("02222222");
                }else{
                    dto.setFirstName("Tin");
                    dto.setLastName("Nguyen Thanh");
                    dto.setDob("2000-04-02");
                    dto.setEmail("tinnt@gmail.com");
                    dto.setMajorId("GD");
                    dto.setPhoneNumber("0010101");
                }
                dto.setSex(true);
                dto.setStatus(EStudentStatus.STUDYING.getName());
                studentDTOList.add(dto);
            }
        }
        if(majorDTOList == null){
            majorDTOList = new ArrayList<>();
        }
        if(majorDTOList.isEmpty()){
            for(int i = 1; i <= 2; i++){
                MajorDTO dto = new MajorDTO();
                if(i % 2 == 0){
                    dto.setId("SE");
                    dto.setName("Software Engineer");
                }else{
                    dto.setId("GD");
                    dto.setName("Graphic Design");
                }
                majorDTOList.add(dto);
            }
        }
    }
    public static void saveXMLContent(Document d, File xmlDb) throws TransformerException {
        try {
            TransformerFactory tff = TransformerFactory.newInstance();
            Transformer tf = tff.newTransformer();
            tf.setOutputProperty(OutputKeys.INDENT, "yes");
            DOMSource source = new DOMSource(d);
            StreamResult rs = new StreamResult(xmlDb);
            tf.transform(source, rs);
        } catch (TransformerConfigurationException ex) {
            System.out.println("save xml transform exception "+ex.getMessage());
        }
    }

    public static Element createStudentElement(StudentDTO dto, Document document){
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
        sex.appendChild(document.createTextNode(dto.isSex()?"true":"false"));
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
        student.setAttribute("majorId",dto.getMajorId());
        student.setAttribute("ID", dto.getId());
        return student;
    }

    public static MajorDTO createMajorDtoFromElement(Element major){
        MajorDTO dto = new MajorDTO();
        String currId = "",currName = "";
            NodeList majorChildNodes = major.getChildNodes();
            for (int j = 0; j < majorChildNodes.getLength(); j++) {
                Node curr = majorChildNodes.item(j);
                String nodeName = majorChildNodes.item(j).getNodeName();
                switch (nodeName){
                    case "majorId":{
                        currId = DomHelper.getNodeValue(nodeName,curr);
                        break;
                    }
                    case "majorName":{
                        currName = DomHelper.getNodeValue(nodeName,curr);
                        break;
                    }
                }
            }
            dto.setName(currName);
            dto.setId(currId);
            return dto;
    }
}
