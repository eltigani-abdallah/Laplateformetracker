package com.studentmanagement.service;

import com.studentmanagement.model.Student;
import com.studentmanagement.utils.CSVHandler;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import java.io.*;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ImportExportServiceTest {

    public ImportExportService service = new ImportExportService();

    @Test
    public void exportToCSV_createsValidFile(@TempDir Path tempDir) throws IOException {

        List<Student> students=List.of(
                new Student("Alice","Wonder",6),
                new Student("Bob","Builder", 5)
        );

        File file = tempDir.resolve("students.csv").toFile();

        try(PrintWriter writer=new PrintWriter(file)){
            writer.println("first_name,last_name,age");
            for(Student student: students){
                writer.println(new CSVHandler().formatStudentToCSV(student));
            }
        }

        List<String> lines=new ArrayList<String>();
        try(BufferedReader reader= new BufferedReader(new FileReader(file))){
            String line;
            while((line=reader.readLine())!=null){
                lines.add(line.trim());
            }
        }

        assertEquals("first_name,last_name,age", lines.get(0));
        assertEquals("Alice,Wonder,6", lines.get(1));
        assertEquals("Bob,Builder,5",lines.get(2));
    }

    @Test
    public void testImportFromCSV_readsCorrectStudents(@TempDir Path tempDir)throws IOException{
        File file = tempDir.resolve("students.csv").toFile();

        try(PrintWriter writer=new PrintWriter(file)){
            writer.println("firstName,lastName,age"); // header
            writer.println("Alice,Wonder,6");
            writer.println("Bob,Builder,5");
        }

        List<Student> imported= service.importFromCSV(file.getAbsolutePath());

        assertEquals(2, imported.size());

        Student s1=imported.get(0);
        assertEquals("Alice", s1.getFirstName());
        assertEquals("Wonder", s1.getLastName());
        assertEquals(6,s1.getAge());

        Student s2=imported.get(1);
        assertEquals("Bob", s2.getFirstName());
        assertEquals("Builder", s2.getLastName());
        assertEquals(5,s2.getAge());


    }

}
