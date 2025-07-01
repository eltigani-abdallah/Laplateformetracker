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

```
A secure authentification process:
┌─────────────────┐    ┌──────────────────┐    ┌─────────────────┐
│   Controller    │───>│   UserService    │───>|    UserDAO      │
│                 │    │                  │    │                 │
│ - loginUser()   │    │ - createUser()   │    │ - saveUser()    │
│ - registerUser()│    │ - authenticate() │    │ - findByUsername│
└─────────────────┘    │ - hashPassword() │    └─────────────────┘
                       └──────────────────┘              │
                                │                        |
                                ▼                        ▼
                       ┌───────────────────┐    ┌─────────────────┐
                       │ PasswordUtils     │    │   Database      │
                       │                   │    │                 │
                       │ - hashPassword()  │    │ users table     │
                       │ - verifyPassword()│    │ - passwordHash  │
                       └───────────────────┘    └─────────────────┘

```

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
    - `getSubject()` -> Returns the school subject of the grade
    - `setSubject()` -> Sets the school subject of the grade
    - `getValue()` -> Returns the numeric value of the grade
    - `setValue()` -> Sets the numeric value of the grade
    - `getCoefficient()` -> Returns the grade coefficient
    - `setCoefficient()` -> Sets the grade coefficient
    - `getDate()` -> Returns the date of the grade
    - `setDate()` -> Sets the date of the grade
    - `getWeightedGradeValue()` -> Calculates and returns the ponderated value of the grade (value * coefficient)
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

- **StudentDAO.java**: interface
    - save() -> Save a student to the database
    - findStudentById() -> Finds a student by their unique ID
    - findAllStudent() -> Retrieves all students from the database
    - updateStudent() -> Updates an existing student's information
    - deleteStudent() -> Deletes a student by their ID
    - searchGeneral() -> General research with pagination
    - countSearchGeneral() -> count results for general research for pagination
    - count() -> Returns the total number of students 

    NOTE: 
    - StudentDAO is an interface that defines the contract with all CRUD operations (Create, Read, Update, Delete) and specialized search methods to manipulate student data.

    ```java
    // example
     public interface StudentDAO {
    Student findById(Long id);
    // other methods...
    }
     ```

                    ↑
                implements
                    |

- **StudentDAOImpl.java**:
    - connection -> Database connection object
    - dbConnection -> Database connection manager
    - StudentDAOImpl() -> Constructor with dependency injection
    - saveStudent() -> Implements student saving to database
    - findStudentById() -> Implements finding students by ID
    - findAllStudent() -> Implements retrieving all students
    - updateStudent() -> Implements student data updating
    - deleteStudent() -> Implements student deletion by ID
    - searchGeneral() -> Implement general search across all fields with pagination
    - countSearchGeneral() -> Implements counts the results of the general search
    - count() -> Implements counting total students
    - mapResultSetToStudent -> Convert database ResultSet to Student 

    NOTE: 
    - StudentDAOImpl is the concrete class that implements his interface. It contains:
    - Required dependencies (Connection and DatabaseConnection)
    - The actual implementation of all methods defined in the interface
    - A private utility method mapResultSetToStudent for data conversion: 
    When an SQL query is executed, it returns data in the form of a ResultSet, which is essentially a result table. 
    This method performs the "mapping" (transformation) between:
        - ResultSet columns (e.g., id, name, age, grade...)
        - The corresponding properties of a new Student object
    This method is used internally by other methods like findById(), findAll(), etc., to convert database results into usable Java objects.

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
    - saveGrade() -> Saves a grade to the database
    - updateGrade() -> Updates an existing grade by its ID
    - deleteGrade() -> Delete an existing gade by its ID
    - saveCoefficient() -> Saves a grade coefficient
    - updateCoefficient() -> Updates an existing coefficient
    - getMinAverageBySubject() -> Finds minumum average by subject in the student class list 
    - getMaximumAverageBySubject() -> Finds maximum average by subject in the student class list 
    - searchBySubject() -> Complete research by subject with pagination
    - countBySubject() -> Count for pagination
    - calculateWeightedAverageGrade -> Calculates and returns the pondereate (coeff) average of the grades by school subjects
    - count() -> Returns the total number of grades

                        ↑
                    implements
                        |

