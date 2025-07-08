// GradeDAOImpl.java (implementation)
package com.studentmanagement.dao;

import com.studentmanagement.dao.GradeDAO;
import com.studentmanagement.model.Grade;
import com.studentmanagement.model.SubjectResult;
import com.studentmanagement.util.SearchCriteria;
import com.studentmanagement.util.DatabaseConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class GradeDAOImpl implements GradeDAO {

    private final Connection connection;
    private final DatabaseConnection dbConnection;

    // Constructor to initialize the database connection
    public GradeDAOImpl(Connection connection) {
        this.connection = connection;
        this.dbConnection = new DatabaseConnection();
    }

    // Save a grade to the database
    @Override
    public void saveGrade(Grade grade) {
        String sql = "INSERT INTO grades (student_id, subject_id, grade) VALUES (?, ?, ?)";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setLong(1, grade.getStudentId());
            stmt.setLong(2, grade.getSubjectId());
            stmt.setDouble(3, grade.getGrade());
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Update an existing grade in the database
    @Override
    public void updateGrade(Grade grade) {
        String sql = "UPDATE grades SET grade = ? WHERE id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setDouble(1, grade.getGrade());
            stmt.setLong(2, grade.getId());
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Delete a grade from the database by its ID
    @Override
    public void deleteGrade(Long gradeId) {
        String sql = "DELETE FROM grades WHERE id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setLong(1, gradeId);
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Save a coefficient to the database
    @Override
    public void saveCoefficient(double coefficient) {
        String sql = "INSERT INTO coefficients (value) VALUES (?)";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setDouble(1, coefficient);
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Update an existing coefficient in the database
    @Override
    public void updateCoefficient(Long coefficientId, double newCoefficient) {
        String sql = "UPDATE coefficients SET value = ? WHERE id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setDouble(1, newCoefficient);
            stmt.setLong(2, coefficientId);
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Retrieve the minimum average grade for a specific subject
    @Override
    public double getMinAverageBySubject(String subjectName) {
        String sql = "SELECT MIN(calculateWeightedAverageGrade(student_id, id)) FROM subject WHERE LOWER(name) = LOWER(?)";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, subjectName);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) return rs.getDouble(1);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }

    // Retrieve the maximum average grade for a specific subject
    @Override
    public double getMaximumAverageBySubject(String subjectName) {
        String sql = "SELECT MAX(calculateWeightedAverageGrade(student_id, id)) FROM subject WHERE LOWER(name) = LOWER(?)";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, subjectName);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) return rs.getDouble(1);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }

    // Search for subject results based on student ID and search criteria
    @Override
    public List<SubjectResult> searchBySubject(Long studentId, SearchCriteria criteria) {
        List<SubjectResult> results = new ArrayList<>();
        String sql = """
            SELECT
                s.name AS subject,
                GROUP_CONCAT(g.grade, ', ') as grades,
                (SELECT calculateWeightedAverageGrade(g.student_id, g.subject_id)) as student_average,
                (SELECT MIN(calculateWeightedAverageGrade(student_id, g.subject_id))
                 FROM (SELECT DISTINCT student_id FROM grades WHERE subject_id = g.subject_id) s2) as class_min_average,
                (SELECT MAX(calculateWeightedAverageGrade(student_id, g.subject_id))
                 FROM (SELECT DISTINCT student_id FROM grades WHERE subject_id = g.subject_id) s2) as class_max_average,
                sc.comment as teacher_comment
            FROM grades g
            LEFT JOIN subject s ON g.subject_id = s.id
            LEFT JOIN subject_comments sc ON sc.student_id = g.student_id AND sc.subject_id = g.subject_id
            WHERE g.student_id = ? AND LOWER(s.name) LIKE LOWER(?)
            GROUP BY s.name, sc.comment
            ORDER BY s.name
            LIMIT ? OFFSET ?
        """;
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setLong(1, studentId);
            stmt.setString(2, "%" + criteria.getSearchTerm() + "%");
            stmt.setInt(3, criteria.getLimit());
            stmt.setInt(4, criteria.getOffset());
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                SubjectResult result = new SubjectResult();
                result.setSubject(rs.getString("subject"));
                result.setGrades(rs.getString("grades"));
                result.setStudentAverage(rs.getDouble("student_average"));
                result.setClassMinAverage(rs.getDouble("class_min_average"));
                result.setClassMaxAverage(rs.getDouble("class_max_average"));
                result.setComment(rs.getString("teacher_comment"));
                results.add(result);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return results;
    }

    // Count the number of subjects for a specific student with a subject name filter
    @Override
    public Long countBySubject(Long studentId, String subjectNameFilter) {
        String sql = "SELECT COUNT(DISTINCT subject_id) FROM grades WHERE student_id = ? AND subject_id IN (SELECT id FROM subject WHERE LOWER(name) LIKE LOWER(?))";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setLong(1, studentId);
            stmt.setString(2, "%" + subjectNameFilter + "%");
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) return rs.getLong(1);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0L;
    }

    // Calculate the weighted average grade for a specific student
    @Override
    public double calculateWeightedAverageGrade(Long studentId) {
        String sql = "SELECT calculateWeightedAverageGrade(?)";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setLong(1, studentId);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) return rs.getDouble(1);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }

    // Count the total number of grades in the database
    @Override
    public Long count() {
        String sql = "SELECT COUNT(*) FROM grades";
        try (Statement stmt = connection.createStatement()) {
            ResultSet rs = stmt.executeQuery(sql);
            if (rs.next()) return rs.getLong(1);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0L;
    }

    // Helper method to map a ResultSet to a Grade object
    private Grade mapResultSetToGrade(ResultSet rs) throws SQLException {
        Grade grade = new Grade();
        grade.setId(rs.getLong("id"));
        grade.setStudentId(rs.getLong("student_id"));
        grade.setSubjectId(rs.getLong("subject_id"));
        grade.setGrade(rs.getDouble("grade"));
        return grade;
    }
}
