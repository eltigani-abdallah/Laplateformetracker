package com.studentmanagement.utils;

import javafx.scene.control.Alert;
import javafx.scene.control.DialogPane;
import javafx.stage.StageStyle;

//Information alert message
public class AlertUtils {
    //Path to Css file
    private static final String CSS_FILE = "/css/style.css";

    private static void applyCustomStyle(Alert alert){
        try {
            DialogPane dialogPane = alert.getDialogPane();

            //load css sheet
            String css = AlertUtils.class.getResource(CSS_FILE).toExternalForm();
            dialogPane.getStylesheets().add(css);

            //Apply custom style class
            dialogPane.getStyleClass().add("custom-alert");

            //Remove default window decorations for cleaner look
            alert.initStyle(StageStyle.UNDECORATED);
        } catch (Exception e) {
            System.err.println("Could not load CSS file: " + e.getMessage());
        }
    }

    public static void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        applyCustomStyle(alert);
        alert.showAndWait();
    }

//Error alert message   
public static void showError(String  title, String message) {
    Alert alert = new Alert(Alert.AlertType.ERROR);
    alert.setTitle(title);
    alert.setHeaderText(null);
    alert.setContentText(message);
    applyCustomStyle(alert);
    alert.showAndWait();
}

//Warning alert message
public static void showWarning(String title, String message){
    Alert alert = new Alert(Alert.AlertType.WARNING);
    alert.setTitle(title);
    alert.setHeaderText(null);
    alert.setContentText(message);
    applyCustomStyle(alert);
    alert.showAndWait();
}

//Information alert message
public static void showInformation(String title, String message){
    Alert alert = new Alert(Alert.AlertType.INFORMATION);
    alert.setTitle(title);
    alert.setHeaderText(null);
    alert.setContentText(message);
    applyCustomStyle(alert);
    alert.showAndWait();
}

}