- **GradeDAOImpl.java**:
    - connection -> Database connection object
    - dbConnection -> Database connection manager
    - GradeDAOImpl() -> Constructor with dependency injection
    - saveGrade() -> Implements grade saving to database
    - updateGrade() -> Implements grade updating by ID
    - deleteGrade() -> Implements grade deletion by ID
    - saveCoefficient() -> Implements coefficient saving
    - updateCoefficient() -> Implements coefficient updating
    - getMinAverageBySubject() -> Implements finding minimum average by subject
    - getMaximumAverageBySubject() -> Implements finding maximum average by subject
    - searchBySubject() -> Complex request with GROUP BY to returns all the info in one request (subject - grades - min average grade, max average grade, student average, comment)
    - countBySubject() -> 
    - calculateWeightedAverageGrade() -> Implements weighted average calculation by subject
    - mapResultSetToGrade() -> Convert database ResultSet to Grade

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
    - saveComment() -> Saves a comment for a subject
    - updateComment() -> Updates an existing comment
    - deleteComment() -> Deletes a comment
    - findCommentsByStudentAndSubject() -> Finds comments for student/subject

                        ↑
                    implements
                        |

- **SubjectCommentDAOImpl.java**:
    - connection -> Database connection object
    - dbConnection -> Database connection manager
    - SubjectCommentDAOImpl() -> Constructor with dependency injection
    - saveComment() -> Implements comment saving to database
    - updateComment() -> Implements comment updating
    - deleteComment() -> Implements comment deletion
    - findCommentsByStudentAndSubject() -> Implements finding comments by student and subject
    - mapResultSetToComment() -> Convert database ResultSet to Comment

- **UserDAO.java**: interface
    - saveUser() -> Saves a user to the database 
    - findUserByUsername () -> Finds a user by username
    - authenticateUser() -> Checks if username/password combination is valid

                        ↑
                    implements
                        |

- **UserDAOImpl.java**:
    - connection -> Database connection object
    - dbConnection -> Database connection manager
    - UserDAOImpl() -> Implements finding user by username
    - saveUser() -> Implements user saving to database
    - findUserByUsername -> Implements findinf user by username
    - authenticateUser() -> Implements login authentication
    - mapResultSetToUser() -> Convert database ResultSet to User

### 3. **Database**
* * *

 - **DatabaseConnection.java**:
    - url -> Database UrL string
    - username -> Database username
    - password -> Database password
    - connection -> Active database connection object
    - DatabaseConnection() -> Default constructor
    - connect() -> Establishes database connection
    - disconnect() -> Closes database connection
    - getConnection() -> Returns active connection
    - isConnected() -> Checks if connection is active
    - executeQuery() -> Executes SELECT query
    - executeUpdate() -> Executes INSERT/UPDATE/DELETE query
    - executeSafeQuery() -> Executes SQL query with parameters for security
    - closeResources()

- **DatabaseConfig.java**:
    - properties -> Properties object for configuration
    - configFile -> Configuration file path
    - DatabaseConfig() -> Default constructor, loads configuration
    - loadConfig() -> Loads configuration from properties file
    - getDatabaseUrl() -> Returns database URL
    - getDatabaseUsername() -> Returns database username
    - getDatabasePassword() -> Returns database password

### 4. **Service Layer (Business Logic)**
* * *
The Service layer contains business logic and orchestrates calls to the DAOs

- **StudentService.java**:
    - studentDAO -> StudentDAO instance
    - validator -> InputValidator instance
    - StudentService() -> Constructor with DAO injection
    - createStudent() -> Creates new student with validation
    - getStudentByID() -> Retrieves student by ID
    - getAllStudents() -> Retrieves all students
    - updateStudent() -> Updates existing student
    - deleteStudent() -> Deletes student by ID
    - searchStudents()  -> Returns paginated student list - use StudentDAO.searchGeneral() with SearchCriteria and pagination

- **GradeService.java**:
    - gradeDAO -> GradeDAO instance
    - GradeService() -> Constructeur
    - searchBySubject() -> Use GradeDAO.searchBySubject() with SearchCriteria and pagination

- **StatisticsService.java**:
    - studentDAO -> StudentDAO instance
    - StatisticsService() -> Constructor with DAO injection
    - calculateClassAverageBySubject() -> Calculates class average by subject
    - getStudentCountByAgeGroup() -> Returns student count by age group
    - getGradeDistributionBySubject() -> Returns grade distribution ranges by subject
    - getTopPerformers() -> Returns top N performing students
    - getStudentStatistics() -> Returns general statistics summary

