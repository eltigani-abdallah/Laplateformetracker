package com.studentmanagement.controller;

import com.studentmanagement.service.AuthenticationService;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import com.studentmanagement.utils.AlertUtils;
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
        String username = textFieldUsername.getText().trim();
        String password = textFieldPassword.getText().trim();

        if (username.isEmpty() || password.isEmpty()){
            AlertUtils.showAlert("Erreur", "Veuillez remplir tous les champs.");
            return;
        }

        try {
            boolean loginSucess = authService.authenticate(username, password);
            if (loginSucess){
                showMainView();
            } else {
                AlertUtils.showAlert("Erreur de connection", "Nom d'utilisateur ou mot de passe incorect.");               
            }
        } catch (Exception e) {
            AlertUtils.showAlert("Erreur", "Une erreur est survenue lors de la connextion: "+ e.getMessage()); 
        }
   }

   @FXML
   private void handleRegister(){
        showRegisterView();
   }

   private void showRegisterView() {

   }

   private void showMainView(){
    
   }
}