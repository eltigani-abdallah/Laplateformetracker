package com.studentmanagement.dao;

import com.studentmanagement.model.Student;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class StudentDAOImpl extends BaseDAO<Student> implements StudentDAO {

    // Constructor to initialize the database connection
    public StudentDAOImpl(Connection connection) {
        super(connection);
    }

    // Save a student to the database
    @Override
    public void saveStudent(Student student) {
        String sql = "INSERT INTO students (first_name, last_name, age, class_id, average_grade) VALUES (?, ?, ?, ?, ?)";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, student.getFirstName());
            stmt.setString(2, student.getLastName());
            stmt.setInt(3, student.getAge());
            stmt.setNull(4, Types.INTEGER); // TODO: replace with student.getClassId() if available
            stmt.setDouble(5, student.getAverageGrade());
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Find a student by their ID
    @Override
    public Student findStudentById(Long id) {
        String sql = "SELECT s.*, c.class_name FROM students s LEFT JOIN class c ON s.class_id = c.id WHERE s.id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setLong(1, id);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) return mapResultSet(rs);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    // Retrieve all students from the database
    @Override
    public List<Student> findAllStudents() {
        List<Student> students = new ArrayList<>();
        String sql = "SELECT s.*, c.class_name FROM students s LEFT JOIN class c ON s.class_id = c.id";
        try (PreparedStatement stmt = connection.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                students.add(mapResultSet(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return students;
    }

    // Update an existing student's information in the database
    @Override
    public void updateStudent(Student student) {
        String sql = "UPDATE students SET first_name = ?, last_name = ?, age = ?, average_grade = ? WHERE id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, student.getFirstName());
            stmt.setString(2, student.getLastName());
            stmt.setInt(3, student.getAge());
            stmt.setDouble(4, student.getAverageGrade());
            stmt.setLong(5, student.getStudentId());
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Delete a student from the database by their ID
    @Override
    public void deleteStudent(Long id) {
        String sql = "DELETE FROM students WHERE id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setLong(1, id);
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Search for students based on a keyword with pagination support
    @Override
    public List<Student> searchGeneral(String keyword, int limit, int offset) {
        List<Student> students = new ArrayList<>();
        String like = "%" + keyword.toLowerCase() + "%";
        String sql = """
            SELECT s.*, c.class_name
            FROM students s
            LEFT JOIN class c ON s.class_id = c.id
            WHERE CAST(s.id AS TEXT) LIKE ?
               OR LOWER(s.first_name) LIKE ?
               OR LOWER(s.last_name) LIKE ?
               OR CAST(s.age AS TEXT) LIKE ?
            ORDER BY s.id
            LIMIT ? OFFSET ?
        """;
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            for (int i = 1; i <= 4; i++) {
                stmt.setString(i, like);
            }
            stmt.setInt(5, limit);
            stmt.setInt(6, offset);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                students.add(mapResultSet(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return students;
    }

    // Count the number of students matching a keyword for pagination purposes
    @Override
    public long countSearchGeneral(String keyword) {
        String like = "%" + keyword.toLowerCase() + "%";
        String sql = """
            SELECT COUNT(*)
            FROM students s
            LEFT JOIN class c ON s.class_id = c.id
            WHERE CAST(s.id AS TEXT) LIKE ?
               OR LOWER(s.first_name) LIKE ?
               OR LOWER(s.last_name) LIKE ?
               OR CAST(s.age AS TEXT) LIKE ?
        """;
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            for (int i = 1; i <= 4; i++) {
                stmt.setString(i, like);
            }
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) return rs.getLong(1);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }

    // Helper method to map a ResultSet to a Student object
    @Override
    protected Student mapResultSet(ResultSet rs) throws SQLException {
        Student student = new Student();
        student.setStudentId(rs.getLong("id"));
        student.setFirstName(rs.getString("first_name"));
        student.setLastName(rs.getString("last_name"));
        student.setAge(rs.getInt("age"));
        student.setClassName(rs.getString("class_name"));
        student.setAverageGrade(rs.getDouble("average_grade"));
        return student;
    }
}
