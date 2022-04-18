package com.example.prx301.configurations;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.TransformerFactory;

@Configuration
public class XMLFactory {
    @Bean
    public TransformerFactory transformerFactory(){
        return TransformerFactory.newInstance();
    }
}
