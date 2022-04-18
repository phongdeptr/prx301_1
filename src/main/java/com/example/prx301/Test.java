package com.example.prx301;

import com.example.prx301.entities.DbType;
import com.example.prx301.entities.ObjectFactory;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import java.io.File;

public class Test {
    public static void main(String[] args) {
        try {
            JAXBContext context = JAXBContext.newInstance(DbType.class);
            Unmarshaller unmarshaller = context.createUnmarshaller();
            DbType db = (DbType) unmarshaller.unmarshal(new File("C:\\Users\\ADMIN\\Desktop\\New folder\\prx301_ass\\src\\main\\xml\\xmlprx301_ass.xml"));
            db.getStudents().getStudent().forEach(studentType -> {
                System.out.println(studentType.getEmail());
            });

        } catch (JAXBException e) {
            e.printStackTrace();
        }
    }
}
