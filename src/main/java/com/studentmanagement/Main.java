package com.studentmanagement;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

public class Main extends Application {
   
    @Override
    public void start(Stage primaryStage) throws Exception {
        // load tab.fxml
        Parent root = FXMLLoader.load(getClass().getResource("/fxml/tab.fxml"));
       
        Image icon = new Image(getClass().getResourceAsStream("/images/icon.png"));
        primaryStage.getIcons().add(icon);

        // Config scene
        Scene scene = new Scene(root);
       
        // Add CSS 
        scene.getStylesheets().add(getClass().getResource("/css/style.css").toExternalForm());
       
        // Config main window
        primaryStage.setTitle("ÉDUSYS");
        primaryStage.setScene(scene);
        primaryStage.show();
    }
   
    public static void main(String[] args) {
        // Launch JavaFX App
        launch(args);
    }
}