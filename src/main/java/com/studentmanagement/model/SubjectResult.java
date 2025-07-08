package com.studentmanagement.model;

public class SubjectResult {
    private Long id;
    private Long studentId;
    private String subject;
    private double averageGrade;
    private double coefficient;

    // Default constructor
    public SubjectResult() {}

    // Constructor with all relevant fields except id
    public SubjectResult(Long studentId, String subject, double averageGrade, double coefficient) {
        this.studentId = studentId;
        this.subject = subject;
        this.averageGrade = averageGrade;
        this.coefficient = coefficient;
    }

    // Getter and Setter for id
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    // Getter and Setter for studentId
    public Long getStudentId() {
        return studentId;
    }

    public void setStudentId(Long studentId) {
        this.studentId = studentId;
    }

    // Getter and Setter for subject
    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    // Getter and Setter for averageGrade
    public double getAverageGrade() {
        return averageGrade;
    }

    public void setAverageGrade(double averageGrade) {
        this.averageGrade = averageGrade;
    }

    // Getter and Setter for coefficient
    public double getCoefficient() {
        return coefficient;
    }

    public void setCoefficient(double coefficient) {
        this.coefficient = coefficient;
    }

    // Method to get weighted score
    public double getTotalWeightedScore() {
        return averageGrade * coefficient;
    }

    // toString method
    @Override
    public String toString() {
        return "SubjectResult{" +
                "id=" + id +
                ", studentId=" + studentId +
                ", subject='" + subject + '\'' +
                ", averageGrade=" + averageGrade +
                ", coefficient=" + coefficient +
                ", totalWeightedScore=" + getTotalWeightedScore() +
                '}';
    }
}
