package com.studentmanagement.controller;

import java.io.File;
import java.util.List;

import com.studentmanagement.service.ImportExportService;
import com.studentmanagement.utils.AlertUtils;
import com.studentmanagement.utils.SearchCriteria;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Pagination;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

/* Abstract base controller for tables with pagination
 * search and export
 * @param <T> type of entities displayed in the table
 */
public  abstract class BaseTableController<T> {
    
    @FXML
    protected TextField searchField;
    @FXML
    protected Button searchButton;
    @FXML
    protected Button exportButton;
    @FXML
    protected Button importButton;
    @FXML
    protected TableView<T> dataTable;
    @FXML
    protected Pagination pagination;

    protected ImportExportService importExportService;
    protected final int ROWS_PER_PAGE = 15;

    //Common initialization for all table controllers
    @FXML
    protected void initialize(){
        initializeServices();
        setupTableColumns();
        setupPagination();
        setupSearchAndExport();
        setupImport();
        setupColumnSorting();
        loadInitialData();
    }

    //Init required services
    protected void initializeServices(){
        importExportService = new ImportExportService();
    }

    //Set up pagination
    private void setupPagination(){
        pagination.setPageCount(calculatePageCount());
        pagination.currentPageIndexProperty().addListener((obs, oldIndex, newIndex) -> {
            loadDataPage(newIndex.intValue());
        });
    }

    //Set up search and export buttons
    private void setupSearchAndExport(){
        searchButton.setOnAction(event -> handleSearch());
        exportButton.setOnAction(event -> handleExport());
    } 

    //Sets up import button
    private void setupImport(){
        if(importButton !=null){
            importButton.setOnAction(event -> handleImport());
        }
    }

    //Sets up column sorting
    private void setupColumnSorting(){
        dataTable.getSortOrder().addListener((javafx.collections.ListChangeListener.Change<? extends TableColumn<T, ?>> change) ->{
            if(change.next() && !change.wasPermutated() && !dataTable.getSortOrder().isEmpty()){
                loadDataPage(pagination.getCurrentPageIndex());
            }
        });
    }

    //Loads initial data
    private void loadInitialData(){
        loadDataPage(0);
    }

    //Handles search
    @FXML
    protected void handleSearch(){
        loadDataPage(0);
        pagination.setCurrentPageIndex(0);
        pagination.setPageCount(calculatePageCount());
    }

    //Handles CSV export
    @FXML
    protected void handleExport(){
        try{
            FileChooser fileChooser = new FileChooser();
            fileChooser.setTitle("Export des données");
            fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("CSV Files", "*.csv"));

            Stage stage = (Stage) exportButton.getScene().getWindow();
            File selectedFile = fileChooser.showSaveDialog(stage);

            if(selectedFile != null){
                //Get all results from current search
                SearchCriteria criteria = createSearchCriteria();
                criteria.setPageSize(Integer.MAX_VALUE);
                List<T> dataToExport = searchData(criteria);
                exportToCSV(dataToExport, selectedFile);
                
                AlertUtils.showInformation("Succès de l'export", "Les données ont été exportées avec succès dans :\n" + selectedFile.getName());
            }
        } catch (Exception e){
            AlertUtils.showError("Erreur export", "Une erreur est survenue durant l'export : " + e.getMessage());
        }
    }

    //loads a page of data
    protected void loadDataPage(int pageIndex){

    }

    //Calculates the total number of pages
    protected int calculatePageCount(){
        return 1;
    }

    //Creates search criteria based on the user interface
    protected SearchCriteria createSearchCriteria(){
        return new SearchCriteria(searchField.getText());
    }

    //=========== ABSTRACT METHODS TO IMPLEMENT =======
    //Sets up table columns
    protected abstract void setupTableColumns();

    //Searches data according to criteria
    protected abstract List<T> searchData(SearchCriteria criteria);

    //Exports data to a CSV file
    protected abstract void exportToCSV(List<T> data, File file);

    //Handles import functionality
    protected abstract void handleImport();

}
