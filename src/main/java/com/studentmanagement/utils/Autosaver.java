package com.studentmanagement.utils;

import com.studentmanagement.model.Student;
import com.studentmanagement.service.ImportExportService;


import java.util.List;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class Autosaver {

    private final ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);
    private final ImportExportService exporter = new ImportExportService();

    public void startAutoSave(List<Student> studentList, int intervalSeconds) {
        scheduler.scheduleAtFixedRate(() -> {
            exporter.exportToCSV(studentList);
            System.out.println("auto-saved student data.");
        }, intervalSeconds, intervalSeconds, TimeUnit.SECONDS);
    }

    public void stopAutoSave(){
        scheduler.shutdown();
        System.out.println("Auto-save disabled");
    }


}


