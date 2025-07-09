package com.studentmanagement.utils;

import com.studentmanagement.model.Student;

import java.io.*;
import java.util.List;

public class CSVHandler{
    char delimiter=',';


    public CSVHandler(){

    }

    public CSVHandler(char delimiter){
        this.delimiter=delimiter;
    }



    /**
     * takes in a list of students that came up in the search and exports that into csv format
      * @param searchResults List<Student>
     */
    public void exportStudentSearchResults(List<Student> searchResults){
        try(PrintWriter writer= new PrintWriter(new FileWriter("student_export.csv"))){
            writer.println("id,first_name,last_name,age,grade");

            for (Student student:searchResults){
                writer.println(formatStudentToCSV(student));
            }

            System.out.println("exported students to student_export.csv");
        } catch(IOException e){
            System.err.println("Export failed :( "+e.getMessage());
        }
    }


    /**
     * Validate if the csv path required exists, and if it ends with .csv for format
     * @param CSVPath the path of the file to be validated
     * @return true or false. it's a boolean what did you expect?
     */
    public boolean validateCSVFormat(String CSVPath){
        return CSVPath!=null &&  CSVPath.endsWith(".csv") && new File(CSVPath).exists();
    }

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
                        Long.parseLong(parts[0]),
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
                Long.parseLong(parts[0]),
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
                studentToFormat.getStudentId(),
                studentToFormat.getFirstName(),
                studentToFormat.getLastName(),
                studentToFormat.getAge(),
                studentToFormat.getAverageGrade()
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