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
@NoArgsConstructor
@AllArgsConstructor
@XmlRootElement(name = "majors",namespace = "https://phonght.com")
@XmlAccessorType(XmlAccessType.FIELD)
public class Majors {
    @XmlElement(name = "major",namespace = "https://phonght.com")
    private List<Major> majors;
}
