package com.studentmanagement.service;

import java.util.List;
import java.util.Map;

public class StatisticsService{
    StudentDAO StudentDAO= new StudentDAO();

    GradeDAO gradeDAO=new GradeDAO;

    public StatisticsService(StudentDAO studentDAO, GradeDAO gradeDAO){

    }

    double calculateClassAverageBySubject(String subject){

    }

    Map<String,Long> getStudentCountByAge(){

    }

    Map<String, List<Double>> getGradeDistributionBySubject(String subject){

    }

    List<Student> getTopPerformers(int grade){

    }

    Map<String, Object> getStudentStatustics(){

    }

    double getMinAverageBySubject(String subject){

    }

    double getMaximumAverageBySubject(String subject){

    }

    double calculateWeightedAverageGrade(){

    }
}