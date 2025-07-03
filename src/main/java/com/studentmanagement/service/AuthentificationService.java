package com.studentmanagement.service;

class AuthenticationService{
    UserDAO userDAO = new UserDAO();

    User currentUser= new User();

    public AuthenticationService(UserDAO userDAO){

    }

    boolean authenticate(String username, String password){

    }

    void register(User newUser){

    }

    boolean isAuthenticated(){

    }



}