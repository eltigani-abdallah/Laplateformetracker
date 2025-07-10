package com.studentmanagement.service;

import com.studentmanagement.model.SubjectComment;

import java.util.ArrayList;
import java.util.List;

public class SubjectCommentService {
    
    public SubjectCommentService() {
    }
    
    public SubjectComment getCommentByStudentAndSubject(Long studentId, String subject) {
        return null;
    }
    
    public List<SubjectComment> getCommentsByStudent(Long studentId) {
        return new ArrayList<>();
    }
    
    public SubjectComment saveComment(SubjectComment comment) {
        return comment;
    }
    
    public SubjectComment updateComment(Long studentId, String subject, String newComment) {
        SubjectComment comment = new SubjectComment();
        comment.setStudentId(studentId);
        comment.setSubject(subject);
        comment.setComment(newComment);
        return comment;
    }
    
    public boolean deleteComment(Long studentId, String subject) {
        return true;
    }
    
    public boolean deleteAllCommentsForStudent(Long studentId) {
        return true;
    }
    
    public int countCommentsByStudent(Long studentId) {
        return 0;
    }
    
    public boolean commentExists(Long studentId, String subject) {
        return false;
    }
}
