package com.studentmanagement.controller;

import com.studentmanagement.model.User;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;

import java.net.URL;
import java.util.ResourceBundle;

public class WelcomeController implements Initializable{

    @FXML
    private Label usernameLabel;
    
    private User currentUser;

    @Override
    public void initialize(URL location, ResourceBundle resources){
    }
    
    public void setCurrentUser(User user){
        this.currentUser = user;
        updateUI();
    }
    
    private void updateUI(){
        if (currentUser != null){
            usernameLabel.setText(currentUser.getUsername());
        } else{
            usernameLabel.setText("[Utilisateur non connecté]");
        }
    }
}