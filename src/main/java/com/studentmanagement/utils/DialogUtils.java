package com.studentmanagement.utils;

import javafx.scene.control.TextField;

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
}
