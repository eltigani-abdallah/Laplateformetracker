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
