package com.studentmanagement.controller;

import com.studentmanagement.service.AuthenticationService;

import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.control.Button;

//Controller for the registration page
public class RegisterController {

    @FXML
    private TextField textFieldUsername;

    @FXML
    private TextField textFieldPassword;

    @FXML
    private TextField textFieldConfirmPassword;

    @FXML
    private Button buttonRegister;

    @FXML
    private Button buttonBackToLogin;

    private AuthenticationService authService;

    public RegisterController(){
        //Empty constructor - dependencies will be injected
    }

    @FXML
    private void initialize(){
        //set up event handlers
        setupEventHandlers();
    }

    //Configure event handlers for the buttons
    private void setupEventHandlers(){
        buttonRegister.setOnAction(event -> handleRegister());
        buttonBackToLogin.setOnAction(event -> handleBackToLogin());
    }

    //Handles the registration of a new user
    @FXML
    private void handleRegister(){

    }

    //Handles the return to the login page
    @FXML
    private void handleBackToLogin(){

    }
    
    //Setter for dependency injection
    public void setAuthService(AuthenticationService authService) {
        this.authService = authService;
    }
}