package com.studentmanagement.utils;

import java.util.List;
import java.util.function.Consumer;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

//Utilities for creating standardized dialog windows
public class DialogUtils {
    //Represents an input field in a dialog window
    public static class DialogField {
        private String label;
        private TextField textField;
        private String initialValue;

        public DialogField(String label, String initialValue){
            this.label = label;
            this.initialValue = initialValue !=null ? initialValue: "";
            this.textField = new TextField(this.initialValue);
        }

        public String getLabel(){return label; }
        public TextField getTextField(){return textField; }
        public String getValue(){return textField.getText().trim(); }
        public void setValue(String value){textField.setText(value);}
        public void focus(){
            textField.selectAll();
            textField.requestFocus();
        }
    }

    //Creates a generic edit dialog window with custom validation
    public static void showEditDialog(String title, List<DialogField> fields, 
                                     Consumer<List<DialogField>> onSave,
                                     java.util.function.Function<List<DialogField>, String> validator) {
        // Create a new dialog window
        Stage dialogStage = new Stage();
        dialogStage.setTitle(title);
        dialogStage.initModality(Modality.WINDOW_MODAL);
        dialogStage.setResizable(false);
        
        // Create window content
        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(20, 20, 20, 20));
        
        // Add fields
        for (int i = 0; i < fields.size(); i++) {
            DialogField field = fields.get(i);
            grid.add(new Label(field.getLabel()), 0, i);
            grid.add(field.getTextField(), 1, i);
        }
        
        // Buttons
        Button saveButton = new Button("J'enregistre");
        Button cancelButton = new Button("J'annule");
        
        HBox buttonBox = new HBox(10);
        buttonBox.setAlignment(Pos.CENTER_RIGHT);
        buttonBox.getChildren().addAll(saveButton, cancelButton);
        grid.add(buttonBox, 0, fields.size(), 2, 1);
        
        // Button actions
        saveButton.setOnAction(e -> {
            try {
                // Custom validation if provided
                if (validator != null) {
                    String validationError = validator.apply(fields);
                    if (validationError != null) {
                        AlertUtils.showError("Erreur de validation", validationError);
                        return;
                    }
                }
                // Call save function
                onSave.accept(fields);
                dialogStage.close();
            } catch(Exception ex) {
                AlertUtils.showError("Erreur", "Une erreur est survenue :\n " + ex.getMessage());
            }
        });

        cancelButton.setOnAction(e -> dialogStage.close());
        // Configure scene and display window
        Scene scene = new Scene(grid);
        dialogStage.setScene(scene);
        dialogStage.showAndWait();
    }
    
    //Shows a delete confirmation window
    public static void showDeleteConfirmation(String title, String message, Runnable onConfirm) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirmation de suppression");
        alert.setHeaderText(title);
        alert.setContentText(message);
        
        alert.showAndWait().ifPresent(response -> {
            if (response == ButtonType.OK) {
                try {
                    onConfirm.run();
                } catch (Exception e) {
                    AlertUtils.showError("Erreur", "Une erreur est survenue pendant la suppression :\n " + e.getMessage());
                }
            }
        });
    }
    

}
