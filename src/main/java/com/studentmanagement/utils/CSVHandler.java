package com.studentmanagement.utils;

import com.studentmanagement.model.Student;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;

public class CSVHandler{
    char delimiter=',';


    public CSVHandler(){

    }

//    public CSVHandler(char delimiter){
//
//    }
//
//    public void exportGeneralSearchResults(List searchResults){
//
//    }
//
//    public void exportStudentSearchResults(List searchResults){
//
//    }
//
//    public boolean validateCSVFormat(String CSVPath){
//
//    }

    /**
     * takes in the path to a CSV file and uses that to parse student info
     * @param CSVPath path to the csv file that should be used
     * @return Student object
     */
    public Student parseStudentFromCSV(String CSVPath){
        try(BufferedReader br=new BufferedReader(new FileReader(CSVPath))){
            String line=br.readLine();
            line=br.readLine();

            if (line!=null){
                String[] parts= line.split(String.valueOf(delimiter));
                return new Student(
                        Integer.parseInt(parts[0]),
                        parts[1],
                        parts[2],
                        Integer.parseInt(parts[3]),
                        Double.parseDouble(parts[4])
                );
            }
        } catch (IOException | NumberFormatException e){
            e.printStackTrace();
        }

        System.out.println("failed to parse student from CSV");
        return null;
    }

    public Student parsestudentFromCSVLine(String lineToParse){
        String[] parts=lineToParse.split(String.valueOf(delimiter));
        return new Student(
                Integer.parseInt(parts[0]),
                parts[1],
                parts[2],
                Integer.parseInt(parts[3]),
                Double.parseDouble(parts[4])
        );
    }

    /**
     * formats a student object to be inserted into a String for csv conversion
     * @param studentToFormat Student object to format into string
     * @return String of student info
     */
    public String formatStudentToCSV(Student studentToFormat){
        String studentCSVFormat= String.format("%d,%s,%s,%d,%.2f",
                studentToFormat.getId(),
                studentToFormat.getFirstName(),
                studentToFormat.getLastName(),
                studentToFormat.getAge(),
                studentToFormat.getGrade()
        );
        return studentCSVFormat;
    }
    /**
     * What the frick is a general
    General parseGeneralFromCSV(String){

    }

    String formatGeneralToCSV(General){

    }*/

}