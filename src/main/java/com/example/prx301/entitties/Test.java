package com.example.prx301.entitties;

import com.example.prx301.dto.EStudentStatus;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class Test {
    public static DB initXMLData() {
        List<Student> studentDTOList = null;
        List<Major> majorDTOList = null;
        studentDTOList = new ArrayList<>();
        if (studentDTOList.isEmpty()) {
            for (int i = 1; i <= 1000; i++) {
                Student dto = new Student();
                dto.setId("SE" + i);
                if (i % 2 == 0) {
                    Major newSEMajor = new Major();
                    newSEMajor.setName("Software Engineer");
                    newSEMajor.setId("SE");
                    dto.setFirstName("Phong");
                    dto.setLastName("Hoang Thanh");
                    dto.setDob("2000-06-02");
                    dto.setMajorId(newSEMajor.getId());
                    dto.setEmail("phonght@gmail.com");
                    dto.setPhoneNumber("02222222");
                } else {
                    Major newGDMajor = new Major();
                    newGDMajor.setName("Graphic Design");
                    newGDMajor.setId("GD");
                    dto.setFirstName("Tin");
                    dto.setLastName("Nguyen Thanh");
                    dto.setDob("2000-04-02");
                    dto.setEmail("tinnt@gmail.com");
                    dto.setMajorId(newGDMajor.getId());
                    dto.setPhoneNumber("0010101");
                }
                dto.setSex(true);
                dto.setStatus(EStudentStatus.STUDYING.getName());
                studentDTOList.add(dto);
            }
        }
        if (majorDTOList == null) {
            majorDTOList = new ArrayList<>();
        }
        if (majorDTOList.isEmpty()) {
            for (int i = 1; i <= 2; i++) {
                Major dto = new Major();
                if (i % 2 == 0) {
                    dto.setId("SE");
                    dto.setName("Software Engineer");
                } else {
                    dto.setId("GD");
                    dto.setName("Graphic Design");
                }
                majorDTOList.add(dto);
            }
        }
        return new DB(new Majors(majorDTOList),new Students(studentDTOList));
    }

    public static void main(String[] args) {
        try {
            Unmarshaller unmarshaller = JAXBContext.newInstance(DB.class).createUnmarshaller();
            Marshaller marshaller = JAXBContext.newInstance(DB.class).createMarshaller();;
            DB db = (DB) unmarshaller.unmarshal(new File("src/main/xml/temp.xml"));
            db.setStudents(new Students());
            marshaller.marshal(db,new File("src/main/xml/temp.xml"));
        } catch (JAXBException e) {
            e.printStackTrace();
        } catch (Exception exception){
            exception.printStackTrace();
        }
    }
}
