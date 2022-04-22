package com.example.prx301.entitties;

import com.example.prx301.dto.StudentDTO;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.xml.bind.annotation.*;
import java.util.Optional;

@Data
@NoArgsConstructor
@XmlRootElement(name = "student",namespace = "https://phonght.com")
@XmlAccessorType(XmlAccessType.FIELD)
public class Student {
    @XmlAttribute(name = "ID")
    private String id;
    @XmlAttribute(name = "majorId")
    private String majorId;
    @XmlElement(name = "firstName",namespace = "https://phonght.com")
    private String firstName;
    @XmlElement(name = "lastName",namespace = "https://phonght.com")
    private String lastName;
    @XmlElement(name = "email",namespace = "https://phonght.com")
    private String email;
    @XmlElement(name = "dob",namespace = "https://phonght.com")
    private String dob;
    @XmlElement(name = "sex",namespace = "https://phonght.com")
    private boolean sex;
    @XmlElement(name = "phone",namespace = "https://phonght.com")
    private String phoneNumber;
    @XmlElement(name = "status",namespace = "https://phonght.com")
    private String status;

    public static Optional<Student> fromDtoToEntity(StudentDTO dto){
        Optional<Student> studentOptional;
        if(dto == null){
            studentOptional = Optional.empty();
        }else{
            Student student = new Student();
            student.setId(dto.getId());
            student.setFirstName(dto.getFirstName());
            student.setLastName(dto.getLastName());
            student.setDob(dto.getDob());
            student.setMajorId(dto.getMajor().getId());
            student.setEmail(dto.getEmail());
            student.setSex(dto.isSex());
            student.setStatus(dto.getStatus());
            student.setPhoneNumber(dto.getPhoneNumber());
            studentOptional = Optional.of(student);
        }
        return studentOptional;
    }
}
