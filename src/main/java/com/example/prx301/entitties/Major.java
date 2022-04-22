package com.example.prx301.entitties;

import lombok.Data;

import javax.xml.bind.annotation.*;

@Data
@XmlRootElement(name = "major",namespace = "https://phonght.com")
@XmlAccessorType(XmlAccessType.FIELD)
public class Major {
    @XmlElement(name = "majorId",namespace = "https://phonght.com")
    private String id;
    @XmlElement(name = "majorName",namespace = "https://phonght.com")
    private String name;
}
