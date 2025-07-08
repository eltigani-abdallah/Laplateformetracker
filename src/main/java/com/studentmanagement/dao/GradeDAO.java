package com.studentmanagement.dao;

import com.studentmanagement.model.Grade;
import com.studentmanagement.model.SubjectResult;
import com.studentmanagement.util.SearchCriteria;
import java.util.List;

public interface GradeDAO {

    // Save a grade
    void saveGrade(Grade grade);

    // Update a grade by ID
    void updateGrade(Grade grade);

    // Delete a grade by ID
    void deleteGrade(Long gradeId);

    // Save a coefficient
    void saveCoefficient(double coefficient);

    // Update a coefficient by ID
    void updateCoefficient(Long coefficientId, double newCoefficient);

    // Get the minimum average grade by subject
    double getMinAverageBySubject(String subjectName);

    // Get the maximum average grade by subject
    double getMaximumAverageBySubject(String subjectName);

    // Search by subject with pagination
    List<SubjectResult> searchBySubject(Long studentId, SearchCriteria criteria);

    // Count the number of subjects for pagination
    Long countBySubject(Long studentId, String subjectNameFilter);

    // Calculate the weighted average grade of a student
    double calculateWeightedAverageGrade(Long studentId);

    // Get the total number of grades
    Long count();
}
