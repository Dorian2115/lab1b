package com.example.lab1b;

public class Grade {
    private String name;
    private int grade;

    public Grade(String name, int grade) {
        this.name = name;
        this.grade = grade;
    }

    public String getName() {
        return name;
    }

    public int getGrade() {
        return grade;
    }

    public void setGrade(int grade1){
        this.grade = grade1;

    }

    public String toString(){
        return name + ": " + grade;
    }
}
