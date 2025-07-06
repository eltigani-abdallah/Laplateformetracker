//package main.java.com.studentmanagement.model;
//package model;
package com.studentmanagement.model;

public class Student {
    private int id;
    private String name;
    private String email;
    private String birthdate;

    public Student(int id, String name, String email, String birthdate) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.birthdate = birthdate;
    }

    public Student(String name, String email, String birthdate) {
        this.name = name;
        this.email = email;
        this.birthdate = birthdate;
    }

    // Getters & Setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getBirthdate() { return birthdate; }
    public void setBirthdate(String birthdate) { this.birthdate = birthdate; }
}

