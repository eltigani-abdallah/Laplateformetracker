package com.studentmanagement.model;

public class SubjectComment {
    private Long id;
    private Long studentId;
    private String subject;
    private String comment;

    // Default constructor
    public SubjectComment() {}

    // Getter for id
    public Long getId() {
        return id;
    }

    // Setter for id 
    public void setId(Long id) {
        this.id = id;
    }

    // Getter for studentId
    public Long getStudentId() {
        return studentId;
    }

    // Setter for studentId 
    public void setStudentId(Long studentId) {
        this.studentId = studentId;
    }

    // Getter for subject
    public String getSubject() {
        return subject;
    }

    // Setter for subject
    public void setSubject(String subject) {
        this.subject = subject;
    }

    // Getter for comment
    public String getComment() {
        return comment;
    }

    // Setter for comment
    public void setComment(String comment) {
        this.comment = comment;
    }

    // Optional: toString method for debugging/logging
    @Override
    public String toString() {
        return "SubjectComment{" +
                "id=" + id +
                ", studentId=" + studentId +
                ", subject='" + subject + '\'' +
                ", comment='" + comment + '\'' +
                '}';
    }
}
