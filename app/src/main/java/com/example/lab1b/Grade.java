package com.example.lab1b;

public class Grade {
    private String name;
    private Double grade;

    public Grade(String name, Double grade) {
        this.name = name;
        this.grade = grade;
    }

    public String getName() {
        return name;
    }

    public Double getGrade() {
        return grade;
    }

    public void setGrade(Double grade1){
        this.grade = grade1;

    }

    public String toString(){
        return name + ": " + grade;
    }
}
