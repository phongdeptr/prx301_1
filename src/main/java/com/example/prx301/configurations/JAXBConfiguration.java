package com.example.prx301.configurations;

import com.example.prx301.entitties.DB;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import java.io.File;

@Configuration
public class JAXBConfiguration {
    @Bean
    public JAXBContext jaxbContext() throws JAXBException {
        return JAXBContext.newInstance(DB.class);
    }

    @Bean
    public DB getDB(JAXBContext context, File xmlFile) throws JAXBException {
        return (DB)context.createUnmarshaller().unmarshal(xmlFile);
    }

    @Bean
    public Marshaller mashaller(JAXBContext context, File xmlFile) throws JAXBException {
       return context.createMarshaller();
    }
}
