package com.stepanov.springbootjs.controller;

import com.stepanov.springbootjs.model.User;
import com.stepanov.springbootjs.service.UserService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/users")
public class RestUserController {

    private final UserService userService;

    public RestUserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public List<User> listAllUsers() {
        List<User> allUsers = userService.getAllUsers();
        return allUsers;
    }
}
