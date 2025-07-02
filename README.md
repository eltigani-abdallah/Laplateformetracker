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
- **Undo operations**: Undo/redo functionality for CRUD actions

## Project Architecture
This application follows a classic layered architecture with the MVC (Model-View-Controller) pattern.
Here are the main layers:

### 1. **Model Layer (Data Model)**
* * *
Classes that represent business entities, and contains attributes, constructors, getters/setters, and utility methods.

- **Student.java**:
    - `Student()`-> Default constructor  
    - `Student()` -> Constructor with basic parameters  
    - `getStudentId()` -> Returns the student's ID  
    - `getFirstName()` -> Returns the student's first name  
    - `setFirstName()` -> Sets the student's first name  
    - `getLastName()` -> Returns the student's last name  
    - `setLastName()` -> Sets the student's last name  
    - `getAge()` -> Returns the student's age  
    - `setAge()` -> Sets the student's age  
    - `getAverageGrade()` -> Returns the student's average grade
    - `setAverageGrade()` -> Sets the student's average grade
    - `getFullName()` -> Returns the full name (first name - last name) 
    - `getStudentClassName()` -> Returns the class name
    - `setClassName()` -> Sets the class name (in db should be class_name VARCHAR(10) to stock "5B", "T1" ect...)
    - `toString()` -> Returns a textual representation of the student 

```
+-------------------------------------------+
|                 Student                   |
+-------------------------------------------+
| - studentId: Long                         |
| - firstName: String                       |
| - lastName: String                        |
| - age: int                                |
| - className: String                       |
+-------------------------------------------+
| - Student()                               |
| - Student(String, String, int)            |
| - getStudentId(): Long                    |
| - getFirstName(): String                  |
| - setFirstName(String): void              |
| - getLastName(): String                   |
| - setLastName(String): void               |
| - getAverageGrade(): double               |
| - setAverageGrade(double): void           |
| - getAge(): int                           |
| - setAge(int): void                       |
| - getFullName(): String                   |
| - getStudentClassName(): String           |
| - setClassName(String): void              |
| - toString(): String                      |
+-------------------------------------------+
```

- **Grade.java**: 
    - `Grade()`-> Default constructor  
    - `Grade()` -> Constructor with student ID, school subject, grade value
    - `getGradeId()` -> Returns the unique grade ID
    - `getStudentId()` -> Returns the student ID
    - `setStudentId()` -> Sets the student ID
    - `getSubject()` -> Returns the school subject of the grade
    - `setSubject()` -> Sets the school subject of the grade
    - `getValue()` -> Returns the numeric value of the grade
    - `setValue()` -> Sets the numeric value of the grade
    - `getCoefficient()` -> Returns the grade coefficient
    - `setCoefficient()` -> Sets the grade coefficient
    - `getDate()` -> Returns the date of the grade
    - `setDate()` -> Sets the date of the grade
    - `getWeightedGradeValue()` -> Calculates and returns the weighted value of the grade (value * coefficient)
    - `toString()` -> Returns a textual representation of the grade

```
+-------------------------------------------+
|                  Grade                    |
+-------------------------------------------+
| - id: Long                                |
| - studentId: Long                         |
| - subject: String                         |
| - value: double                           |
| - coefficient: double                     |
| - date: Date                              |
+-------------------------------------------+
| - Grade()                                 |
| - Grade(Long, String, double, double)     |
| - getGradeId(): Long                      |
| - getStudentId(): Long                    |
| - setStudentId(Long): void                |
| - getSubject(): String                    |
| - setSubject(String): void                |
| - getValue(): double                      |
| - setValue(double): void                  |
| - getCoefficient(): double                |
| - setCoefficient(double): void            |
| - getDate(): Date                         |
| - setDate(Date): void                     |
| - getWeightedGradeValue(): double         |
| - toString(): String                      |
+-------------------------------------------+
```

- **SubjectComment.java**:
    - `getId()` -> Returns unique comment ID
    - `getStudentId()` -> Returns student ID
    - `getSubject()` -> Returns school subject
    - `getComment()` -> Returns teacher's trimester comment
    - `setComment()` -> Sets teacher's comment

```
+-------------------------------------------+
|            SubjectComment                 |
+-------------------------------------------+
| - id: Long                                |
| - studentId: Long                         |
| - subject: String                         |
| - comment: String                         |
+-------------------------------------------+
| + SubjectComment()                        |
| + getId(): Long                           |
| + getStudentId(): Long                    |
| + getSubject(): String                    |
| + getComment(): String                    |
| + setComment(String): void                |
+-------------------------------------------+
```

- **User.java**: 
    - `User()` -> Default constructor  
    - `User()` -> Constructor with username and password
    - `getUserId()` -> Returns the unique Id of the user
    - `getUsername()` -> Returns the username
    - `setUsername()` -> Sets the username
    - `getPasswordHash()` -> Returns the password hash (for DAO access only)
    - `setPasswordHash()` -> Sets the password hash (for DAO access only)
    - `getEmail()` -> Returns the user email
    - `setEmail()` -> Sets the user email
    - `toString()` -> Returns a textual representation of the user

```
+-------------------------------------------+
|                  User                     |
+-------------------------------------------+
| - id: Long                                |
| - username: String                        |
| - passwordHash: String                    |
| - email: String                           |
+-------------------------------------------+
| + User()                                  |
| + User(String, String)                    |
| + getUserId(): Long                       |
| + getUsername(): String                   |
| + setUsername(String): void               |
| + getPasswordHash(): String               |
| + setPasswordHash(String): void           |
| + getEmail(): String                      |
| + setEmail(String): void                  |
| + toString(): String                      |
+-------------------------------------------+
```

### 2. **DAO (Data Access Object) Layer**
* * *

The DAO pattern separates data access logic from business logic.
Advantages of the DAO Pattern:
- **Separation of concerns**: Data access logic is isolated
- **Testability**: It is easy to create mock implementations for testing
- **Flexibility**: Changing the database only requires changing DAO implementation.

