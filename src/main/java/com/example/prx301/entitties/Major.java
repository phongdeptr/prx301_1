package com.example.prx301.entitties;

import com.example.prx301.dto.MajorDTO;
import com.example.prx301.dto.StudentDTO;
import lombok.Data;

import javax.xml.bind.annotation.*;
import java.util.Optional;

@Data
@XmlRootElement(name = "major",namespace = "https://phonght.com")
@XmlAccessorType(XmlAccessType.FIELD)
public class Major {
    @XmlElement(name = "majorId",namespace = "https://phonght.com")
    private String id;
    @XmlElement(name = "majorName",namespace = "https://phonght.com")
    private String name;

    public static Optional<Major> fromDtoToEntity(MajorDTO dto){
        Optional<Major> studentOptional;
        if(dto == null){
            studentOptional = Optional.empty();
        }else{
            Major major = new Major();
            major.setId(dto.getId());
            major.setName(dto.getName());
            studentOptional = Optional.of(major);
        }
        return studentOptional;
    }
}
