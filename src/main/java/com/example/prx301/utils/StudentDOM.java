package com.example.prx301.utils;


import com.example.prx301.dto.StudentDTO;
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
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;

/**
 *
 * @author Admin
 */
public class StudentDOM {

    public static final String DOCUMENT_PATH = "D:\\FPTU\\XML\\prx301_1\\src\\main\\resources\\storage\\xml\\xmlprx301_ass.xml";

    public void Add(StudentDTO dto) throws TransformerException, IOException, SAXException, ParserConfigurationException {
        DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder documentBuilder = documentBuilderFactory.newDocumentBuilder();
        Document document = documentBuilder.parse(DOCUMENT_PATH);
        Element root = document.getDocumentElement();
        Node student = root.getChildNodes().item(3);
        System.out.println("test: "+student.getNodeName());

        Collection<StudentDTO> servers = new ArrayList<StudentDTO>();
        servers.add(dto);
        servers.stream().forEach((studentDTO -> {
            
        }));
//        for (StudentDTO server : servers) {
//            // server elements
//            Element newServer = document.createElement("student");
//            newServer.setAttribute("ID", server.getId());
//
//            Element firstname = document.createElement("firstName");
//            firstname.appendChild(document.createTextNode(server.getFirstName()));
//            newServer.appendChild(firstname);
//
//            Element lastName = document.createElement("lastName");
//            lastName.appendChild(document.createTextNode(server.getLastName()));
//            newServer.appendChild(lastName);
//
//            Element email = document.createElement("email");
//            email.appendChild(document.createTextNode(server.getEmail()));
//            newServer.appendChild(email);
//
//            Element dob = document.createElement("dob");
//            dob.appendChild(document.createTextNode(server.getDob()));
//            newServer.appendChild(dob);
//
//            Element sex = document.createElement("sex");
//            sex.appendChild(document.createTextNode(String.valueOf(server.getSex())));
//            newServer.appendChild(sex);
//
//            Element phone = document.createElement("phone");
//            phone.appendChild(document.createTextNode(server.getPhoneNumber()));
//            newServer.appendChild(phone);
//
//            Element status = document.createElement("status");
//            status.appendChild(document.createTextNode(String.valueOf(server.getStatus())));
//            newServer.appendChild(status);
//
//            student.appendChild(newServer);
//        }

        DOMSource source = new DOMSource(document);

        TransformerFactory transformerFactory = TransformerFactory.newInstance();
        Transformer transformer = transformerFactory.newTransformer();
        StreamResult result = new StreamResult(DOCUMENT_PATH);
        transformer.transform(source, result);
    }

    public void delete(String id) {
        Document d;
        try {
            d = DomHelpers.getDocument(DOCUMENT_PATH);
            NodeList nl = d.getElementsByTagName("student");
            for (int i = 0; i < nl.getLength(); i++) {
                Element sStudent = (Element) nl.item(i);
                if (sStudent.getElementsByTagName("id").item(0).getTextContent().equals(id)) {
                    sStudent.getParentNode().removeChild(sStudent);
                }
            }
            //Write to file
            DomHelpers.saveXMLContent(d, DOCUMENT_PATH);

            System.out.println("Delete success");
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public void update(String id, String name, int age) {
        Document d;
        try {
            d = DomHelpers.getDocument(DOCUMENT_PATH);
            NodeList nl = d.getElementsByTagName("student");
            for (int i = 0; i < nl.getLength(); i++) {
                Element sStudent = (Element) nl.item(i);
                if (sStudent.getElementsByTagName("id").item(0).getTextContent().equals(id)) {
                    sStudent.getElementsByTagName("name").item(0).setTextContent(name);
                    sStudent.getElementsByTagName("age").item(0).setTextContent(String.valueOf(age));
                }
            }
            //Write to file
            DomHelpers.saveXMLContent(d, DOCUMENT_PATH);
            System.out.println("Update success");
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
}