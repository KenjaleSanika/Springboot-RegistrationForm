package com.app.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import com.app.entity.User;
import com.app.service.UserService;

@Controller
public class UserController {
    
    @Autowired
    private UserService service;

    @GetMapping("/")
    public String register(Model model) {
        User user = new User();
        model.addAttribute("user", user);
        return "register";
    }
    
    @PostMapping("/registerUser")
    public String registerUser(@ModelAttribute("user") User user, Model model) {
        String result = "register";
        HttpStatus status = HttpStatus.CREATED;
        String errorMessage = "";
        String successMessage = "";

        if (!user.getPassword().equals(user.getCpassword())) {
            status = HttpStatus.CONFLICT;
            errorMessage = "Passwords do not match";
        } else if (service.isUsernameTaken(user.getUsername())) {
            status = HttpStatus.CONFLICT;
            errorMessage = "Username already registered";
        } else if (service.isEmailTaken(user.getEmail())) {
            status = HttpStatus.CONFLICT;
            errorMessage = "Email already registered";
        } else if (service.isPhoneTaken(user.getPhone())) {
            status = HttpStatus.CONFLICT;
            errorMessage = "Phone number already registered";
        } else {
            try {
                service.registerUser(user);
                // Set success message with status code
                successMessage = "Successfully registered! (Sucess Code: " + status.value() + ")";
                model.addAttribute("successMessage", successMessage);
                model.addAttribute("httpStatus", status.value());
                return result; // Stay on the registration page
            } catch (Exception e) {
                status = HttpStatus.INTERNAL_SERVER_ERROR;
                errorMessage = "An error occurred while registering. Please try again.";
            }
        }

        // Append status code to the error message
        errorMessage += " (Error Code: " + status.value() + ")";
        model.addAttribute("errorMessage", errorMessage);
        return result;
    }


    @GetMapping("/users")
    public String listUsers(Model model) {
        model.addAttribute("users", service.getAllUsers());
        return "users";
    }

    @GetMapping("/edit/{username}")
    public String showEditForm(@PathVariable String username, Model model) {
        User user = service.getUserByUsername(username);
        model.addAttribute("user", user);
        return "edit-user";
    }

    @PostMapping("/updateUser")
    public String updateUser(@ModelAttribute("user") User user, Model model) {
        try {
            service.updateUser(user);
            return "redirect:/users";
        } catch (Exception e) {
            model.addAttribute("errorMessage", "An error occurred while updating. Please try again.");
            return "edit-user";
        }
    }

    @GetMapping("/delete/{username}")
    public String deleteUser(@PathVariable String username) {
        service.deleteUser(username);
        return "redirect:/users";
    }
}
