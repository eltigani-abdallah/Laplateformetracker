package com.studentmanagement.service;

import com.studentmanagement.model.Student;
import com.studentmanagement.utils.SearchCriteria;
import java.util.List;
import java.util.ArrayList;

//Minimum version just to compile without errors
public class StudentService{

    public void createStudent(Student student){

    }

    public void updateStudent(Student student){

    }

    public List<Student> searchStudents(SearchCriteria criteria){
        return new ArrayList<>();
    }
    
    public int getTotalStudents(SearchCriteria criteria){
        return 0;
    }
}