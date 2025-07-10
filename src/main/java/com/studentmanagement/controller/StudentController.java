package com.studentmanagement.controller;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import java.net.URL;
import java.util.Arrays;
import java.util.List;
import java.util.ResourceBundle;

import com.studentmanagement.model.Grade;
import com.studentmanagement.model.Student;
import com.studentmanagement.service.GradeService;
import com.studentmanagement.service.StudentService;
import com.studentmanagement.service.SubjectCommentService;
import com.studentmanagement.utils.AlertUtils;
import com.studentmanagement.utils.GradeValidator;
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

    @FXML
    @Override
    protected void initialize(){
        //Initialize services
        studentService = new StudentService();
        gradeService = new GradeService();
        commentService = new SubjectCommentService();

        //Set up subject combo box
        subjectComboBox.setItems(FXCollections.observableArrayList(subjects));

        //Rename dataTable reference to match FXML
        dataTable = gradesTable;

        //Call parent initialization
        super.initialize();

        //Disable grade adding until a student is loaded
        disableGradeControls(true);
    }

    @Override
    protected void setupTableColumns() {
        //Configure columns
        subjectColumn.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getSubject()));

        //Grades column with checkbox for each grade
        gradesColumn.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getGrades()));
        gradesColumn.setCellFactory(column -> new TableCell<SubjectResult, String>() {
            @Override
            protected void updateItem(String item, boolean empty){
                super.updateItem(item, empty); 
                if(empty || item == null || item.isEmpty()){
                    setGraphic(null);
                    setText(null);
                    return;
                }
                VBox container = new VBox(5);
                
                //Split grades string into individual grades
                String[] gradesArray = item.split(", ");
                for (String gradeStr : gradesArray){
                    HBox gradeBox = new HBox(10);
                    gradeBox.setAlignment(Pos.CENTER_LEFT);
                    
                    //Create checkbox for each grade
                    CheckBox checkBox = new CheckBox();
                    Label gradeLabel = new Label(gradeStr);
                    
                    //Add edit button
                    Button editButton = new Button("Je modifie");
                    editButton.setVisible(false);
                    
                    //Add delete button
                    Button deleteButton = new Button("Je supprime");
                    deleteButton.setVisible(false);
                    
                    //Show/hide buttons when checkbox is selected
                    checkBox.selectedProperty().addListener((obs, oldVal, newVal) -> {
                        editButton.setVisible(newVal);
                        deleteButton.setVisible(newVal);
                    });
                    
                    //Setup edit action
                    editButton.setOnAction(e -> {
                        SubjectResult result = getTableView().getItems().get(getIndex());
                        showGradeEditDialog(result.getSubject(), gradeStr);
                    });
                    
                    //Setup delete action
                    deleteButton.setOnAction(e -> {
                        SubjectResult result = getTableView().getItems().get(getIndex());
                        showGradeDeleteConfirmation(result.getSubject(), gradeStr);
                    });
                    
                    gradeBox.getChildren().addAll(checkBox, gradeLabel, editButton, deleteButton);
                    container.getChildren().add(gradeBox);
                }
                
                setGraphic(container);
                setText(null);
            }
        });
        
        minAverageColumn.setCellValueFactory(data -> 
            new SimpleDoubleProperty(data.getValue().getClassMinAverage()));
        
        maxAverageColumn.setCellValueFactory(data -> 
            new SimpleDoubleProperty(data.getValue().getClassMaxAverage()));
        
        studentAverageColumn.setCellValueFactory(data -> 
            new SimpleDoubleProperty(data.getValue().getStudentAverage()));
        
        //Comments column with checkbox for editing
        commentsColumn.setCellValueFactory(data -> 
            new SimpleStringProperty(data.getValue().getTeacherComment()));
        commentsColumn.setCellFactory(column -> new TableCell<SubjectResult, String>() {
            @Override
            protected void updateItem(String item, boolean empty) {
                super.updateItem(item, empty);
                
                if (empty) {
                    setGraphic(null);
                    setText(null);
                    return;
                }
                
                HBox container = new HBox(10);
                container.setAlignment(Pos.CENTER_LEFT);
                
                //Display comment
                Label commentLabel = new Label(item != null ? item : "");
                
                //Create checkbox for editing
                CheckBox checkBox = new CheckBox();
                
                //Add edit button
                Button editButton = new Button("Je modifie");
                editButton.setVisible(false);
                
                //Add delete button
                Button deleteButton = new Button("Je supprime");
                deleteButton.setVisible(false);
                
                //Show/hide buttons when checkbox is selected
                checkBox.selectedProperty().addListener((obs, oldVal, newVal) -> {
                    editButton.setVisible(newVal);
                    deleteButton.setVisible(newVal);
                });
                
                //Setup edit action
                editButton.setOnAction(e -> {
                    SubjectResult result = getTableView().getItems().get(getIndex());
                    showCommentEditDialog(result.getSubject(), result.getTeacherComment());
                });
                
                //Setup delete action
                deleteButton.setOnAction(e -> {
                    SubjectResult result = getTableView().getItems().get(getIndex());
                    showCommentDeleteConfirmation(result.getSubject());
                });
                
                container.getChildren().addAll(checkBox, commentLabel, editButton, deleteButton);
                setGraphic(container);
                setText(null);
            }
        });
    }

 @FXML
    protected void handleLoadStudent(){
        try{
            String studentIdText = studentIdField.getText().trim();
            if(studentIdText.isEmpty()){
                AlertUtils.showError("Erreur", "Tu dois remplir l'ID d'étudiant !");
                return;
            }
            try{
                Long studentId = Long.parseLong(studentIdText);
                currentStudent = studentService.getStudentByID(studentId);
                if(currentStudent == null){
                    AlertUtils.showError("Erreur", "Aucun étudiant trouvé avec cet ID.");
                    disableGradeControls(true);
                    return;
                }
                
                //Update UI with student info
                studentNameLabel.setText("Nom de l'étudiant : " + currentStudent.getFullName());
                studentClassLabel.setText("Classe : " + currentStudent.getStudentClassName());
                
                //Enable grade controls
                disableGradeControls(false);
                
                //Load student grades
                refreshTable();
                
            } catch(NumberFormatException e){
                AlertUtils.showError("Erreur", "L'ID étudiant doit être un nombre entier.");
            }
            
        } catch (Exception e){
            AlertUtils.showError("Erreur", "Une erreur est survenue lors du chargement de l'étudiant :\n" + e.getMessage());
        }
    }

    @FXML
    protected void handleAddGrade(){
        try{
            String subject = subjectComboBox.getValue();
            if (subject == null || subject.isEmpty()) {
                AlertUtils.showError("Erreur", "Tu dois sélectionner une matière!");
                return;
            }
        
            //Validate input using the validator
            GradeValidator.ValidationResult result = GradeValidator.validateGradeInput(gradeField, coefficientField);
            if(!result.isValid()){
                AlertUtils.showError("Erreur", result.getErrorMessage());
                if(result.getFocusField() != null){
                    ((TextField)result.getFocusField()).requestFocus();
                }
                return;
            }
            //Create and save grade using the validator
            Grade newGrade = GradeValidator.createGradeFromFields(
                currentStudent.getStudentId(), subject, gradeField, coefficientField);
            gradeService.saveGrade(newGrade);

            //Clear fields and refresh
            gradeField.clear();
            coefficientField.clear();
            refreshTable();
            AlertUtils.showInformation("Succès", "La note a été ajoutée avec succès.");
        } catch(Exception e){
            AlertUtils.showError("Erreur", "Une erreur est survenue lors de l'ajout de la note :\n" + e.getMessage());
        }
    }

    //Disable all ui fields buttons and text area until user enter student ID
    private void disableGradeControls(boolean disable){
        subjectComboBox.setDisable(disable);
        gradeField.setDisable(disable);
        coefficientField.setDisable(disable);
        addGradeButton.setDisable(disable);
        commentArea.setDisable(disable);
        addCommentButton.setDisable(disable);
        searchButton.setDisable(disable);
        exportButton.setDisable(disable);
    }

}