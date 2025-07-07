package com.studentmanagement.controller;

import com.studentmanagement.model.Student;
import com.studentmanagement.service.ImportExportService;
import com.studentmanagement.service.StudentService;
import com.studentmanagement.utils.AlertUtils;
import com.studentmanagement.utils.SearchCriteria;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
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
        try {
            FileChooser fileChooser = new FileChooser();
            fileChooser.setTitle("Exporter la recherche");
            fileChooser.getExtensionFilters().add(
                new FileChooser.ExtensionFilter("Fichiers CSV", "*.csv")
            );
            Stage stage = (Stage) exportButton.getScene().getWindow();
            File selectedFile = fileChooser.showSaveDialog(stage);

            if (selectedFile != null){
                //Get the actual search criteria
                SearchCriteria criteria = new SearchCriteria(searchField.getText());

                //To export all the search results of the actual search
                criteria.setPageSize(Integer.MAX_VALUE);

                //To get all the students to export
                List<Student> studentsToExport = studentService.searchStudents(criteria);
                 
                //Use export service
                importExportService.exportToCSV(studentsToExport, selectedFile);

                //Show success message
                AlertUtils.showInformation("Export réussi", "Les données ont été exportées avec succès vers\n" + selectedFile.getName());
            }
        } catch (Exception e){
            AlertUtils.showError("Erreur d'exportation", "Une erreur est survenue lors de l'exportation " + e.getMessage());
        }
    }

    @FXML
    private void handleAddStudent(){
        try {
            //get the field values
            String firstName = firstNameField.getText().trim();
            String lastName = lastNameField.getText().trim();
            String ageText = ageField.getText().trim();
            String className = classNameField.getText().trim();

            //validate the base
            if (firstName.isEmpty() || lastName.isEmpty() || ageText.isEmpty() || className.isEmpty()){
                AlertUtils.showError("Erreur de saisie", "Tous les champs doivent être remplis !");
                return;
            }

            //Convert age
            int age;
            try {
                age = Integer.parseInt(ageText);
                if (age <= 3 || age >= 150){
                    AlertUtils.showError("Erreur de saisie", "Rapelle toi !\nNous n'acceptons pas les étudiants de moins de 3 ans\nni ceux de plus de 150 ans !");
                    return;
                }
            } catch (NumberFormatException e){
                    AlertUtils.showError("Erreur de format", "L'âge doit être un nombre entier\n5 ans 1/2 c'est pas possible !");
                    return;
                }
            
                //Create and add a student
                Student newStudent = new Student();
                newStudent.setFirstName(firstName);
                newStudent.setLastName(lastName);
                newStudent.setAge(age);
                newStudent.setClassName(className);

                //Use the createSudent method from StudentService
                studentService.createStudent(newStudent);

                //Refresh the table empty the fields
                loadStudentsPage(pagination.getCurrentPageIndex());
                pagination.setPageCount(calculatePageCount());
                clearAddForm();

                //Show a success message
                AlertUtils.showInformation("Succès", "L'étudiant a été ajouté avec succès");                
        } catch (Exception e){
            AlertUtils.showError("Erreur", "Une erreur est survenue lors de l'ajout : " + e.getMessage());
        }
    }

    private void clearAddForm(){
        firstNameField.clear();
        lastNameField.clear();
        ageField.clear();
        classNameField.clear();
    }

    private void loadStudentsPage(int pageIndex){
        try {
            //Create a SearchCriteria object for pagination
            SearchCriteria criteria = new SearchCriteria(searchField.getText());
            criteria.setPageNumber(pageIndex + 1); // because pageIndex begins at 0
            criteria.setPageSize(ROWS_PER_PAGE);

            //Add sort information if available
            if (!studentTable.getSortOrder().isEmpty()){
                TableColumn<Student, ?> sortColumn = studentTable.getSortOrder().get(0);
                String sortField = sortColumn.getId().replace("Column", "");
                String sortDirection = sortColumn.getSortType().toString();
                criteria.setSortField(sortField);
                criteria.setSortDirection(sortDirection);
            }

            //Get students for the current page
            List<Student> pageItems = studentService.searchStudents(criteria);

            //Update the table
            ObservableList<Student> students = FXCollections.observableArrayList(pageItems);
            studentTable.setItems(students);
        } catch (Exception e){
            AlertUtils.showError("Erreur", "Impossible de charger les données : " + e.getMessage());
        }
    }

    private int calculatePageCount(){
        try {
            //Create an SearchCriteria object to obtain the total number
            SearchCriteria criteria = new SearchCriteria(searchField.getText());
            int totalStudents = studentService.getTotalStudents(criteria);
            return (totalStudents + ROWS_PER_PAGE -1 ) / ROWS_PER_PAGE;
        } catch (Exception e){
            AlertUtils.showError("Erreur", "Impossible de calculer le nombre de pages " + e.getMessage());
        }
        return 1;
    }

    private void showEditDialog(Student student){

    }

    private void showDeleteConfirmation(Student student){

    }

}