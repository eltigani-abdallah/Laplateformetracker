package com.studentmanagement.utils;

import com.studentmanagement.model.Student;
import javafx.scene.control.TextField;

public class StudentValidator {
    
    public static class ValidationResult{
        private boolean valid;
        private String errorMessage;
        private TextField focusField;

        public ValidationResult(boolean valid, String errorMessage, TextField focusField){
            this.valid = valid;
            this.errorMessage = errorMessage;
            this.focusField = focusField;
        }

        public boolean isValid(){
            return valid;
        }

        public String getErrorMessage(){
            return errorMessage;
        }

        public TextField getFocusField(){
            return focusField;
        }
    }

    //Validate input fields for student creation
    public static ValidationResult validationForCreation(TextField firstNameField, TextField lastNameField, TextField ageField, TextField classNameField){
        //Verify if all fields are full
        String firstName = firstNameField.getText().trim();
        String lastName = lastNameField.getText().trim();
        String ageText = ageField.getText().trim();
        String className = classNameField.getText().trim();
        
        if(firstName.isEmpty() || lastName.isEmpty() || ageText.isEmpty() || className.isEmpty()){
            return new ValidationResult(false, "Tous les champs doivent être remplis !", firstNameField);
        }
        //validate each field
        ValidationResult result = validateFirstName(firstName, firstNameField);
        if(!result.isValid()) return result;
        result = validateLastName(lastName, lastNameField);
        if(!result.isValid()) return result;
        result = validateAge(ageText, ageField);
        if(!result.isValid()) return result;
        result = validateClassName(className, classNameField);
        if(!result.isValid()) return result;

        return new ValidationResult(true, null, null);
    }

    //Validate and apply changes to a Student object
    public static ValidationResult validateAndApplyChanges(Student student, TextField firstNameField, TextField lastNameField, TextField ageField, TextField classNameField){
        boolean hasChanges = false;
        //Validate and apply changes for first name
        String firstName = firstNameField.getText().trim();
        if(!firstName.isEmpty()){
            ValidationResult result = validateFirstName(firstName, firstNameField);
            if(!result.isValid()) return result;
            firstName = formatName(firstName);
            if(!firstName.equals(student.getFirstName())){
                student.setFirstName(firstName);
                hasChanges = true;
            }
        }
        //Validate and apply changes for name
        String lastName = lastNameField.getText().trim();
        if(!lastName.isEmpty()){
            ValidationResult result = validateLastName(lastName, lastNameField);
            if(!result.isValid()) return result;
            lastName = formatName(lastName);
            if(!lastName.equals(student.getLastName())){
                student.setLastName(lastName);
                hasChanges = true;
            }
        }
        //Validate and apply changes for age
        String ageText = ageField.getText().trim();
        if(!ageText.isEmpty()){
            ValidationResult result = validateAge(ageText, ageField);
            if(!result.isValid()) return result;
            int age = Integer.parseInt(ageText);
            if(age != student.getAge()){
                student.setAge(age);
                hasChanges = true;
            }
        }
        //Validate and apply changes for class name
        String className = classNameField.getText().trim();
        if(!className.isEmpty()){
            ValidationResult result = validateClassName(className, classNameField);
            if(!result.isValid()) return result;
            className = formatClassName(className);
            if(className.equals(student.getClassName())){
                student.setClassName(className);
                hasChanges = true;
            }
        }
        if(!hasChanges){
            return new ValidationResult(false, "Tu n'as rien modifié !\nSi tu veux fermer cette fenêtre, clique sur Annuler", null);
        }
        return new ValidationResult(true, null, null);
    }
   
    private static ValidationResult validateFirstName(String firstName, TextField field) {
        return new ValidationResult(true, null, null);
    }

    private static ValidationResult validateLastName(String lastName, TextField field) {
        return new ValidationResult(true, null, null);
    }

    private static ValidationResult validateAge(String ageText, TextField field) {
        return new ValidationResult(true, null, null);
    }

    private static ValidationResult validateClassName(String ClassName, TextField field) {
        return new ValidationResult(true, null, null);
    }

    private static String formatName(String name){

    }

    private static String formatClassName(String className){

    }

}