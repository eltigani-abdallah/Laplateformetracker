package com.studentmanagement.service;

import java.util.ArrayList;

class BackupService{
    DatabaseConnection databaseConnection= new DatabaseConnection();
    StudentDAO studentDAO= new StudentDAO();

    public BackupService(DatabaseConnection databaseConnection){

    }

    void createBackup(){

    }

    void restoreBackup(){

    }

    void scheduleAutoBackup(){

    }

    void stopAutoBackup(){

    }

    ArrayList<String> listBackups(){

    }

    void deleteBackup(String backupName){

    }

    void compressBackup(String backupName){

    }

    void extractBackup(String backupName){

    }
}