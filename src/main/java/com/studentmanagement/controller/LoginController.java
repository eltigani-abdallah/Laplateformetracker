package com.studentmanagement.controller;

import com.studentmanagement.service.AuthenticationService;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.control.Button;


public class LoginController {

    @FXML
    private TextField textFieldUsername;

    @FXML
    private TextField textFieldPassword;

    @FXML
    private Button buttonLogin;

    @FXML
    private Button buttonRegister;

   private AuthenticationService authService;

   public LoginController(){
    this.authService = new AuthenticationService();
   }

   @FXML
   private void initialize(){
    //Init controller
    setupEventHandlers();
   }

   private void setupEventHandlers(){
    //Add events to buttons
    buttonLogin.setOnAction(event -> handleLogin());
    buttonRegister.setOnAction(event -> handleRegister());
   }
   
   @FXML
   private void handleLogin(){

   }

   @FXML
   private void handleRegister(){
    
   }
}