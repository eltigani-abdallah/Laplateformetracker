package com.studentmanagement.utils;

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
    
}
