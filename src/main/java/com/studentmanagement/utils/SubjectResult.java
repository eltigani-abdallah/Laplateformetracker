package com.studentmanagement.utils;

public class SubjectResult {
    private String subject;
    private String grades;
    private double studentAverage;
    private double classMinAverage;
    private double classMaxAverage;
    private String teacherComment;
    
    public SubjectResult() {
    }
    
    public SubjectResult(String subject, String grades, double studentAverage, 
                         double classMinAverage, double classMaxAverage, String teacherComment) {
        this.subject = subject;
        this.grades = grades;
        this.studentAverage = studentAverage;
        this.classMinAverage = classMinAverage;
        this.classMaxAverage = classMaxAverage;
        this.teacherComment = teacherComment;
    }
    
    public String getSubject() {
        return subject;
    }
    
    public void setSubject(String subject) {
        this.subject = subject;
    }
    
    public String getGrades() {
        return grades;
    }
    
    public void setGrades(String grades) {
        this.grades = grades;
    }
    
    public double getStudentAverage() {
        return studentAverage;
    }
    
    public void setStudentAverage(double studentAverage) {
        this.studentAverage = studentAverage;
    }
    
    public double getClassMinAverage() {
        return classMinAverage;
    }
    
    public void setClassMinAverage(double classMinAverage) {
        this.classMinAverage = classMinAverage;
    }
    
    public double getClassMaxAverage() {
        return classMaxAverage;
    }
    
    public void setClassMaxAverage(double classMaxAverage) {
        this.classMaxAverage = classMaxAverage;
    }
    
    public String getTeacherComment() {
        return teacherComment;
    }
    
    public void setTeacherComment(String teacherComment) {
        this.teacherComment = teacherComment;
    }
    
    @Override
    public String toString() {
        return "SubjectResult{" +
                "subject='" + subject + '\'' +
                ", grades='" + grades + '\'' +
                ", studentAverage=" + studentAverage +
                ", classMinAverage=" + classMinAverage +
                ", classMaxAverage=" + classMaxAverage +
                ", teacherComment='" + teacherComment + '\'' +
                '}';
    }
}