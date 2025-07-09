package com.studentmanagement.service;

import java.util.*;
import java.io.*;
import com.opencsv.bean.CsvToBeanBuilder;
import com.opencsv.bean.ColumnPositionMappingStrategy;
import java.io.FileReader;

import com.studentmanagement.utils.CSVHandler;
import com.studentmanagement.model.Student;


public class ImportExportService{
    CSVHandler csvHandler= new CSVHandler();


    public  ImportExportService(){

    }

    public List<Student> importFromCSV(String filePath){
        try {
            ColumnPositionMappingStrategy<Student> strategy = new ColumnPositionMappingStrategy<>();
            strategy.setType(Student.class);
            strategy.setColumnMapping("id", "firstName", "lastName", "age", "grade"); // CSV column order

            return new CsvToBeanBuilder<Student>(new FileReader(filePath))
                    .withMappingStrategy(strategy)
                    .withSkipLines(1) // skip header
                    .withIgnoreLeadingWhiteSpace(true)
                    .build()
                    .parse();

        } catch (Exception e) {
            e.printStackTrace();
            return Collections.emptyList();
        }
    }





    public void exportToCSV(List<Student> studentList){
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