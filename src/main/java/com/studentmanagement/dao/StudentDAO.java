package com.studentmanagement.dao;

import com.studentmanagement.model.Student;
import java.util.List;

public interface StudentDAO {

    // Save a student to the database
    void saveStudent(Student student);

    // Find a student by their ID
    Student findStudentById(Long id);

    // Retrieve all students from the database
    List<Student> findAllStudents();

    // Update an existing student's information in the database
    void updateStudent(Student student);

    // Delete a student from the database by their ID
    void deleteStudent(Long id);

    // Search for students based on a keyword with pagination support
    List<Student> searchGeneral(String keyword, int limit, int offset);

    // Count the number of students matching a keyword for pagination purposes
    long countSearchGeneral(String keyword);
}
