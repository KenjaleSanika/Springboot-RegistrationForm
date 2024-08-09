package com.app.service;

import com.app.entity.User;
import java.util.List;

public interface UserService {
    void registerUser(User user);
    boolean isUsernameTaken(String username);
    boolean isEmailTaken(String email);
    boolean isPhoneTaken(String phone);
    User getUserByUsername(String username); // Add this line
    void updateUser(User user); // Add this line
    List<User> getAllUsers(); // Make sure this is also present
    void deleteUser(String username);

}