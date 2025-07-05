package com.studentmanagement.dao;

import com.studentmanagement.model.User;

//minimum implementation to compile
public interface UserDAO {

    void saveUser(User user);
    
    User findUserByUsername(String username);
    
    boolean authenticateUser(String username, String password);
}