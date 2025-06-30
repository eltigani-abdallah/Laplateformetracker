# Laplateformetracker - Student Management System

## Description

This project is a student management system developed in Java with a PostgreSQL database. It allows managing student information (name, surname, age, grades ...) with advanced features for search, sorting, statistics, and security.

## Features

### Basic Features
- **Add a student**: Input and save information
- **Edit a student**: Search by ID and modify data
- **Delete a student**: Remove by ID
- **Display all students**: Complete list of students
- **Search for a student**: Lookup by ID

### Advanced Features
- **Sort students**: By name, surname, age ect...
- **Advanced search**: Muliple criteria (age, average grade, ect...)
- **Statistics**: Class average, age distribution ...
- **Import/Export**: Formats CSV
- **Pagination**: Display in batches
- **Authentification**: Secured by login/password
- **Registration**: to add new users
- **Export Results**: CSV, PDF
- **Automatic Backup**: Protection against data loss

## Project Architecture
This application follows a classic layered architecture with the MVC (Model-View-Controller) pattern.
Here are the main layers:

### 1. **Model Layer (Data Model)**
Classes that represent business entities, and contains attributes, constructors, getters/setters, and utility methods.

- 'Student.java':
    + Student()-> Default constructor  
    + Student() -> Constructor with basic parameters  
    + getId() -> Returns the student's ID  
    + setId() -> Sets the student's ID  
    + getFirstName() -> Returns the student's first name  
    + setFirstName() -> Sets the student's first name  
    + getLastName() -> Returns the student's last name  
    + setLastName() -> Sets the student's last name  
    + getAge() -> Returns the student's age  
    + setAge() -> Sets the student's age  
    + getFullName -> Returns the full name (first name + last name) 
    + getStudentClass -> Returns the student's class by ID 
    + toString -> Returns a textual representation of the student 

- 'Grade.java': 
    + Grade()-> Default constructor  
    + Grade() -> Constructor with student ID, school subject, grade value
    + getId() -> Returns the unique grade ID
    + getSubject() -> Returns the school subject of the grade
    + setSubject() -> Sets the school subject of the grade
    + getValue() -> Returns the numeric value of the grade
    + setValue() -> Sets the numeric value of the grade
    + getCoefficient() -> Returns the grade coefficient
    + setCoefficient() -> Sets the grade coefficient
    + getDate() -> Returns the date of the grade
    + setDate() -> Sets the date of the grade
    + getComment() -> Returns the teacher comment
    + setComment() -> Sets the teacher comment
    + getGrades() -> Returns the list of the student's grades by school subjects 
    + getWeightedGradeValue() -> Calculates and returns the ponderated value of the grade (value * coefficient)
    + addGrade() -> Adds a grade to the list of grades by school subjects  
    + calculateAverageGrade -> Calculates and returns the average of the grades by school subjects
    + getMinAverageGrade -> Returns the minimum average grade by school subjects for the student class
    + getMaxAverageGrade -> Returns the maximum average grade by school subjects for the student class 
    + toString() -> Returns a textual representation of the grade

- 'User.java': 
    + User() -> Default constructor  
    + User() -> Constructor with username and password
    + getId() -> Returns the unique Id of the user
    + getUsername() -> Returns the username
    + setUsername() -> Sets the username
    + getPasswordHash() -> Returns the password hash ! Must be protected
    + setPassword() -> Sets the password
    + getEmail() -> Returns the user email
    + setEmail() -> Sets the user email
    + toString() -> Returns a textual representation of the user

### 2. **DAO (Data Access Object) Layer**
The DAO pattern separates data access logic from business logic.
Advantages of the DAO Pattern:
- **Separation of concerns**: Data access logic is isolated
- **Testability**: It is easy to create mock implementations for testing
- **Flexibility**: Changing the database only requires changing DAO implementation.

- 'StudentDAO.java': 
    + save() -> Save a student to the database
    + findById() -> Finds a student by their unique ID
    + findAll() -> Retrieves all students from the database
    + update() -> Updates an existing student's information
    + delete() -> Deletes a student by their ID
    + findByName() -> Finds students by their name
    + findByAge() -> Finds students within a specific age
    + findBySchoolClass -> Finds students by school class
    + count() -> Returns the total number of students 

