package com.example.prx301.configurations;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathFactory;

@Configuration
public class XPathConfiguration {

    @Bean
    public XPathFactory xPathFactory(){
        XPathFactory factory = XPathFactory.newInstance();
        return factory;
    }
    @Bean
    public XPath xPath(){
        XPathFactory factory = xPathFactory();
        XPath xPath = factory.newXPath();
        return xPath;
    }
}
