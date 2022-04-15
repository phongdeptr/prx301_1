package com.example.prx301.entities;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;

@XmlRootElement(name = "db")
@XmlAccessorType(XmlAccessType.FIELD)
public class DB {
    @XmlElement(name = "students")
    private List<Student> students;
    @XmlElement(name = "majors")
    private List<Major> majors;

    public DB() {
    }

    public DB(List<Student> students, List<Major> majors) {
        this.students = students;
        this.majors = majors;
    }

    public List<Student> getStudents() {
        return students;
    }

    public void setStudents(List<Student> students) {
        this.students = students;
    }

    public List<Major> getMajors() {
        return majors;
    }

    public void setMajors(List<Major> majors) {
        this.majors = majors;
    }
}
