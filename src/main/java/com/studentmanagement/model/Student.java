//package main.java.com.studentmanagement.model;

package com.studentmanagement.model;

public class Student {
    private Long studentId;
    private String firstName;
    private String lastName;
    private int age;
    private String className;
    private double averageGrade;

    // Default constructor
    public Student() {}

    // Constructor with basic parameters
    public Student(String firstName, String lastName, int age) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.age = age;
    }

    // Getter and Setter for studentId
    public Long getStudentId() {
        return studentId;
    }

    public void setStudentId(Long studentId) {
        this.studentId = studentId;
    }

    // Getter and Setter for firstName
    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    // Getter and Setter for lastName
    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    // Getter and Setter for age
    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    // Getter and Setter for className
    public String getStudentClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    // Getter and Setter for averageGrade
    public double getAverageGrade() {
        return averageGrade;
    }

    public void setAverageGrade(double averageGrade) {
        this.averageGrade = averageGrade;
    }

    // Method to get full name
    public String getFullName() {
        return firstName + " " + lastName;
    }

    // toString method
    @Override
    public String toString() {
        return "Student{" +
                "studentId=" + studentId +
                ", fullName='" + getFullName() + '\'' +
                ", age=" + age +
                ", className='" + className + '\'' +
                ", averageGrade=" + averageGrade +
                '}';
    }
}

