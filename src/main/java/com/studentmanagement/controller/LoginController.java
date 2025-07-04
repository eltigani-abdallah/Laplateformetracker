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
   
   @FXML
   private void handleLogin(){
        String username = textFieldUsername.getText().trim();
        String password = textFieldPassword.getText().trim();

        if (username.isEmpty() || password.isEmpty()){
            AlertUtils.showError("Erreur", "Veuillez remplir tous les champs.");
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
            AlertUtils.showError("Erreur", "Une erreur est survenue lors de la connextion: "+ e.getMessage()); 
        }
   }

   @FXML
   private void handleRegister(){
        showRegisterView();
   }

   private void showRegisterView() {
        try {
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
            AlertUtils.showError("Erreur", "Impossible de charger la page d'inscription: " + e.getMessage());
        }
   }

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
        AlertUtils.showError("Erreur", "Impossible de charger la vue principale : " + e.getMessage());
    }
   }
}