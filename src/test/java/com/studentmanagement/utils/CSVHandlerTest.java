package com.studentmanagement.utils;

import com.studentmanagement.model.Student;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class CSVHandlerTest {

    @Test
    public void testFormatStudentToCSV(){
        CSVHandler handler=new CSVHandler();
        Student testStudent= new Student(1L, "bob", "Builder", 5, 93.6);

        String expected= "1,bob,Builder,5,93.60";
        String actual= handler.formatStudentToCSV(testStudent);

        assertEquals(expected, actual);
    }

    @Test
    public void testParseStudentFromCSVLine(){
        CSVHandler handler = new CSVHandler();
        String line = "1,bob,Builder,5,93.6";

        Student s= handler.parsestudentFromCSVLine(line);

        assertEquals(1, s.getStudentId());
        assertEquals("bob", s.getFirstName());
        assertEquals("Builder", s.getLastName());
        assertEquals(5, s.getAge());
        assertEquals(93.60, s.getAverageGrade());
    }
}