- **BaseDAO.java**: Abstract base class for all DAOs
    - `connection` -> Database connection object
    - `BaseDAO(Connection)` -> Constructor with connection injection
    - `mapResultSet(ResultSet)` -> Abstract method to convert ResultSet to entity
    - `count(String)` -> Generic count method for any table
    - `closeResources(ResultSet, PreparedStatement)` -> Utility to close database resources

```java
public abstract class BaseDAO<T> {
    protected Connection connection;
    
    public BaseDAO(Connection connection) {
        this.connection = connection;
    }
    
    protected abstract T mapResultSet(ResultSet rs) throws SQLException;
    protected int count(String tableName) { ... }
    protected void closeResources(ResultSet rs, PreparedStatement stmt) { ... }
}
```
```
+------------------------------------------------------+
|                 BaseDAO                              |
+------------------------------------------------------+
| - connection: Connection                             |
+------------------------------------------------------+
| + BaseDAO(Connection)                                |
| + mapResultSet(ResultSet): T                         |
| + count(String): int                                 |
| + closeResources(ResultSet, PreparedStatement): void |
+------------------------------------------------------+

```
> The `BaseDAO` class is an abstract template for all Data Access Objects (DAOs) in the project. It includes:
> 
> - **Connection**: A database connection object for performing data operations.
> - **Constructor**: Initializes the DAO with a specific database connection.
> - **mapResultSet**: An abstract method that subclasses must implement to convert a `ResultSet` into an entity of type `T`.
> - **count**: A generic method for counting records in any table.
> - **closeResources**: A utility method for safely closing database resources, preventing memory leaks.
> 
> When creating a new DAO for a specific entity (like User or Product), extend the `BaseDAO` class and implement the `mapResultSet` method to define the conversion from `ResultSet` to the desired entity. This structure ensures consistent and efficient data access across the application.

- **StudentDAO.java**: interface
    - `save()` -> Save a student to the database
    - `findStudentById()` -> Finds a student by their unique ID
    - `findAllStudent()` -> Retrieves all students from the database
    - `updateStudent()` -> Updates an existing student's information
    - `deleteStudent()` -> Deletes a student by their ID
    - `searchGeneral()` -> General research with pagination
    - `countSearchGeneral()` -> count results for general research for pagination

    NOTE: 
    - StudentDAO is an interface that defines the contract with all CRUD operations (Create, Read, Update, Delete) and specialized search methods to manipulate student data.

    ```java
    // example
     public interface StudentDAO {
    Student findById(Long id);
    // other methods...
    }
     ```
```
+------------------------------------------------+
|               StudentDAO                       |
+------------------------------------------------+
| + save(Student): void                          |
| + findStudentById(Long): Student               |
| + findAllStudents(): List<Student>             |
| + updateStudent(Student): void                 |
| + deleteStudent(Long): void                    |
| + searchGeneral(SearchCriteria): List<Student> |
| + countSearchGeneral(SearchCriteria): Long     |
+------------------------------------------------+
                    ↑
                implements
                    |
+-------------------------------------------------+
|                  StudentDAOImpl                 |
+-------------------------------------------------+
| - connection: Connection                        |
| - dbConnection: DatabaseConnection              |
+-------------------------------------------------+
| + StudentDAOImpl(Connection)                    |
| + saveStudent(Student): void                    |
| + findStudentById(Long): Student                |
| + findAllStudents(): List<Student>              |
| + updateStudent(Student): void                  |
| + deleteStudent(Long): void                     |
| + searchGeneral(SearchCriteria): List<Student>  |
| + countSearchGeneral(SearchCriteria): Long      |
| + count(): Long                                 |
| - mapResultSetToStudent(ResultSet): Student     |
+-------------------------------------------------+
|                 <<extends>>                     |
|              BaseDAO<Student>                   |
+-------------------------------------------------+
```
- **StudentDAOImpl.java**: `extends BaseDAO<Student>`
    - `StudentDAOImpl(Connection)` -> Constructor with connection injection
    - `saveStudent()` -> Implements student saving to database
    - `findStudentById()` -> Implements finding students by ID
    - `findAllStudent()` -> Implements retrieving all students
    - `updateStudent()` -> Implements student data updating
    - `deleteStudent()` -> Implements student deletion by ID
    - `searchGeneral()` -> Implement general search across all fields with pagination
    - `countSearchGeneral()` -> Implements counts the results of the general search
    - `mapResultSetTo(ResultSet)` -> Convert database ResultSet to Student object

SQL requests example:

```sql
-- Example of searchGeneral() request
SELECT * FROM students 
WHERE CAST(id AS TEXT) LIKE ? 
   OR LOWER(first_name) LIKE LOWER(?) 
   OR LOWER(last_name) LIKE LOWER(?)
   OR CAST(age AS TEXT) LIKE ?
   OR CAST(class_name AS TEXT) LIKE ?
ORDER BY id 
LIMIT ? OFFSET ?

-- Example of countSearchGeneral() request
SELECT COUNT(*) FROM students 
WHERE (CAST(id AS TEXT) LIKE ? 
    OR LOWER(first_name) LIKE LOWER(?) 
    OR LOWER(last_name) LIKE LOWER(?) 
    OR CAST(age AS TEXT) LIKE ? 
    OR CAST(class_name AS TEXT) LIKE ?)

```

Snipset example in StudentDAOImpl:

```java
    // Example findStudentById
    public Student findStudentById(Long id) {
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
- **GradeDAO.java**: interface
    - `saveGrade()` -> Saves a grade to the database
    - `updateGrade()` -> Updates an existing grade by its ID
    - `deleteGrade()` -> Delete an existing gade by its ID
    - `saveCoefficient()` -> Saves a grade coefficient
    - `updateCoefficient()` -> Updates an existing coefficient
    - `searchBySubject()` -> Complete research by subject with pagination
    - `countBySubject()` -> Count for pagination
    - `count()` -> Returns the total number of grades

 ```   
