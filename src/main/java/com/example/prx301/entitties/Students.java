package com.example.prx301.entitties;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;
@Data
@AllArgsConstructor
@NoArgsConstructor
@XmlRootElement(name = "students",namespace = "https://phonght.com")
@XmlAccessorType(XmlAccessType.FIELD)
public class Students {
    @XmlElement(name = "student",namespace = "https://phonght.com")
    private List<Student> students;
}
