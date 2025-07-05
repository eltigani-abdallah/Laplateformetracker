package com.studentmanagement.service;

import com.studentmanagement.model.User;
import com.studentmanagement.dao.UserDAO;

//minimum implementation to compile
public class AuthenticationService {
    private UserDAO userDAO;
    private User currentUser;
    
    public AuthenticationService(UserDAO userDAO) {
        this.userDAO = userDAO;
    }
    
    public boolean authenticate(String username, String password) {
        try {
            boolean isAuthenticated = userDAO.authenticateUser(username, password);
            
            if (isAuthenticated) {
                this.currentUser = userDAO.findUserByUsername(username);
            }
            
            return isAuthenticated;
        } catch (Exception e) {
            return false;
        }
    }

    public void register(User user) {
        userDAO.saveUser(user);
    }

    public boolean isAuthenticated() {
        return currentUser != null;
    }
}