+---------------------------------------------------------------+
|                    GradeDAO                                   |
+---------------------------------------------------------------+
| + saveGrade(Grade): void                                      |
| + updateGrade(Grade): void                                    |
| + deleteGrade(Long): void                                     |
| + saveCoefficient(double): void                               |
| + updateCoefficient(Long, double): void                       |
| + getMinAverageBySubject(String): double                      |
| + getMaximumAverageBySubject(String): double                  |
| + searchBySubject(Long, SearchCriteria): List<SubjectResult>  |
| + countBySubject(Long, String): Long                          |
| + calculateWeightedAverageGrade(Long): double                 |
| + count(): Long                                               |
+---------------------------------------------------------------+
|                  <<interface>>                                |
|                    GradeDAO                                   |
+---------------------------------------------------------------+
                        ↑
                    implements
                        |
+---------------------------------------------------------------+
|                  GradeDAOImpl                                 |
+---------------------------------------------------------------+
| - connection: Connection                                      |
| - dbConnection: DatabaseConnection                            |
+---------------------------------------------------------------+
| + GradeDAOImpl(Connection)                                    |
| + saveGrade(Grade): void                                      |
| + updateGrade(Grade): void                                    |
| + deleteGrade(Long): void                                     |
| + saveCoefficient(double): void                               |
| + updateCoefficient(Long, double): void                       |
| + getMinAverageBySubject(String): double                      |
| + getMaximumAverageBySubject(String): double                  |
| + searchBySubject(Long, SearchCriteria): List<SubjectResult>  |
| + countBySubject(Long, String): Long                          |
| + calculateWeightedAverageGrade(Long): double                 |
| - mapResultSetToGrade(ResultSet): Grade                       |
+---------------------------------------------------------------+
|                  <<extends>>                                  |
|                    BaseDAO<Grade>                             |
+---------------------------------------------------------------+
```

- **GradeDAOImpl.java**: `extends BaseDAO<Grade>`
    - `GradeDAOImpl(Connection)` -> Constructor with connection injection
    - `saveGrade()` -> Implements grade saving to database
    - `updateGrade()` -> Implements grade updating by ID
    - `deleteGrade()` -> Implements grade deletion by ID
    - `saveCoefficient()` -> Implements coefficient saving
    - `updateCoefficient()` -> Implements coefficient updating
    - `searchBySubject()` -> Complex request with GROUP BY to returns all the info in one request (subject - grades - min average grade, max average grade, student average, comment)
    - `countBySubject()` ->  Implements subject count
    - `mapResultSet(ResultSet)` -> Convert database ResultSet to Grade

```sql
-- Example of countBySubject() request
    SELECT COUNT(DISTINCT subject) FROM grades 
    WHERE student_id = ? AND LOWER(subject) LIKE LOWER(?)

-- Example of searchBySubject() request 
SELECT 
    g.subject,
    GROUP_CONCAT(g.value, ', ') as grades,
    (SELECT calculateWeightedAverageGrade(g.student_id, g.subject)) as student_average,
    (SELECT MIN(calculateWeightedAverageGrade(student_id, g.subject)) 
     FROM (SELECT DISTINCT student_id FROM grades WHERE subject = g.subject) s) as class_min_average,
    (SELECT MAX(calculateWeightedAverageGrade(student_id, g.subject)) 
     FROM (SELECT DISTINCT student_id FROM grades WHERE subject = g.subject) s) as class_max_average,
    sc.comment as teacher_comment
FROM grades g
LEFT JOIN subject_comments sc ON sc.student_id = g.student_id AND sc.subject = g.subject
WHERE g.student_id = ? AND LOWER(g.subject) LIKE LOWER(?)
GROUP BY g.subject, sc.comment
ORDER BY g.subject
LIMIT ? OFFSET ?
```

- **SubjectCommentDAO.java**:  interface 
    - `saveComment()` -> Saves a comment for a subject
    - `updateComment()` -> Updates an existing comment
    - `deleteComment()` -> Deletes a comment
    - `findCommentsByStudentAndSubject()` -> Finds comments for student/subject

 ```       
+-----------------------------------------------------------------------+
|                SubjectCommentDAO                                      |
+-----------------------------------------------------------------------+
| + saveComment(SubjectComment): void                                   |
| + updateComment(SubjectComment): void                                 |
| + deleteComment(Long): void                                           |
| + findCommentsByStudentAndSubject(Long, String): List<SubjectComment> |
+-----------------------------------------------------------------------+
|                  <<interface>>                                        |
|                SubjectCommentDAO                                      |
+-----------------------------------------------------------------------+
                        ↑
                    implements
                        |
+-----------------------------------------------------------------------+
|              SubjectCommentDAOImpl                                    |
+-----------------------------------------------------------------------+
| - connection: Connection                                              |
| - dbConnection: DatabaseConnection                                    |
+-----------------------------------------------------------------------+
| + SubjectCommentDAOImpl(Connection)                                   |
| + saveComment(SubjectComment): void                                   |
| + updateComment(SubjectComment): void                                 |
| + deleteComment(Long): void                                           |
| + findCommentsByStudentAndSubject(Long, String): List<SubjectComment> |
| - mapResultSetToComment(ResultSet): SubjectComment                    |
+-----------------------------------------------------------------------+
|                  <<extends>>                                          |
|                BaseDAO<SubjectComment>                                |
+-----------------------------------------------------------------------+
 ```   

- **SubjectCommentDAOImpl.java**: `extends BaseDAO<SubjectComment>`
    - `SubjectCommentDAOImpl(Connection)` -> Constructor with connection injection
    - `saveComment()` -> Implements comment saving to database
    - `updateComment()` -> Implements comment updating
    - `deleteComment()` -> Implements comment deletion
    - `findCommentsByStudentAndSubject()` -> Implements finding comments by student and subject
    - `mapResultSet(ResultSet)` -> Convert database ResultSet to SubjectComment

- **UserDAO.java**: interface
    - `saveUser()` -> Saves a user to the database 
    - `findUserByUsername ()` -> Finds a user by username
    - `authenticateUser()` -> Checks if username/password combination is valid
```
+-------------------------------------------------+
|                    UserDAO                      |
+-------------------------------------------------+
| + saveUser(User): void                          |
| + findUserByUsername(String): User              |
| + authenticateUser(String, String): boolean     |
+-------------------------------------------------+
|                  <<interface>>                  |
|                    UserDAO                      |
+-------------------------------------------------+
                        ↑
                    implements
                        |