- **AuthenticationService.java**:
    - userDAO -> UserDAO instance
    - currentUser -> Current logged-in user
    - AuthenticationService() -> Constructor with UserDAO injection
    - authenticate() -> Authenticates user with login/password
    - isAuthenticated() -> Checks if user is logged in

- **ImportExportService.java**:
    - csvHandler -> CSV file handler
    - pdfExporter -> PDF file exporter
    - ImportExportService() -> Default constructor
    - exportToCSV() -> Exports research result to CSV file
    - exportToPDF() -> Exports graphs to PDF file

- **BackupService.java**:
    - BackupService() -> Constructor with DAO injection
    - createBackup() -> Creates complete system backup
    - restoreBackup() -> Restores system from backup
    - scheduleAutoBackup() -> Schedules periodic automatic backup
    - stopAutoBackup() -> Stops automatic backup
    - listBackups() -> Lists all available backups
    - deleteBackup(String) -> Deletes specific backup
    - compressBackup(String) -> Compresses backup to save space
    - extractBackup(String) -> Extracts compressed backup

### 5. **Controller Layer** 
* * *

- **MainController.java**:
    - studentService -> StudentService instance
    - authService -> AuthenticationService instance
    - currentStage -> Stage instance
    - currentUser -> User instance
    - initialize() -> Initializes the main controller
    - showGeneralManagement() -> Displays General management view
    - showStudentManagement() -> Displays student management view
    - showGeneralStatistics() -> Displays general statistics view
    - showStudentStatistics() -> Displays student statistics view
    - showImportExport() -> Displays import/export pop up
    - showGradeModifyForm() -> Displays grade modify delete form
    - showCoefficientModifyForm() -> Displays coefficient modify delete form
    - showLogin() -> Displays login view
    - showRegister() -> Displays register view
    - loadView(String) -> Dynamically loads a FXML view

- **AuthenticationController.java**:
    - authService -> AuthenticationService instance
    - usernameField -> TextField instance
    - passwordField -> PasswordField instance
    - loginButton -> Button instance
    - registerButton -> Button instance
    - initialize() -> Initializes authentication controller
    - handleLogin() -> Handles login event
    - handleRegister() -> Handles registration event
    - showMainView() -> Displays main view after login
    - showAlert() -> Displays alert dialog box

- **StudentFormController.java**:
    - initialize() -> Initializes student form
    - setStudent(Student) -> Loads student data into form
    - handleSaveStudent() -> Handles student creation/update
    - handleCancelStudent() -> Handles form cancel
    - handleDeleteStudent() -> Handles student deletion
    - clearForm() -> Clears all form fields
    - validateStudentForm() -> Validates student data

- **StatisticsController.java**:
    - initialize() -> Initializes the controller
    - loadStatistics() -> Loads statistics data
    - updateCharts() -> Updates all chart displays
    - handleRefresh() -> Handles refresh button click
    - handleExport() -> Handles export button click
    - createAgeDistributionChart() -> Creates age distribution pie chart
    - createGradeDistributionChart() -> Creates grade distribution bar chart

- **CommentController.java**:
    - commentService -> SubjectCommentService instance
    - initialize() -> Initializes comment controller
    - validateCommentInput() -> Validates comment data
    - refreshCommentView() -> Updates comment display
    - handleAddComment() -> Handles comment addition
    - handleUpdateComment() -> Handles comment modification
    - handleDeleteComment() -> Handles comment deletion

- **BackupController.java**:
    - backupService -> BackupService instance
    - handleCreateBackup() -> Handles backup creation
    - handleRestoreBackup() -> Handles backup restoration
    - handleAutoBackupSchedule() -> Manages auto-backup settings
    - showBackupList() -> Displays available backups
    - handleDeleteBackup() -> Handles backup deletion

- **ImportExportController.java**:
    - importExportService -> ImportExportService instance
    - initialize() -> Initializes import/export controller
    - handleFileSelection() -> Handles file picker dialog
    - handleCSVExport() -> Handles CSV file export
    - handlePDFExport() -> Handles PDF file export

