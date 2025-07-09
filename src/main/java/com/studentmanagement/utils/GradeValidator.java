package com.studentmanagement.utils;

import com.studentmanagement.model.Grade;
import com.studentmanagement.model.SubjectComment;
import javafx.scene.control.TextArea;
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

    //Check if values have changed
    public static ValidationResult checkForChanges(double originalGrade, double originalCoeff, TextField gradeField, TextField coefficientField){
        try{
            double newGrade = Double.parseDouble(gradeField.getText().trim());
            double newCoeff = Double.parseDouble(coefficientField.getText().trim());
            if(newGrade == originalGrade && newCoeff == originalCoeff){
                return new ValidationResult(false, "Tu n'as rien modifié.\nSi tu veux quitter cette fenêtre,\nclique sur J'annule.", null);
            }
            return new ValidationResult(true, null, null);
        } catch (NumberFormatException e){
            //This should not happen if validateGradeInput is called first
            return new ValidationResult(false, "Format de nombre invalide.", null);
        }
    }
    
    //Create Grade object from validated fields
    public static Grade createGradeFromFields(Long studentId, String subject, 
                                            TextField gradeField, TextField coefficientField) {
        Grade grade = new Grade();
        grade.setStudentId(studentId);
        grade.setSubject(subject);
        grade.setValue(Double.parseDouble(gradeField.getText().trim()));
        grade.setCoefficient(Double.parseDouble(coefficientField.getText().trim()));
        return grade;
    }
    
    //Create SubjectComment object from validated field
    public static SubjectComment createCommentFromField(Long studentId, String subject, TextArea commentArea) {
        SubjectComment comment = new SubjectComment();
        comment.setStudentId(studentId);
        comment.setSubject(subject);
        comment.setComment(commentArea.getText().trim());
        return comment;
    }
}
