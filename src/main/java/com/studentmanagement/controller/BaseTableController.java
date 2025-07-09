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
    protected TableView<T> dataTable;
    @FXML
    protected Pagination pagination;

    protected ImportExportService importExportService;
    protected final int ROWS_PER_PAGE = 15;
}
