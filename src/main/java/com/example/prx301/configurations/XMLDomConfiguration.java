package com.example.prx301.configurations;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.w3c.dom.Document;
import org.xml.sax.SAXException;

import javax.print.Doc;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.File;
import java.io.IOException;

@Configuration
public class XMLDomConfiguration {
    @Bean
    public DocumentBuilderFactory documentBuilderFactory(){
        return DocumentBuilderFactory.newInstance();
    }

    @Bean
    public DocumentBuilder documentBuilder(DocumentBuilderFactory documentBuilderFactory) throws ParserConfigurationException {
        return documentBuilderFactory.newDocumentBuilder();
    }

    @Bean
    public Document document(DocumentBuilder documentBuilder) throws ParserConfigurationException, IOException, SAXException {
        return documentBuilder.parse(new File("src/main/xml/xmlprx301_ass.xml"));
    }
}