- **SearchController.java**:
    - studentService -> StudentService instance
    - gradeService -> GradeService instance
    - initialize() -> Initializes search controller
    - clearSearchResults() -> Clears current search results
    - exportSearchResults() -> Exports current search to CSV/PDF
    - handleGeneralSearch() -> Handles general student search
    - handleSubjectSearch() -> Handles subject-specific search
    - handleAdvancedSearch() -> Handles complex search criteria
    - updateSearchResults() -> Updates search result display
    - handlePagination() -> Manages pagination controls

- **GradeController.java**:
    - gradeService -> GradeService instance
    - initialize() -> Initializes grade controller
    - handleAddGrade() -> Handles adding new grades
    - handleUpdateGrade() -> Handles grade modifications
    - handleDeleteGrade() -> Handles grade deletion
    - validateGradeInput() -> Validates grade data
    - refreshGradeView() -> Updates grade display

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
    - searchValue -> Value typed in the search bar
    - pageNumber -> Page number (default: 1)
    - pageSize -> Page size (default: 15)
    - SearchCriteria() -> Default constructor
    - SearchCriteria() -> Constructor with searchValue
    - getSearchValue() -> Returns the search value
    - setSearchValue() -> Sets the search value
    - getPageNumber() -> Returns the page number
    - setPageNumber() -> Sets the page number
    - getPageSize() -> Returns the page size
    - setPageSize() -> Sets the page size
    - getOffset() -> Calculates the offset ((pageNumber - 1) * pageSize)
    - toString() -> String representation

- **SubjectResult.java**:
    - subject -> Subject
    - grades -> List of grades (String format "grade1, grade2, grade3...")
    - studentAverage -> Student's average
    - classMinAverage -> Minimum class average
    - classMaxAverage -> Maximum class average
    - teacherComment -> Teacher's comment
    - SubjectResult() -> Default constructor
    - SubjectResult() -> Constructor with all parameters
    - getSubject() -> Returns the subject
    - setSubject() -> Sets the subject
    - getGrades() -> Returns the formatted grades
    - setGrades() -> Sets the formatted grades
    - getStudentAverage() -> Returns the student's average
    - setStudentAverage() -> Sets the student's average
    - getClassMinAverage() -> Returns the minimum class average
    - setClassMinAverage() -> Sets the minimum class average
    - getClassMaxAverage() -> Returns the maximum class average
    - setClassMaxAverage() -> Sets the maximum class average
    - getTeacherComment() -> Returns the teacher's comment
    - setTeacherComment() -> Sets the teacher's comment
    - toString() -> String representation

- **InputValidator.java**:
    - isValidName() -> Validates name format
    - isValidAge() -> Validates if age is within acceptable range
    - isValidGrade() -> Validates if grade is within valid range
    - isValidEmail() -> Validates email format
    - isValidId() -> Validates ID format
    - sanitizeInput() -> Cleans and secures an input string
    - validateStudent(Student) -> Validates all student fields when adding new student

- **PasswordUtils.java**:
    - generateSalt() -> Generates random salt
    - hashPassword(String, String) -> Hashes password with salt
    - hashPasswordWithSalt(String) -> Hashes password with auto-generated salt
    - verifyPassword(String, String) -> Verifies if password matches hash
    - validatePasswordStrength(String) -> Validates password strength
    - getPasswordCriteria() -> Returns validation criteria

- **CSVHandler.java**:
    - CSVHandler() -> Default constructor with standard delimiters
    - CSVHandler() -> Constructor with custom delimiters
    - exportGeneralSearchResults() -> Exports general search results list to CSV file
    - exportStudentSearchResults() -> Exports grade list to CSV file
    - validateCSVFormat(S) -> Validates CSV file format
    - parseStudentFromCSV() -> Parses CSV line to Student object
    - formatStudentToCSV() -> Formats Student object to CSV line
    - parseGeneralFromCSV() -> Parses CSV line to general object
    - formatGeneralToCSV() -> Formats general object to CSV line

- **PDFExporter.java**:
    - document -> Document instance
    - writer -> PdfWriter instance
    - PDFExporter() -> Default constructor, initializes PDF components
    - exportStatisticsGeneralGraph() -> Exports general statistics graph to PDF file
    - exportStatisticsStudentGraph() -> Exports student statistics graph to PDF file
    - formatStudentData() -> Formats student data for PDF
    - addHeader() -> Adds header to PDF document
    - addFooter() -> Adds footer to PDF document