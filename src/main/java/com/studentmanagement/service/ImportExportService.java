package com.studentmanagement.service;

import java.util.*;
import java.io.*;
//import com.opencsv.*;

import com.studentmanagement.utils.CSVHandler;
import com.studentmanagement.model.Student;


class ImportExportService{
    CSVHandler csvHandler= new CSVHandler();


    public  ImportExportService(){

    }







    void exportToCSV(List<Student> studentList){
        try(PrintWriter writer= new PrintWriter(new FileWriter("export.csv"))) {
            writer.println("id, first_name, last_name, age, grade");

            for (Student student : studentList) {
                String line = csvHandler.formatStudentToCSV(student);
                writer.println(line);
            }

            System.out.println("exported to export.csv successfully.");
        } catch (IOException e){
            e.printStackTrace();
        }

    }


        try(PrintWriter writer= new PrintWriter(new FileWriter("export.csv"))) {
            writer.println("id, first_name, last_name, age, grade");

            for (Student student : studentList) {
                String line = csvHandler.formatStudentToCSV(student);
                writer.println(line);
            }

            System.out.println("exported to export.csv successfully.");
        } catch (IOException e){
            e.printStackTrace();
        }

    }


}