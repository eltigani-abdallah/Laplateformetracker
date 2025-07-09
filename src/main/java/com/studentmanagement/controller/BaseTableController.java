package com.studentmanagement.controller;

import com.studentmanagement.service.ImportExportService;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Pagination;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;

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

    }

    //Loads initial data
    private void loadInitialData(){

    }

    //Handles search
    @FXML
    protected void handleSearch(){

    }

    //Handles CSV export
    @FXML
    protected void handleExport(){

    }

    //loads a page of data
    protected void loadDataPage(int pageIndex){

    }

    //Calculates the total number of pages
    protected int calculatePageCount(){
        return 1;
    }

    //=========== ABSTRACT METHODS TO IMPLEMENT =======
    //Sets up table columns
    protected abstract void setupTableColumns();

    //Handles import functionality
    protected abstract void handleImport();

}
