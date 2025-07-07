package com.studentmanagement.model;

//minimum version to compile without errors
public class Student{

    private Long studentId;
    private String firstName;
    private String lastName;
    private int age;
    private String className;

    public Student (){
        //default constructor
    }

    public Long getStudentId(){
        return studentId;
    }

    public String getFirstName(){
        return firstName;
    }

    public void setFirstName(String firstName){
        this.firstName = firstName;
    }

    public String getLastName(){
        return lastName;
    }

    public void setLastName(String lastName){
        this.lastName = lastName;
    }

    public int getAge(){
        return age;
    }

    public void setAge(int age){
        this.age = age;
    }

    public String getClassName(){
        return className;
    }

    public void setClassName(String className){
        this.className = className;
    }

    public String getStudentClassName(){
        return className;
    }

    public String getFullName(){
        return firstName + " " + lastName;
    }

    //Calculate student average grade 
    //this is a real time method that should be implemented 
    //with GradeDAO but as here it is just to compile without errors..
    public double getAverageGrade(){
        return 0.0; //default value
    }

    //this method is here for compatibility with interface
    //but do not stock any value - don't do nothing because average is
    //calculated in real time
    public void setAverageGrade(double averageGrade){

    }

}