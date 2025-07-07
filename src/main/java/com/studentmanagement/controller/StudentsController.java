package com.studentmanagement.controller;

import com.studentmanagement.model.Student;
import com.studentmanagement.service.ImportExportService;
import com.studentmanagement.service.StudentService;
import com.studentmanagement.utils.AlertUtils;
import com.studentmanagement.utils.SearchCriteria;

import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.util.List;

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

        //load initial data
        loadStudentsPage(0);
    }

    //Config the TableView for displaying student data
    private void setupTableColumns(){
        idColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        firstNameColumn.setCellValueFactory(new PropertyValueFactory<>("firstName"));
        lastNameColumn.setCellValueFactory(new PropertyValueFactory<>("lastName"));
        ageColumn.setCellValueFactory(new PropertyValueFactory<>("age"));
        classNameColumn.setCellValueFactory(new PropertyValueFactory<>("className"));
        averageGradeColumn.setCellValueFactory(new PropertyValueFactory<>("averageGrade"));
    }

    //Config action handlers for buttons
    private void setupButtonHandlers(){
        searchButton.setOnAction(event -> handleSearch());
        importButton.setOnAction(event -> handleImport());
        exportButton.setOnAction(event -> handleExport());
        addButton.setOnAction(event -> handleAddStudent());
    }

    //Enable sorting functionnality 
    private void setupColumnSorting(){
        //Activate sorting for appropriate columns
        idColumn.setSortable(true);
        firstNameColumn.setSortable(true);
        lastNameColumn.setSortable(true);
        ageColumn.setSortable(true);
        classNameColumn.setSortable(true);
        averageGradeColumn.setSortable(true);

        //Config sorting events to update the search
        studentTable.getSortOrder().addListener((javafx.collections.ListChangeListener.Change<? extends TableColumn<Student, ?>> change) -> {
            if (change.next() && !change.wasPermutated() && !studentTable.getSortOrder().isEmpty()){
                //get the sorted column and the direction
                TableColumn<Student, ?> sortColumn = studentTable.getSortOrder().get(0);
                String sortField = sortColumn.getId().replace("Column", "");
                String sortDirection = sortColumn.getSortType().toString();

                //Update search criteria and reload data
                SearchCriteria criteria = new SearchCriteria(searchField.getText());
                criteria.setSortField(sortField);
                criteria.setSortDirection(sortDirection);

                //Reload data with the new sort
                loadStudentsPage(pagination.getCurrentPageIndex());
            }
        });
    }

    //Setter to inject dependancies
    public void setStudentService(StudentService studentService){
        this.studentService = studentService;
    }

    public void setImportExportService(ImportExportService importExportService){
        this.importExportService = importExportService;
    }

    //Create the modify button for editing student data
    private void setupEditColumn(){
        editColumn.setCellFactory(col -> {
            TableCell<Student, Void> cell = new TableCell<>(){
                private final Button editButton = new Button("Modifier");
                {
                    editButton.getStyleClass().add("button");
                    editButton.setMinHeight(30);
                    editButton.setOnAction(event -> {
                        Student student = getTableView().getItems().get(getIndex());
                        showEditDialog(student);
                    });
                }

                @Override
                protected void updateItem(Void item, boolean empty){
                    super.updateItem(item, empty);
                    if (empty){
                        setGraphic(null);
                    } else {
                        setGraphic(editButton);
                    }
                }
            };
            return cell;
        });
    }

    //Create the delete button for deleting student data
    private void setupDeleteColumn(){
        deleteColumn.setCellFactory(col -> {
            TableCell<Student, Void> cell = new TableCell<>(){
                private final Button deleteButton = new Button("Supprimer");
                {
                    deleteButton.getStyleClass().add("button");
                    deleteButton.setMinHeight(30);
                    deleteButton.setOnAction(event -> {
                        Student student = getTableView().getItems().get(getIndex());
                        showDeleteConfirmation(student);
                    });
                }
                @Override
                protected void updateItem(Void item, boolean empty){
                    super.updateItem(item, empty);
                    if (empty){
                        setGraphic(null);
                    } else {
                        setGraphic(deleteButton);
                    }
                }
            };
            return cell;
        });
    }

    @FXML
    private void handleSearch(){
        loadStudentsPage(0); //reload the first page with the search filter
        pagination.setCurrentPageIndex(0);
        pagination.setPageCount(calculatePageCount());
    }

    @FXML
    private void handleImport(){
        try {
            FileChooser fileChooser = new FileChooser();
            fileChooser.setTitle("Importer la recherche");
            fileChooser.getExtensionFilters().add(
                new FileChooser.ExtensionFilter("Fichiers CSV", "*.csv")
            );

            Stage stage = (Stage) importButton.getScene().getWindow();
            File selectedFile = fileChooser.showOpenDialog(stage);

            if (selectedFile != null){
                //Use the import service
                int importedCount = importExportService.ImportFromCSV(selectedFile);

                //Refesh table
                loadStudentsPage(0);
                pagination.setCurrentPageIndex(0);
                pagination.setPageCount(calculatePageCount());

                //Show a succes message
                AlertUtils.showInformation("Import réussi", importedCount + " étudiant(s) ont été importés avec succès !");
            }
        } catch (Exception e){
            AlertUtils.showError("Erreur d'Importation", "Oups ! Une erreur est survenue lors de l'importation : " + e.getMessage());
        }
    }

    @FXML
    private void handleExport(){

    }

    @FXML
    private void handleAddStudent(){

    }

    private void loadStudentsPage(int pageIndex){

    }

    private int calculatePageCount(){
        return 1;
    }

    private void showEditDialog(Student student){

    }

    private void showDeleteConfirmation(Student student){

    }

}