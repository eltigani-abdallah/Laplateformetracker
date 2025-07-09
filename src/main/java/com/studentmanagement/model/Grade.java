package com.studentmanagement.model;

//Minimal version to compile without errors
public class Grade {
    private Long id;
    private Long studentId;
    private String subject;
    private double value;
    private double coefficient;
    
    public Grade() {
    }
    
    public Long getId() {
        return id;
    }
    
    public void setId(Long id) {
        this.id = id;
    }
    
    public Long getStudentId() {
        return studentId;
    }
    
    public void setStudentId(Long studentId) {
        this.studentId = studentId;
    }
    
    public String getSubject() {
        return subject;
    }
    
    public void setSubject(String subject) {
        this.subject = subject;
    }
    
    public double getValue() {
        return value;
    }
    
    public void setValue(double value) {
        this.value = value;
    }
    
    public double getCoefficient() {
        return coefficient;
    }
    
    public void setCoefficient(double coefficient) {
        this.coefficient = coefficient;
    }
}