package com.studentmanagement.controller;

import com.studentmanagement.model.Student;
import com.studentmanagement.service.ImportExportService;
import com.studentmanagement.service.StudentService;

import javafx.fxml.FXML;
import javafx.scene.control.*;

public class StudentsController{

    @FXML
    private TextField searchField;
    @FXML
    private Button searchButton;
    @FXML 
    private Button importButton;
    @FXML
    private Button exportButton;

    @FXML
    private TableView<Student> studentTable;
    @FXML
    private TableColumn<Student, Integer> idColumn;
    @FXML
    private TableColumn<Student, String> firstNameColumn;
    @FXML
    private TableColumn<Student, String> lastNameColumn;
    @FXML
    private TableColumn<Student, Integer> ageColumn;
    @FXML
    private TableColumn<Student, String> classNameColumn;
    @FXML
    private TableColumn<Student, Double> averageGradeColumn;
    @FXML
    private TableColumn<Student, Void> editColumn;
    @FXML
    private TableColumn<Student, Void> deleteColumn;

    @FXML
    private Pagination pagination;

    @FXML
    private TextField firstNameField;
    @FXML
    private TextField lastNameField;
    @FXML
    private TextField ageField;
    @FXML
    private TextField classNameField;
    @FXML
    private Button addButton;

    private StudentService studentService;
    private ImportExportService importExportService;
    private final int ROWS_PER_PAGE = 15;

    public StudentsController(){
        //Empty constructor - dependancies will be injected
    }

    @FXML
    private void initialize(){
        //Init services
        studentService = new StudentService();
        importExportService = new ImportExportService();

        //Config columns
        setupTableColumns();

        //Config action columns edit and delete
        setupEditColumn();
        setupDeleteColumn();

        //Init pagination
        pagination.setPageCount(calculatePageCount());
        pagination.currentPageIndexProperty().addListener((obs, oldIndex, newIndex)-> {
           loadStudentsPage(newIndex.intValue()); 
        });

        //Config buttons events
        setupButtonHandlers();

        //Activate sorting columns
        setupColumnSorting();

        //load inti data
        loadStudentsPage(0);
    }

    private void setupTableColumns(){

    }

    private void setupButtonHandlers(){

    }

    private void setupColumnSorting(){

    }

    // private void setStudentService(){

    // }

    private void setupEditColumn(){

    }

    private void setupDeleteColumn(){

    }

    private void loadStudentsPage(int pageIndex){

    }

    private int calculatePageCount(){
        return 1;
    }


}