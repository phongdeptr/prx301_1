package com.example.prx301.configurations;

import com.example.prx301.utils.XMLHelpers;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.xml.sax.SAXException;
import org.xml.sax.SAXParseException;

import javax.print.Doc;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;
import java.io.File;
import java.io.IOException;

@Configuration
public class XMLDomConfiguration {
    @Value("${spring.application.name}")
    private String appName;
    @Bean
    public File xmlDb(){
        return new File("src/main/xml/"+appName+".xml");
    }

    @Bean
    public DocumentBuilderFactory documentBuilderFactory(){
        return DocumentBuilderFactory.newInstance();
    }

    @Bean
    public DocumentBuilder documentBuilder(DocumentBuilderFactory documentBuilderFactory) throws ParserConfigurationException {
        return documentBuilderFactory.newDocumentBuilder();
    }

    @Bean
    public Document document(DocumentBuilder documentBuilder) {
        Document newDoc = null;
        try {
            newDoc = documentBuilder.parse(xmlDb());
        } catch (IOException e) {
            e.printStackTrace();
        }catch (Exception saxException) {
            System.out.println(saxException.getMessage());
            newDoc = documentBuilder.newDocument();
            newDoc.createElement("db");
            Element db = newDoc.createElement("db");
            db.setAttribute("xmlns","https://phonght.com/xsd");
            db.setAttribute("xmlns:xsi","http://www.w3.org/2001/XMLSchema-instance");
            db.setAttribute("xsi:schemaLocation","https://phonght.com/xsd");
            Element majors = newDoc.createElement("majors");
            Element students = newDoc.createElement("students");
            db.appendChild(majors);
            db.appendChild(students);
            newDoc.appendChild(db);
            try {
                XMLHelpers.saveXMLContent(newDoc,xmlDb());
            } catch (TransformerException transformerException) {
                transformerException.printStackTrace();
            }
        }
        return newDoc;
    }
}
