package com.example.prx301.dto;
import java.util.List;

public class DB {
    private List<StudentDTO> students;
    private List<MajorDTO> majors;

    public DB() {
    }

    public DB(List<StudentDTO> students, List<MajorDTO> majors) {
        this.students = students;
        this.majors = majors;
    }

    public List<StudentDTO> getStudents() {
        return students;
    }

    public void setStudents(List<StudentDTO> students) {
        this.students = students;
    }

    public List<MajorDTO> getMajors() {
        return majors;
    }

    public void setMajors(List<MajorDTO> majors) {
        this.majors = majors;
    }
}
