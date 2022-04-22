package com.example.prx301.entitties;
import com.example.prx301.dto.MajorDTO;
import com.example.prx301.dto.StudentDTO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.xml.bind.annotation.*;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@XmlRootElement(name = "db",namespace = "https://phonght.com")
@XmlAccessorType(XmlAccessType.FIELD)
@Data
public class DB {
    @XmlElement(name = "majors",namespace = "https://phonght.com")
    private Majors majors;
    @XmlElement(name = "students",namespace = "https://phonght.com")
    private Students students;
}
