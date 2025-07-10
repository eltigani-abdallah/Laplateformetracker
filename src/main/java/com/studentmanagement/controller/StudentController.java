package com.studentmanagement.controller;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import java.net.URL;
import java.util.Arrays;
import java.util.List;
import java.util.ResourceBundle;

import com.studentmanagement.model.Student;
import com.studentmanagement.service.GradeService;
import com.studentmanagement.service.StudentService;
import com.studentmanagement.service.SubjectCommentService;
import com.studentmanagement.utils.SubjectResult;


public class StudentController extends BaseTableController<SubjectResult>  {

@FXML
    private TextField studentIdField;
    @FXML
    private Label studentNameLabel;
    @FXML
    private Label studentClassLabel;
    @FXML
    private ComboBox<String> subjectComboBox;
    @FXML
    private TextField gradeField;
    @FXML
    private TextField coefficientField;
    @FXML
    private Button addGradeButton;
    @FXML
    private TextArea commentArea;
    @FXML
    private Button addCommentButton;
    @FXML
    private Button okButton;
    @FXML
    private Button searchButton;
    @FXML
    private TableView<SubjectResult> gradesTable;
    @FXML
    private TableColumn<SubjectResult, String> subjectColumn;
    @FXML
    private TableColumn<SubjectResult, String> gradesColumn;
    @FXML
    private TableColumn<SubjectResult, Number> minAverageColumn;
    @FXML
    private TableColumn<SubjectResult, Number> maxAverageColumn;
    @FXML
    private TableColumn<SubjectResult, Number> studentAverageColumn;
    @FXML
    private TableColumn<SubjectResult, String> commentsColumn;

    private StudentService studentService;
    private GradeService gradeService;
    private SubjectCommentService commentService;
    private Student currentStudent;
    private List<String> subjects = Arrays.asList(
            "Français", "Anglais", "Mathématiques", "Histoire", "Géographie", 
            "Sciences Physiques", "Sciences de la Vie et de la Terre", "Arts Plastiques",
            "Éducation Physique et Sportive", "Technologie", "Musique", "Espagnol", "Allemand"
        );

    public StudentController() {
        // Empty constructor - dependencies will be injected
    }


}