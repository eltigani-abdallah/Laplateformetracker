
package com.studentmanagement.dao;
import java.sql.SQLException;
import java.util.List;

public interface SubjectCommentDAO {
    void saveComment(SubjectComment comment) throws SQLException;
    void updateComment(SubjectComment comment) throws SQLException;
    void deleteComment(Long commentId) throws SQLException;
    List<SubjectComment> findCommentsByStudentAndSubject(Long studentId, String subjectName) throws SQLException;
}