+-------------------------------------------------+
|                  UserDAOImpl                    |
+-------------------------------------------------+
| - connection: Connection                        |
| - dbConnection: DatabaseConnection              |
+-------------------------------------------------+
| + UserDAOImpl(Connection)                       |
| + saveUser(User): void                          |
| + findUserByUsername(String): User              |
| + authenticateUser(String, String): boolean     |
| - mapResultSetToUser(ResultSet): User           |
+-------------------------------------------------+
|                  <<extends>>                    |
|                    BaseDAO<User>                |
+-------------------------------------------------+
```
- **UserDAOImpl.java**:  `extends BaseDAO<User>`
    - `UserDAOImpl(Connection)` -> Constructor with connection injection
    - `saveUser()` -> Implements user saving to database
    - `findUserByUsername` -> Implements findinf user by username
    - `authenticateUser()` -> Implements login authentication
    - `mapResultSet(ResultSet)` -> Convert database ResultSet to User

### 3. **Database**
* * *

 - **DatabaseConnection.java**:
    - `url` -> Database UrL string
    - `username` -> Database username
    - `password` -> Database password
    - `connection` -> Active database connection object
    - `DatabaseConnection()` -> Default constructor
    - `connect()` -> Establishes database connection
    - `disconnect()` -> Closes database connection
    - `getConnection()` -> Returns active connection
    - `isConnected()` -> Checks if connection is active
    - `executeQuery()` -> Executes SELECT query
    - `executeUpdate()` -> Executes INSERT/UPDATE/DELETE query
    - `executeSafeQuery()` -> Executes SQL query with parameters for security
    - `closeResources()`
```
+-------------------------------------------------+
|                DatabaseConnection               |
+-------------------------------------------------+
| - url: String                                   |
| - username: String                              |
| - password: String                              |
| - connection: Connection                        |
+-------------------------------------------------+
| + DatabaseConnection()                          |
| + connect(): void                               |
| + disconnect(): void                            |
| + getConnection(): Connection                   |
| + isConnected(): boolean                        |
| + executeQuery(String): ResultSet               |
| + executeUpdate(String): void                   |
| + executeSafeQuery(String, List<Object>): void  |
| + closeResources(): void                        |
+-------------------------------------------------+

