package com.studentmanagement.utils;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

//Utility class for managing scenes
public class SceneUtils {
    
    public static <T> T changeScene(Stage stage, String fxmlPath, String title) throws Exception {
        //Load the new view
        FXMLLoader loader = new FXMLLoader(SceneUtils.class.getResource(fxmlPath));
        Parent root = loader.load();
        
        //Retrieve the current scene or create a new one
        Scene currentScene = stage.getScene();
        if (currentScene == null) {

            currentScene = new Scene(root);
            stage.setScene(currentScene);
        } else {
            //Replace the content of the existing scene
            currentScene.setRoot(root);
        }
        
        // Set the title
        stage.setTitle(title);
        
        // Return the controller
        return loader.getController();
    }
}