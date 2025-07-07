package com.studentmanagement.model;

import java.util.Date;

public class Grade {
    private Long id;
    private Long studentId;
    private String subject;
    private double value;
    private double coefficient;
    private Date date;

    // Default constructor
    public Grade() {}

    // Constructor with student ID, subject, value, and coefficient
    public Grade(Long studentId, String subject, double value, double coefficient) {
        this.studentId = studentId;
        this.subject = subject;
        this.value = value;
        this.coefficient = coefficient;
        this.date = new Date(); // default to current date
    }

    // Getter and setter for id (grade ID)
    public Long getGradeId() {
        return id;
    }

    public void setGradeId(Long id) {
        this.id = id;
    }

    // Getter and setter for studentId
    public Long getStudentId() {
        return studentId;
    }

    public void setStudentId(Long studentId) {
        this.studentId = studentId;
    }

    // Getter and setter for subject
    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    // Getter and setter for value
    public double getValue() {
        return value;
    }

    public void setValue(double value) {
        this.value = value;
    }

    // Getter and setter for coefficient
    public double getCoefficient() {
        return coefficient;
    }

    public void setCoefficient(double coefficient) {
        this.coefficient = coefficient;
    }

    // Getter and setter for date
    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    // Method to get the weighted grade value
    public double getWeightedGradeValue() {
        return value * coefficient;
    }

    // toString method
    @Override
    public String toString() {
        return "Grade{" +
                "gradeId=" + id +
                ", studentId=" + studentId +
                ", subject='" + subject + '\'' +
                ", value=" + value +
                ", coefficient=" + coefficient +
                ", date=" + date +
                ", weightedValue=" + getWeightedGradeValue() +
                '}';
    }
}
