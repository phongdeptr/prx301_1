package com.example.prx301.dto;

import com.example.prx301.errors.MajorError;
import lombok.Data;

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
}
