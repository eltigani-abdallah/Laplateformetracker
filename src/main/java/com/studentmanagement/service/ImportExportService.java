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

    /**
     * import a list of students from a csv file
     * @param filePath path of file to be imported
     * @return
     */
    public List<Student> importFromCSV(String filePath){
        try {
            ColumnPositionMappingStrategy<Student> strategy = new ColumnPositionMappingStrategy<>();
            strategy.setType(Student.class);
            strategy.setColumnMapping("first_name", "last_name", "age"); // CSV column order

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


    /**
     * export a list of students to a CSV file
     * @param studentList List<Student> of students to export
     */
    public void exportToCSV(List<Student> studentList){
        try(PrintWriter writer= new PrintWriter(new FileWriter("export.csv"))) {
            writer.println("first_name,last_name,age");

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