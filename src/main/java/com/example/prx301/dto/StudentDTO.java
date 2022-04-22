package com.example.prx301.dto;

import com.example.prx301.entitties.Student;
import lombok.Data;

import java.util.Optional;

@Data
public class StudentDTO {
    private String id;
    private String firstName;
    private String lastName;
    private String email;
    private String dob;
    private boolean sex;
    private String phoneNumber;
    private String status;
    private MajorDTO major;

    public StudentDTO() {
    }

    public StudentDTO(String id, String firstName, String lastName, String email, String dob, boolean sex, String phoneNumber, String status, MajorDTO major) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.dob = dob;
        this.sex = sex;
        this.phoneNumber = phoneNumber;
        this.status = status;
        this.major = major;
    }

    public static Optional<StudentDTO> fromEntityToDto(Student myStudent){
        Optional<StudentDTO> dtoOptional = Optional.empty();
        StudentDTO targetDto = new StudentDTO();
        targetDto.setId(myStudent.getId());
        targetDto.setFirstName(myStudent.getFirstName());
        targetDto.setLastName(myStudent.getLastName());
        targetDto.setDob(myStudent.getDob());
        targetDto.setMajor(new MajorDTO(myStudent.getMajorId(), null));
        targetDto.setEmail(myStudent.getEmail());
        targetDto.setSex(myStudent.isSex());
        targetDto.setStatus(myStudent.getStatus());
        targetDto.setPhoneNumber(myStudent.getPhoneNumber());
        dtoOptional = Optional.of(targetDto);
        return dtoOptional;
    }
}
