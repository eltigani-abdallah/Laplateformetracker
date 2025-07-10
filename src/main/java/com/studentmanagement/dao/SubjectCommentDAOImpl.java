package com.studentmanagement.dao;

import com.studentmanagement.model.SubjectComment;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

// This class implements the SubjectCommentDAO interface and extends the BaseDAO class
// It provides methods to interact with the database for SubjectComment objects
public class SubjectCommentDAOImpl extends BaseDAO<SubjectComment> implements SubjectCommentDAO {

    // Connection object to interact with the database
    private final Connection connection;

    // Constructor to initialize the database connection
    public SubjectCommentDAOImpl(Connection connection) {
        this.connection = connection;
    }

    // Method to save a comment to the database
    @Override
    public void saveComment(SubjectComment comment) throws SQLException {
        // SQL query to insert a new comment into the subject_comments table
        String query = """
            INSERT INTO subject_comments (student_id, subject_id, comment)
            VALUES (?, (SELECT id FROM subject WHERE LOWER(name) = LOWER(?)), ?)
        """;
        // Using try-with-resources to ensure the PreparedStatement is closed after use
        try (PreparedStatement ps = connection.prepareStatement(query)) {
            ps.setLong(1, comment.getStudentId());
            ps.setString(2, comment.getSubject());
            ps.setString(3, comment.getComment());
            ps.executeUpdate(); // Execute the update query
        }
    }

    // Method to update an existing comment in the database
    @Override
    public void updateComment(SubjectComment comment) throws SQLException {
        // SQL query to update the comment in the subject_comments table
        String query = "UPDATE subject_comments SET comment = ? WHERE id = ?";
        try (PreparedStatement ps = connection.prepareStatement(query)) {
            ps.setString(1, comment.getComment());
            ps.setLong(2, comment.getId());
            ps.executeUpdate(); // Execute the update query
        }
    }

    // Method to delete a comment from the database
    @Override
    public void deleteComment(Long commentId) throws SQLException {
        // SQL query to delete a comment from the subject_comments table
        String query = "DELETE FROM subject_comments WHERE id = ?";
        try (PreparedStatement ps = connection.prepareStatement(query)) {
            ps.setLong(1, commentId);
            ps.executeUpdate(); // Execute the update query
        }
    }

    // Method to find comments by student ID and subject name
    @Override
    public List<SubjectComment> findCommentsByStudentAndSubject(Long studentId, String subjectName) throws SQLException {
        // SQL query to select comments based on student_id and subject name
        String query = """
            SELECT sc.id, sc.student_id, s.name AS subject, sc.comment
            FROM subject_comments sc
            JOIN subject s ON sc.subject_id = s.id
            WHERE sc.student_id = ? AND LOWER(s.name) LIKE LOWER(?)
        """;
        List<SubjectComment> comments = new ArrayList<>();
        try (PreparedStatement ps = connection.prepareStatement(query)) {
            ps.setLong(1, studentId);
            ps.setString(2, "%" + subjectName + "%");
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    comments.add(mapResultSetToComment(rs)); // Map each result set row to a SubjectComment object
                }
            }
        }
        return comments;
    }

    // Helper method to map a ResultSet row to a SubjectComment object
    private SubjectComment mapResultSetToComment(ResultSet rs) throws SQLException {
        SubjectComment comment = new SubjectComment();
        comment.setId(rs.getLong("id"));
        comment.setStudentId(rs.getLong("student_id"));
        comment.setSubject(rs.getString("subject"));  // Here subject name is used
        comment.setComment(rs.getString("comment"));
        return comment;
    }
}