```
- **DatabaseConfig.java**:
    - `properties` -> Properties object for configuration
    - `configFile` -> Configuration file path
    - `DatabaseConfig()` -> Default constructor, loads configuration
    - `loadConfig()` -> Loads configuration from properties file
    - `getDatabaseUrl()` -> Returns database URL
    - `getDatabaseUsername()` -> Returns database username
    - `getDatabasePassword()` -> Returns database password

```
+-------------------------------------------------+
|                  DatabaseConfig                 |
+-------------------------------------------------+
| - properties: Properties                        |
| - configFile: String                            |
+-------------------------------------------------+
| + DatabaseConfig()                              |
| + loadConfig(): void                            |
| + getDatabaseUrl(): String                      |
| + getDatabaseUsername(): String                 |
| + getDatabasePassword(): String                 |
+-------------------------------------------------+
```
### 4. **Service Layer (Business Logic)**
* * *
The Service layer contains business logic and orchestrates calls to the DAOs

- **StudentService.java**:
    - `studentDAO` -> StudentDAO instance
    - `validator` -> InputValidator instance
    - `StudentService()` -> Constructor with DAO injection
    - `createStudent()` -> Creates new student with validation
    - `getStudentByID()` -> Retrieves student by ID
    - `getAllStudents()` -> Retrieves all students
    - `updateStudent()` -> Updates existing student
    - `deleteStudent()` -> Deletes student by ID
    - `searchStudents()`  -> Returns paginated student list - use StudentDAO.searchGeneral() with SearchCriteria and pagination
```
+-------------------------------------------------+
|                  StudentService                 |
+-------------------------------------------------+
| - studentDAO: StudentDAO                        |
| - validator: InputValidator                     |
+-------------------------------------------------+
| + StudentService(StudentDAO, InputValidator)    |
| + createStudent(Student): void                  |
| + getStudentByID(Long): Student                 |
| + getAllStudents(): List<Student>               |
| + updateStudent(Student): void                  |
| + deleteStudent(Long): void                     |
| + searchStudents(SearchCriteria): List<Student> |
+-------------------------------------------------+
```
- **GradeService.java**:
    - `gradeDAO` -> GradeDAO instance
    - `GradeService()` -> Constructeur
    - `searchBySubject()` -> Use GradeDAO.searchBySubject() with SearchCriteria and pagination

```
+--------------------------------------------------------------+
|                  GradeService                                |
+--------------------------------------------------------------+
| - gradeDAO: GradeDAO                                         |
+--------------------------------------------------------------+
| + GradeService(GradeDAO)                                     |
| + searchBySubject(Long, SearchCriteria): List<SubjectResult> |
+--------------------------------------------------------------+
```

- **StatisticsService.java**:
    - `studentDAO` -> StudentDAO instance
    - `gradeDAO` -> GradeDAO instance
    - `StatisticsService(StudentDAO, GradeDAO)` -> Constructor with DAO injection
    - `calculateClassAverageBySubject()` -> Calculates class average by subject
    - `getStudentCountByAgeGroup()` -> Returns student count by age group
    - `getGradeDistributionBySubject()` -> Returns grade distribution ranges by subject
    - `getMinAverageBySubject()` -> Finds minumum average by subject in the student class list 
    - `getMaximumAverageBySubject()` -> Finds maximum average by subject in the student class list 
    - `calculateWeightedAverageGrade` -> Calculates and returns the pondereate (coeff) average of the grades by school subjects
```
+--------------------------------------------------------------------+
|                StatisticsService                                   |
+--------------------------------------------------------------------+
| - studentDAO: StudentDAO                                           |
| - gradeDAO: GradeDAO                                               |
+--------------------------------------------------------------------+
| + StatisticsService(StudentDAO, GradeDAO)                          |
| + calculateClassAverageBySubject(String): double                   |
| + getStudentCountByAgeGroup(): Map<String, Long>                   |
| + getGradeDistributionBySubject(String): Map<String, List<Double>> |
| + getTopPerformers(int): List<Student>                             |
| + getStudentStatistics(): Map<String, Object>                      |
| + getMinAverageBySubject(String): double                           |
| + getMaximumAverageBySubject(String): double                       |
| + calculateWeightedAverageGrade(): double                          |
+--------------------------------------------------------------------+
```
- **AuthenticationService.java**:
    - `userDAO` -> UserDAO instance
    - `currentUser` -> Current logged-in user
    - `AuthenticationService()` -> Constructor with UserDAO injection
    - `authenticate()` -> Authenticates user with login/password
    - `register(User)` -> Registers new user
    - `isAuthenticated()` -> Checks if user is logged in

```
+-------------------------------------------------+
|              AuthenticationService              |
+-------------------------------------------------+
| - userDAO: UserDAO                              |
| - currentUser: User                             |
+-------------------------------------------------+
| + AuthenticationService(UserDAO)                |
| + authenticate(String, String): boolean         |
| + register(User): void                          |
| + isAuthenticated(): boolean                    |
+-------------------------------------------------+
```

- **ImportExportService.java**:
    - `csvHandler` -> CSV file handler
    - `pdfExporter` -> PDF file exporter
    - `ImportExportService()` -> Default constructor
    - `exportToCSV()` -> Exports research result to CSV file
    - `exportToPDF()` -> Exports graphs to PDF file
```
+-------------------------------------------------+
|               ImportExportService               |
+-------------------------------------------------+
| - csvHandler: CSVHandler                        |
| - pdfExporter: PDFExporter                      |
+-------------------------------------------------+
| + ImportExportService()                         |
| + exportToCSV(List<Student>): void              |
| + exportToPDF(): void                           |
+-------------------------------------------------+
```
- **BackupService.java**:
    - `databaseConnection` -> Database connection instance
    - `BackupService(DatabaseConnection)` -> Constructor with connection injection
    - `createBackup()` -> Creates complete system backup
    - `restoreBackup()` -> Restores system from backup
    - `scheduleAutoBackup()` -> Schedules periodic automatic backup
    - `listBackups()` -> Lists all available backups
    - `deleteBackup()` -> Deletes specific backup

```
+-------------------------------------------------+
|                BackupService                    |
+-------------------------------------------------+
| - databaseConnection: DatabaseConnection        |
| - studentDAO: StudentDAO                        |
+-------------------------------------------------+
| + BackupService(DatabaseConnection, StudentDAO) |
| + createBackup(): void                          |
| + restoreBackup(): void                         |
| + scheduleAutoBackup(): void                    |
| + stopAutoBackup(): void                        |
| + listBackups(): List<String>                   |
| + deleteBackup(String): void                    |
| - compressBackup(String): void                  |
| - extractBackup(String): void                   |
+-------------------------------------------------+
```

### 5. **Controller Layer** 
* * *

- **MainController.java**:
    - `studentService` -> StudentService instance
    - `authService` -> AuthenticationService instance
    - `currentStage` -> Stage instance
    - `currentUser` -> User instance
    - `initialize()` -> Initializes the main controller
    - `showGeneralManagement()` -> Displays General management view
    - `showStudentManagement()` -> Displays student management view
    - `showGeneralStatistics()` -> Displays general statistics view
    - `showStudentStatistics()` -> Displays student statistics view
    - `showImportExport()` -> Displays import/export pop up
    - `showGradeModifyForm()` -> Displays grade modify delete form
    - `showCoefficientModifyForm()` -> Displays coefficient modify delete form
    - `showLogin()` -> Displays login view
    - `showRegister()` -> Displays register view
    - `loadView()` -> Dynamically loads a FXML view

```
+-------------------------------------------------+
|                MainController                   |
+-------------------------------------------------+
| - studentService: StudentService                |
| - authService: AuthenticationService            |
| - currentStage: Stage                           |
| - currentUser: User                             |
+-------------------------------------------------+
| + initialize(): void                            |
| + showGeneralManagement(): void                 |
| + showStudentManagement(): void                 |
| + showGeneralStatistics(): void                 |
| + showStudentStatistics(): void                 |
| + showImportExport(): void                      |
| + showGradeModifyForm(): void                   |
| + showCoefficientModifyForm(): void             |
| + showLogin(): void                             |
| + showRegister(): void                          |
| + loadView(String): void                        |
+-------------------------------------------------+
```
- **AuthenticationController.java**:
    - `authService` -> AuthenticationService instance
    - `usernameField` -> TextField instance
    - `passwordField` -> PasswordField instance
    - `loginButton` -> Button instance
    - `registerButton` -> Button instance
    - `initialize()` -> Initializes authentication controller
    - `handleLogin()` -> Handles login event
    - `handleRegister()` -> Handles registration event
    - `showMainView()` -> Displays main view after login
    - `showAlert()` -> Displays alert dialog box
```
+-------------------------------------------------+
|            AuthenticationController             |
+-------------------------------------------------+
| - authService: AuthenticationService            |
| - usernameField: TextField                      |
| - passwordField: PasswordField                  |
| - loginButton: Button                           |
| - registerButton: Button                        |
+-------------------------------------------------+
| + initialize(): void                            |
| + handleLogin(): void                           |
| + handleRegister(): void                        |
| + showMainView(): void                          |
| + showAlert(String): void                       |
+-------------------------------------------------+
```
- **StudentFormController.java**:
    - `initialize()` -> Initializes student form
    - `setStudent()` -> Loads student data into form
    - `handleSaveStudent()` -> Handles student creation/update
    - `handleCancelStudent()` -> Handles form cancel
    - `handleDeleteStudent()` -> Handles student deletion
    - `clearForm()` -> Clears all form fields
    - `validateStudentForm()` -> Validates student data
```
+-------------------------------------------------+
|              StudentFormController              |
+-------------------------------------------------+
| + initialize(): void                            |
| + setStudent(Student): void                     |
| + handleSaveStudent(): void                     |
| + handleCancelStudent(): void                   |
| + handleDeleteStudent(): void                   |
| + clearForm(): void                             |
| + validateStudentForm(): boolean                |
+-------------------------------------------------+
```

- **StatisticsController.java**:
    - `initialize()` -> Initializes the controller
    - `loadStatistics()` -> Loads statistics data
    - `updateCharts()` -> Updates all chart displays
    - `handleRefresh()` -> Handles refresh button click
    - `handleExport()` -> Handles export button click
    - `createAgeDistributionChart()` -> Creates age distribution pie chart
    - `createGradeDistributionChart()` -> Creates grade distribution bar chart
```
+-------------------------------------------------+
|                StatisticsController             |
+-------------------------------------------------+
| + initialize(): void                            |
| + loadStatistics(): void                        |
| + updateCharts(): void                          |
| + handleRefresh(): void                         |
| + handleExport(): void                          |
| - createAgeDistributionChart(): void            |
| - createGradeDistributionChart(): void          |
+-------------------------------------------------+
```
- **CommentController.java**:
    - `commentService` -> SubjectCommentService instance
    - `initialize()` -> Initializes comment controller
    - `validateCommentInput()` -> Validates comment data
    - `refreshCommentView()` -> Updates comment display
    - `handleAddComment()` -> Handles comment addition
    - `handleUpdateComment()` -> Handles comment modification
    - `handleDeleteComment()` -> Handles comment deletion
```
+-------------------------------------------------+
|                CommentController                |
+-------------------------------------------------+
| - commentService: SubjectCommentService         |
+-------------------------------------------------+
| + initialize(): void                            |
| + validateCommentInput(): boolean               |
| + refreshCommentView(): void                    |
| + handleAddComment(): void                      |
| + handleUpdateComment(): void                   |
| + handleDeleteComment(): void                   |
+-------------------------------------------------+
```

- **BackupController.java**:
    - `backupService` -> BackupService instance
    - `handleCreateBackup()` -> Handles backup creation
    - `handleRestoreBackup()` -> Handles backup restoration
    - `handleAutoBackupSchedule()` -> Manages auto-backup settings
    - `showBackupList()` -> Displays available backups
    - `handleDeleteBackup()` -> Handles backup deletion
```
+-------------------------------------------------+
|                BackupController                 |
+-------------------------------------------------+
| - backupService: BackupService                  |
+-------------------------------------------------+
| + handleCreateBackup(): void                    |
| + handleRestoreBackup(): void                   |
| + handleAutoBackupSchedule(): void              |
| + showBackupList(): void                        |
| + handleDeleteBackup(): void                    |
+-------------------------------------------------+
```
- **ImportExportController.java**:
    - `importExportService` -> ImportExportService instance
    - `initialize()` -> Initializes import/export controller
    - `handleFileSelection()` -> Handles file picker dialog
    - `handleCSVExport()` -> Handles CSV file export
    - `handlePDFExport()` -> Handles PDF file export

```
+-------------------------------------------------+
|              ImportExportController             |
+-------------------------------------------------+
| - importExportService: ImportExportService      |
+-------------------------------------------------+
| + initialize(): void                            |
| + handleFileSelection(): void                   |
| + handleCSVExport(): void                       |
| + handlePDFExport(): void                       |
+-------------------------------------------------+
```

- **SearchController.java**:
    - `studentService` -> StudentService instance
    - `gradeService` -> GradeService instance
    - `initialize()` -> Initializes search controller
    - `clearSearchResults()` -> Clears current search results
    - `exportSearchResults()` -> Exports current search to CSV/PDF
    - `handleGeneralSearch()` -> Handles general student search
    - `handleSubjectSearch()` -> Handles subject-specific search
    - `handleAdvancedSearch()` -> Handles complex search criteria
    - `updateSearchResults()` -> Updates search result display
    - `handlePagination()` -> Manages pagination controls
```
+-------------------------------------------------+
|                SearchController                 |
+-------------------------------------------------+
| - studentService: StudentService                |
| - gradeService: GradeService                    |
+-------------------------------------------------+
| + initialize(): void                            |
| + clearSearchResults(): void                    |
| + exportSearchResults(): void                   |
| + handleGeneralSearch(): void                   |
| + handleSubjectSearch(): void                   |
| + handleAdvancedSearch(): void                  |
| + updateSearchResults(): void                   |
| + handlePagination(): void                      |
+-------------------------------------------------+
```
- **GradeController.java**:
    - `gradeService` -> GradeService instance
    - `initialize()` -> Initializes grade controller
    - `handleAddGrade()` -> Handles adding new grades
    - `handleUpdateGrade()` -> Handles grade modifications
    - `handleDeleteGrade()` -> Handles grade deletion
    - `validateGradeInput()` -> Validates grade data
    - `refreshGradeView()` -> Updates grade display
```
+-------------------------------------------------+
|                GradeController                  |
+-------------------------------------------------+
| - gradeService: GradeService                    |
+-------------------------------------------------+
| + initialize(): void                            |
| + handleAddGrade(): void                        |
| + handleUpdateGrade(): void                     |
| + handleDeleteGrade(): void                     |
| + validateGradeInput(): boolean                 |
| + refreshGradeView(): void                      |
+-------------------------------------------------+
```

### 6. **View**
* * *

- **MainView.java**:

- **StudentView.java**:

- **AuthenticationView.java**:

- **GeneralGraphicView.java**:

- **StudentGraphicView.java**:

### 7. **Utils Layer**
* * *

- **SearchCriteria.java**:
    - `searchValue` -> Value typed in the search bar
    - `pageNumber` -> Page number (default: 1)
    - `pageSize` -> Page size (default: 15)
    - `SearchCriteria()` -> Default constructor
    - `SearchCriteria()` -> Constructor with searchValue
    - `getSearchValue()` -> Returns the search value
    - `setSearchValue()` -> Sets the search value
    - `getPageNumber()` -> Returns the page number
    - `setPageNumber()` -> Sets the page number
    - `getPageSize()` -> Returns the page size
    - `setPageSize()` -> Sets the page size
    - `getOffset()` -> Calculates the offset ((pageNumber - 1) * pageSize)
    - `toString()` -> String representation
```
+-------------------------------------------------+
|                SearchCriteria                   |
+-------------------------------------------------+
| - searchValue: String                           |
| - pageNumber: int                               |
| - pageSize: int                                 |
+-------------------------------------------------+
| + SearchCriteria()                              |
| + SearchCriteria(String): void                  |
| + getSearchValue(): String                      |
| + setSearchValue(String): void                  |
| + getPageNumber(): int                          |
| + setPageNumber(int): void                      |
| + getPageSize(): int                            |
| + setPageSize(int): void                        |
| + getOffset(): int                              |
| + toString(): String                            |
+-------------------------------------------------+
```

- **SubjectResult.java**:
    - `subject` -> Subject
    - `grades` -> List of grades (String format "grade1, grade2, grade3...")
    - `studentAverage` -> Student's average
    - `classMinAverage` -> Minimum class average
    - `classMaxAverage` -> Maximum class average
    - `teacherComment` -> Teacher's comment
    - `SubjectResult()` -> Default constructor
    - `SubjectResult()` -> Constructor with all parameters
    - `getSubject()` -> Returns the subject
    - `setSubject()` -> Sets the subject
    - `getGrades()` -> Returns the formatted grades
    - `setGrades()` -> Sets the formatted grades
    - `getStudentAverage()` -> Returns the student's average
    - `setStudentAverage()` -> Sets the student's average
    - `getClassMinAverage()` -> Returns the minimum class average
    - `setClassMinAverage()` -> Sets the minimum class average
    - `getClassMaxAverage()` -> Returns the maximum class average
    - `setClassMaxAverage()` -> Sets the maximum class average
    - `getTeacherComment()` -> Returns the teacher's comment
    - `setTeacherComment()` -> Sets the teacher's comment
    - `toString()` -> String representation
```
+-------------------------------------------------+
|                SubjectResult                    |
+-------------------------------------------------+
| - subject: String                               |
| - grades: String                                |
| - studentAverage: double                        |
| - classMinAverage: double                       |
| - classMaxAverage: double                       |
| - teacherComment: String                        |
+-------------------------------------------------+
| + SubjectResult()                               |
| + SubjectResult(String, String, double,         |
|                   double, double, String): void |
| + getSubject(): String                          |
| + setSubject(String): void                      |
| + getGrades(): String                           |
| + setGrades(String): void                       |
| + getStudentAverage(): double                   |
| + setStudentAverage(double): void               |
| + getClassMinAverage(): double                  |
| + setClassMinAverage(double): void              |
| + getClassMaxAverage(): double                  |
| + setClassMaxAverage(double): void              |
| + getTeacherComment(): String                   |
| + setTeacherComment(String): void               |
| + toString(): String                            |
+-------------------------------------------------+
```
- **InputValidator.java**:
    - `isValidName()` -> Validates name format
    - `isValidAge()` -> Validates if age is within acceptable range
    - `isValidGrade()` -> Validates if grade is within valid range
    - `isValidEmail()` -> Validates email format
    - `isValidId()` -> Validates ID format
    - `sanitizeInput()` -> Cleans and secures an input string
    - `validateStudent()` -> Validates all student fields when adding new student
    - `validateGrade()` -> Validates all grade fields
    - `validateUser()` -> Validates user registration data
```
+-------------------------------------------------+
|                InputValidator                   |
+-------------------------------------------------+
| + isValidName(String): boolean                  |
| + isValidAge(int): boolean                      |
| + isValidGrade(double): boolean                 |
| + isValidEmail(String): boolean                 |
| + isValidId(String): boolean                    |
| + sanitizeInput(String): String                 |
| + validateStudent(Student): boolean             |
| + validateGrade(Grade): boolean                 |
| + validateUser(User): boolean                   |
+-------------------------------------------------+
```
- **PasswordUtils.java**:
    - `generateSalt()` -> Generates random salt
    - `hashPassword()` -> Hashes password with salt
    - `hashPasswordWithSalt()` -> Hashes password with auto-generated salt
    - `verifyPassword()` -> Verifies if password matches hash
    - `validatePasswordStrength()` -> Validates password strength
    - `getPasswordCriteria()` -> Returns validation criteria
```
+-------------------------------------------------+
|                PasswordUtils                    |
+-------------------------------------------------+
| + generateSalt(): String                        |
| + hashPassword(String, String): String          |
| + hashPasswordWithSalt(String): String          |
| + verifyPassword(String, String): boolean       |
| + validatePasswordStrength(String): boolean     |
| + getPasswordCriteria(): String                 |
+-------------------------------------------------+
```
- **CSVHandler.java**:
    - `delimiter` -> CSV delimiter character (default: comma)
    - `CSVHandler()` -> Default constructor with standard delimiters
    - `CSVHandler()` -> Constructor with custom delimiters
    - `exportGeneralSearchResults()` -> Exports general search results list to CSV file
    - `exportStudentSearchResults()` -> Exports grade list to CSV file
    - `validateCSVFormat(S)` -> Validates CSV file format
    - `parseStudentFromCSV()` -> Parses CSV line to Student object
    - `formatStudentToCSV()` -> Formats Student object to CSV line
    - `parseGeneralFromCSV()` -> Parses CSV line to general object
    - `formatGeneralToCSV()` -> Formats general object to CSV line
```
+-------------------------------------------------+
|                CSVHandler                       |
+-------------------------------------------------+
| - delimiter: char                               |
+-------------------------------------------------+
| + CSVHandler()                                  |
| + CSVHandler(char): void                        |
| + exportGeneralSearchResults(List): void        |
| + exportStudentSearchResults(List): void        |
| + validateCSVFormat(String): boolean            |
| + parseStudentFromCSV(String): Student          |
| + formatStudentToCSV(Student): String           |
| + parseGeneralFromCSV(String): General          |
| + formatGeneralToCSV(General): String           |
+-------------------------------------------------+
```
- **PDFExporter.java**:
    - `document` -> Document instance
    - `writer` -> PdfWriter instance
    - `PDFExporter()` -> Default constructor, initializes PDF components
    - `exportStatisticsGeneralGraph()` -> Exports general statistics graph to PDF file
    - `exportStatisticsStudentGraph()` -> Exports student statistics graph to PDF file
    - `formatStudentData()` -> Formats student data for PDF
    - `addHeader()` -> Adds header to PDF document
    - `addFooter()` -> Adds footer to PDF document

```
+-------------------------------------------------+
|                PDFExporter                      |
+-------------------------------------------------+
| - document: Document                            |
| - writer: PdfWriter                             |
+-------------------------------------------------+
| + PDFExporter()                                 |
| + exportStatisticsGeneralGraph(): void          |
| + exportStatisticsStudentGraph(): void          |
| + formatStudentData(Student): void              |
| + addHeader(): void                             |
| + addFooter(): void                             |
+-------------------------------------------------+
```
### Directory Structure
* * *

```
StudentManagementSystem/
│
├── src/
│   ├── main/
│   │   ├── java/
│   │   │   ├── com/
│   │   │   │   ├── studentmanagement/
│   │   │   │   │   ├── model/
│   │   │   │   │   │   ├── Student.java
│   │   │   │   │   │   ├── Grade.java
│   │   │   │   │   │   ├── User.java
│   │   │   │   │   │   ├── SubjectComment.java
│   │   │   │   │   │   └── SubjectResult.java
│   │   │   │   │   ├── dao/
│   │   │   │   │   │   ├── StudentDAO.java
│   │   │   │   │   │   ├── StudentDAOImpl.java
│   │   │   │   │   │   ├── UserDAO.java
│   │   │   │   │   │   ├── GradeDAO.java
│   │   │   │   │   │   ├── GradeDAOImpl.java
│   │   │   │   │   │   ├── SubjectCommentDAO.java
│   │   │   │   │   │   └── SubjectCommentDAOImpl.java
│   │   │   │   │   ├── database/
│   │   │   │   │   │   ├── DatabaseConnection.java
│   │   │   │   │   │   └── DatabaseConfig.java
│   │   │   │   │   ├── service/
│   │   │   │   │   │   ├── StudentService.java
│   │   │   │   │   │   ├── AuthenticationService.java
│   │   │   │   │   │   ├── StatisticsService.java
│   │   │   │   │   │   ├── ImportExportService.java
│   │   │   │   │   │   └── BackupService.java
│   │   │   │   │   ├── controller/
│   │   │   │   │   │   ├── StudentFormController.java
│   │   │   │   │   │   ├── StatisticsController.java
│   │   │   │   │   │   ├── CommentController.java
│   │   │   │   │   │   ├── BackupController.java
│   │   │   │   │   │   ├── ImportExportController.java
│   │   │   │   │   │   └── SearchController.java
│   │   │   │   │   │   └── GradeController.java
│   │   │   │   │   ├── view/
│   │   │   │   │   │   ├── MainView.java
│   │   │   │   │   │   ├── StudentView.java
│   │   │   │   │   │   ├── AuthenticationView.java
│   │   │   │   │   │   ├── GeneralGraphicView.java
│   │   │   │   │   │   └── StudentGraphicView.java
│   │   │   │   │   ├── utils/
│   │   │   │   │   │   ├── InputValidator.java
│   │   │   │   │   │   ├── PasswordUtils.java
│   │   │   │   │   │   ├── CSVHandler.java
│   │   │   │   │   │   ├── XMLHandler.java
│   │   │   │   │   │   ├── JSONHandler.java
│   │   │   │   │   │   └── PDFExporter.java
│   │   │   │   │   └── Main.java
│   │   │   │
│   │   │   └── resources/
│   │   │       ├── fxml/
│   │   │       │   ├── main-view.fxml
│   │   │       │   ├── student-form.fxml
│   │   │       │   ├── student-list.fxml
│   │   │       │   ├── login.fxml
│   │   │       │   ├── statistics.fxml
│   │   │       │   └── search.fxml
│   │   │       ├── css/
│   │   │       │   └── styles.css
│   │   │       ├── images/
│   │   │       │   └── icons/
│   │   │       ├── database/
│   │   │       │   └── schema.sql
│   │   │       └── config/
│   │   │           └── application.properties
│   │
│   └── test/
│       ├── java/
│       │   ├── com/
│       │   │   ├── studentmanagement/
│       │   │   │   ├── dao/
│       │   │   │   │   └── StudentDAOTest.java
│       │   │   │   ├── service/
│       │   │   │   │   ├── StudentServiceTest.java
│       │   │   │   │   └── AuthenticationServiceTest.java
│       │   │   │   └── utils/
│       │   │   │       └── InputValidatorTest.java
│       │
│       └── resources/
│           └── test-data/
│               └── sample-students.csv
│
├── lib/
│   ├── postgresql-xx.x.jar
│   └── other-dependencies.jar
│
├── docs/
│   ├── database-schema.md
│   └── user-manual.md
│
├── scripts/
│   ├── setup-database.sql
│   └── sample-data.sql
│
├── README.md
├── .gitignore
└── pom.xml
```
## Database 

### Diagram tables
* * *
### User Table
The `User` table is designed to manage user accounts within the system.
It contains essential information about each user, including their credentials, role, and account status.

```
+--------------------------------------------------+
|                      User                        |
+--------------------------------------------------+
| - id: BIGSERIAL PRIMARY KEY                      |
| - username: VARCHAR(255) NOT NULL UNIQUE         |
| - password_hash: TEXT NOT NULL                   |
| - role: VARCHAR(50) DEFAULT 'USER'               |  -- Default role value
| - email: VARCHAR(255) NOT NULL UNIQUE            |
| - is_active: BOOLEAN DEFAULT TRUE                |
+--------------------------------------------------+
```

### Class Table
The `Class` table stores information about academic classes.
Each class has a unique identifier, a name.
```
+---------------------------------------------------+
|                      Class                        |
+---------------------------------------------------+
| - id: BIGSERIAL PRIMARY KEY                       |
| - name: VARCHAR(5) NOT NULL UNIQUE               | -- example : T1, 5B, 3A ..
+---------------------------------------------------+
```
