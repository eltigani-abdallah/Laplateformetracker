package com.studentmanagement.utils;

import javafx.scene.control.TextField;

public class GradeValidator {
    public static class ValidationResult{
        private boolean valid;
        private String errorMessage;
        private Object focusField;

        public ValidationResult(boolean valid, String errorMessage, Object focusField){
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

        public Object getFocusField(){
            return focusField;
        }
    }
    
    //Validate grade and coefficient fields
    public static ValidationResult validateGradeInput(TextField gradeField, TextField coefficientField){
        String gradeText = gradeField.getText().trim();
        String coeffText = coefficientField.getText().trim();
        //Check if fields are empty
        if(gradeText.isEmpty() && coeffText.isEmpty()){
            return new ValidationResult(false, "Tu n'as rien modifié.\nSi tu veux quitter cette fenêtre,\nclique sur J'annule.", null);
        }
        if(gradeText.isEmpty()){
            return new ValidationResult(false, "La note ne peut pas être vide", gradeField);
        }
        if(coeffText.isEmpty()){
            return new ValidationResult(false, "Le coefficient ne peut pas être vide.", coefficientField);
        }
        //Validate grade
        try{
            double grade = Double.parseDouble(gradeText);
            if(grade < 0 || grade > 20) {
                return new ValidationResult(false, "La note doit être comprise entre 0 et 20.", gradeField);
            }
        } catch(NumberFormatException e){
            return new ValidationResult(false, "La note doit être un nombre valide.", gradeField);
        }
        //Validate coefficient
        try {
            double coeff = Double.parseDouble(coeffText);
            if(coeff < 0 || coeff > 5 ){
                return new ValidationResult(false, "Le coefficient doit être compris entre 0 et 5.", coefficientField);
            }
        }catch (NumberFormatException e){
            return new ValidationResult(false, "Le coefficient doit être un nombre valide.", coefficientField);
        }
        return new ValidationResult(true, null, null);
    }
}
