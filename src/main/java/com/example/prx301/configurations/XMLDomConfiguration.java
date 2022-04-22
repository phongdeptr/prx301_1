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
    public File xmlDb() throws IOException {
        File file =  new File("src/main/xml/"+appName+".xml");
        boolean exists = file.exists();
        if(!exists){
            file.createNewFile();
        }
        return file;
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
            File xml = xmlDb();
            newDoc = documentBuilder.parse(xml);
        } catch (IOException | SAXException e) {
            e.printStackTrace();
        }
        return newDoc;
    }
}
