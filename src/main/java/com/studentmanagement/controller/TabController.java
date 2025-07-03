package com.studentmanagement.controller;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import java.net.URL;
import java.util.ResourceBundle;

public class TabController implements Initializable {

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        // Initialize the controller
        System.out.println("TabController initialized");
    }
    

    @FXML
    private void handleButtonAction() {
        System.out.println("Button clicked!");
    }
}