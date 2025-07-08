package com.studentmanagement.utils;

import com.studentmanagement.model.Student;
import javafx.scene.control.TextField;

public class StudentValidator {
    
    public static class ValidationResult{
        private boolean valid;
        private String errorMessage;
        private TextField focusField;

        public ValidationResult(boolean valid, String erroMessage, TextField focusField){
            this.valid = valid;
            this.errorMessage = erroMessage;
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
}
