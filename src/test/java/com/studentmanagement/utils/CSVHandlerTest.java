package com.studentmanagement.utils;

import com.studentmanagement.model.Student;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class CSVHandlerTest {

    @Test
    public void testFormatStudentToCSV(){
        CSVHandler handler=new CSVHandler();
        Student testStudent= new Student("bob", "Builder", 5);

        String expected= "bob,Builder,5";
        String actual= handler.formatStudentToCSV(testStudent);

        assertEquals(expected, actual);
    }

    @Test
    public void testParseStudentFromCSVLine(){
        CSVHandler handler = new CSVHandler();
        String line = "bob,Builder,5";

        Student s= handler.parsestudentFromCSVLine(line);


        assertEquals("bob", s.getFirstName());
        assertEquals("Builder", s.getLastName());
        assertEquals(5, s.getAge());

    }
}
