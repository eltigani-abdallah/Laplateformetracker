package com.studentmanagement.controller;

import com.studentmanagement.service.AuthenticationService;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.stage.Stage;
import javafx.scene.Scene;
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
   
   // Handle login
   @FXML
   private void handleLogin(){
        String username = textFieldUsername.getText().trim();
        String password = textFieldPassword.getText().trim();

        if (username.isEmpty() || password.isEmpty()){
            AlertUtils.showError("Erreur", "Remplis tous les champs,\nil en manque surement un !");
            return;
        }

        try {
            boolean loginSucess = authService.authenticate(username, password);
            if (loginSucess){
                clearForm();
                showMainView();
            } else {
                AlertUtils.showInformation("Information", "Ton surnom et/ou ton mot de passe sont incorrects !");    
                textFieldPassword.clear();     
            }
        } catch (Exception e) {
            AlertUtils.showError("Erreur", "Oups !\nUne erreur est survenue lors de la connexion :\n"+ e.getMessage()); 
            textFieldPassword.clear();
        }
   }

   //handle register
   @FXML
   private void handleRegister(){
        showRegisterView();
   }

   //show register view
   private void showRegisterView() {
        try {

            //Clear form before navigating to register
            clearForm();

            //load register view
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/register.fxml"));
            Parent root = loader.load();

            //get the actual stage
            Stage stage = (Stage) buttonRegister.getScene().getWindow();

            //create a new scene
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.setTitle("ÉduSys - Inscription");
            stage.show();
        } catch (Exception e){
            AlertUtils.showError("Erreur", "Oups !\nImpossible de charger la page d'inscription :\n " + e.getMessage());
        }
   }

   //show main view
   private void showMainView(){
    try {
        //load main view
        FXMLLoader Loader = new FXMLLoader(getClass().getResource("/fxml/tab.fxml"));
        Parent root = Loader.load();

        //Get the actual stage
        Stage stage = (Stage) buttonLogin.getScene().getWindow();

        //Create a new scene
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.setTitle("ÉduSys - Gestion des Étudiants");
        stage.show();
    } catch (Exception e){
        AlertUtils.showError("Erreur", "Oups !\nImpossible de charger la vue principale :\n " + e.getMessage());
    }
   }

   //Clear form
   private void clearForm() {
    textFieldUsername.clear();
    textFieldPassword.clear();
   }
}