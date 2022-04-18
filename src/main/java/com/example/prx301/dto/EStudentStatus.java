package com.example.prx301.dto;

public enum EStudentStatus {
    STUDYING("study"),
    DROPOUT("dropout"),
    GRADUATED("graduated");
    private final String name;

    EStudentStatus(String name) {
        this.name = name;
    }
    public String getName() {
        return name;
    }
    public static EStudentStatus valueOfStr(String str){
        switch (str){
            case "study":
                return EStudentStatus.STUDYING;
            case "dropout":
                return EStudentStatus.DROPOUT;
            case "graduated":
                return EStudentStatus.GRADUATED;
            default:
                return null;
        }
    }
}
