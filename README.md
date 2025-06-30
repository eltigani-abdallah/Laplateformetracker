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
    + getWeightedGradeValue() -> Calculates and returns the ponderated value of the grade (value * coefficient)
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

- 'StudentDAO.java': <<interface>> 
    + save() -> Save a student to the database
    + findById() -> Finds a student by their unique ID
    + findAll() -> Retrieves all students from the database
    + update() -> Updates an existing student's information
    + delete() -> Deletes a student by their ID
    + findByName() -> Finds students by their name
    + findByAge() -> Finds students within a specific age
    + findBySchoolClass -> Finds students by school class
    + count() -> Returns the total number of students 
                    ↑
                implements
                    |
- 'StudentDAOImpl.java':
    - connection -> Database connection object
    - dbConnection -> Database connection manager
    + StudentDAOImpl() -> Constructor with dependency injection
    + save() -> Implements student saving to database
    + findById() -> Implements finding students by ID
    + findAll() -> Implements retrieving all students
    + update() -> Implements student data updating
    + delete() -> Implements student deletion by ID
    + findByName() -> Implements finding students by name
    + findByAge() -> Implements finding students by age
    + findBySchoolClass -> Implements finding students by school class
    + count() -> Implements countinf total students
    + mapResultSetToStudent -> Convert database ResultSet to Student

    NOTE: 
    - StudentDAO is an interface that defines the contract with all CRUD operations (Create, Read, Update, Delete) and specialized search methods to manipulate student data.

    - StudentDAOImpl is the concrete class that implements this interface. It contains:
    + Required dependencies (Connection and DatabaseConnection)
    + The actual implementation of all methods defined in the interface
    + A private utility method mapResultSetToStudent for data conversion: 
    When an SQL query is executed, it returns data in the form of a ResultSet, which is essentially a result table. This method performs the "mapping" (transformation) between:
    1. ResultSet columns (e.g., id, name, age, grade...)
    2. The corresponding properties of a new Student object
    This method is used internally by other methods like findById(), findAll(), etc., to convert database results into usable Java objects.

    Example :

```java
    // In StudentDAO.java
     public interface StudentDAO {
    Student findById(Long id);
    // other methods...
    }

    // In StudentDAOImpl.java
    public Student findById(Long id) {
        try {
            String sql = "SELECT * FROM students WHERE id = ?";
            PreparedStatement stmt = connection.prepareStatement(sql);
            stmt.setLong(1, id);
            ResultSet rs = stmt.executeQuery();
            
            if (rs.next()) {
                return mapResultSetToStudent(rs);
            }
            return null;
            
        } catch (SQLException e) {
            return null;
        }
    }
```
- 'GradeDAO.java': <<interface>> 
    + saveGrade() -> Saves a grade to the database
    + updateGrade() -> Updates an existing grade by its ID
    + deleteGrade() -> Delete an existing gade by its ID
    + saveComment() -> Saves a teacher's comment for his subject
    + updateComment() -> Updates an existing comment
    + deleteComment() -> Deletes an existing comment
    + saveCoefficient() -> Saves a grade coefficient
    + updateCoefficient() -> Updates an existing coefficient
    + findGradeById() -> Finds a grade by its unique ID
    + findAllStudentSubject() -> Retrieves all the student's subject
    + findByStudentID() -> Finds grades by student ID
    + findGradesBySchoolSubject() -> Finds grades by subject and student ID
    + findGradesByDate() -> Finds grades by date
    + getMinAverageBySubject() -> Finds minumum average by subject in the student class list
    + getMaximumAverageBySubject() -> Finds maximum average by subject in the student class list
    + calculateWeightedAverageGrade -> Calculates and returns the pondereate (coeff) average of the grades by school subjects
    + count() -> Returns the total number of grades
                        ↑
                    implements
                        |
- 'GradeDAOImpl.java':
    - connection -> Database connection object
    - dbConnection -> Database connection manager
    + GradeDAOImpl() -> Constructor with dependency injection
    + saveGrade() -> Implements grade saving to database
    + updateGrade() -> Implements grade updating by ID
    + deleteGrade() -> Implements grade deletion by ID
    + saveComment() -> Implements comment saving for a subject
    + updateComment() -> Implement comment updating
    + deleteComment() -> Implement comment deletion
    + saveCoefficient() -> Implements coefficient saving
    + updateCoefficient() -> Implements coefficient updating
    + findGradeById() -> Implements finding grade by ID
    + findAllStudentSubject() -> Implements retrieving all student's subjects
    + findGradeByStudentId() -> Implements finding grades by student ID
    + findGradesBySchoolSubject() -> Implements finding grades by subject and student ID
    + findGradesByDate() -> Implements finding grades by date
    + getMinAverageBySubject() -> Implements finding minimum average by subject
    + getMaximumAverageBySubject() -> Implements finding maximum average by subject
    + calculateWeightedAverageGrade() -> Implements weighted average calculation by subject
    + mapResultSetToGrade() -> Convert database ResultSet to Grade

- 'UserDAO.java': <<interface>>
    + saveUser() -> Saves a user to the database 
    + findUserByUsername () -> Finds a user by username
    + authenticateUser() -> Checks if username/password combination is valid
                        ↑
                    implements
                        |
- 'UserDAOImpl.java':
    - connection -> Database connection object
    - dbConnection -> Database connection manager
    + UserDAOImpl() -> Implements finding user by username
    + saveUser() -> Implements user saving to database
    + findUserByUsername -> Implements findinf user by username
    + authenticateUser() -> Implements login authentication
    + mapResultSetToUser() -> Convert database ResultSet to User

### 3. **Database**
 - 'DatabaseConnection.java':
    - url -> Database UrL string
    - username -> Database username
    - password -> Database password
    - connection -> Active database connection object
    + DatabaseConnection() -> Default constructor
    + connect() -> Establishes database connection
    + disconnect() -> Closes database connection
    + getConnection() -> Returns active connection
    + isConnected() -> Checks if connection is active
    + executeQuery() -> Executes SELECT query
    + executeUpdate() -> Executes INSERT/UPDATE/DELETE query
    + executeSafeQuery() -> Executes SQL query with parameters for security

- 'DatabaseConfig.java':
    - properties -> Properties object for configuration
    - configFile -> Configuration file path
    + DatabaseConfig() -> Default constructor, loads configuration
    + loadConfig() -> Loads configuration from properties file
    + getDatabaseUrl() -> Returns database URL
    + getDatabaseUsername() -> Returns database username
    + getDatabasePassword() -> Returns database password
