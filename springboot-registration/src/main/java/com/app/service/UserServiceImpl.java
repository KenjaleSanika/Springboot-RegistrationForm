package com.app.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.app.entity.User;
import com.app.repo.UserRepo;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepo repo;
    
    @Override
    public void registerUser(User user) {
        repo.save(user);
    }

    @Override
    public boolean isUsernameTaken(String username) {
        return repo.existsByUsername(username);
    }

    @Override
    public boolean isEmailTaken(String email) {
        return repo.existsByEmail(email);
    }

    @Override
    public boolean isPhoneTaken(String phone) {
        return repo.existsByPhone(phone);
    }
    
    @Override
    public List<User> getAllUsers() {
        return repo.findAll();
    }
    @Override
    public User getUserByUsername(String username) {
        return repo.findById(username).orElseThrow(() -> new RuntimeException("User not found"));
    }

    @Override
    public void updateUser(User user) {
        repo.save(user);
    }
    @Override
    public void deleteUser(String username) {
        repo.deleteById(username);
    }
}

    

