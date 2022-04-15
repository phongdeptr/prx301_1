package com.example.prx301.dto;

public enum EStudentStatus {
    STUDYING("study"),
    DROPOUT("dropout"),
    GRADUATED("graduated");
    private String name;

    EStudentStatus(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
