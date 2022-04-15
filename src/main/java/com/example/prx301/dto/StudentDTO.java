package com.example.prx301.dto;

public class StudentDTO {
    private String id;
    private String firstName;
    private String lastName;
    private String email;
    private String dob;
    private boolean sex;
    private String phoneNumber;
    private EStudentStatus status;
    private String majorId;

    public StudentDTO() {
    }

    public StudentDTO(String id, String firstName, String lastName, String email, String dob, boolean sex, String phoneNumber, EStudentStatus status, String majorId) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.dob = dob;
        this.sex = sex;
        this.phoneNumber = phoneNumber;
        this.status = status;
        this.majorId = majorId;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getDob() {
        return dob;
    }

    public void setDob(String dob) {
        this.dob = dob;
    }

    public boolean isSex() {
        return sex;
    }

    public void setSex(boolean sex) {
        this.sex = sex;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public EStudentStatus getStatus() {
        return status;
    }

    public void setStatus(EStudentStatus status) {
        this.status = status;
    }

    public String getMajorId() {
        return majorId;
    }

    public void setMajorId(String majorId) {
        this.majorId = majorId;
    }
}
