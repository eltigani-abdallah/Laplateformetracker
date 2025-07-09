package com.studentmanagement.controller;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import java.net.URL;
import java.util.ResourceBundle;

//minimum version just to compile
public class StudentController implements Initializable {

    @FXML
    private TextField studentIdField;
    @FXML
    private Button okButton;
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
    private TextField searchField;
    @FXML
    private Button sortButton;
    @FXML
    private TableView<?> gradesTable;
    @FXML
    private TableColumn<?, ?> subjectColumn;
    @FXML
    private TableColumn<?, ?> gradesColumn;
    @FXML
    private TableColumn<?, ?> minAverageColumn;
    @FXML
    private TableColumn<?, ?> maxAverageColumn;
    @FXML
    private TableColumn<?, ?> studentAverageColumn;
    @FXML
    private TableColumn<?, ?> commentsColumn;
    @FXML
    private Pagination pagination;
    @FXML
    private Button exportButton;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        // Init combo box with subjects
        ObservableList<String> subjects = FXCollections.observableArrayList(
            "Français", "Anglais", "Mathématiques", "Histoire", "Géographie", 
            "Sciences Physiques", "Sciences de la Vie et de la Terre", "Arts Plastiques",
            "Éducation Physique et Sportive", "Technologie", "Musique", "Espagnol", "Allemand"
        );
        subjectComboBox.setItems(subjects);
    }

    @FXML
    private void handleLoadStudent() {
        String studentId = studentIdField.getText();
        if (studentId != null && !studentId.trim().isEmpty()) {

        }
    }

    @FXML
    private void handleAddGrade() {
        String subject = subjectComboBox.getValue();
        String grade = gradeField.getText();
        String coefficient = coefficientField.getText();
        
        if (subject != null && !grade.trim().isEmpty() && !coefficient.trim().isEmpty()) {
            gradeField.clear();
            coefficientField.clear();
            subjectComboBox.setValue(null);
        }
    }

    @FXML
    private void handleAddComment() {
        String comment = commentArea.getText();
        if (comment != null && !comment.trim().isEmpty()) {
            commentArea.clear();
        }
    }

    @FXML
    private void handleSort() {

    }

    @FXML
    private void handleExport() {

    }
}