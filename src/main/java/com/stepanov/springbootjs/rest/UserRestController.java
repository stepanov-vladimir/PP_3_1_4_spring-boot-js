package com.stepanov.springbootjs.rest;

import com.stepanov.springbootjs.exception.UserErrorResponse;
import com.stepanov.springbootjs.exception.UserNotFoundException;
import com.stepanov.springbootjs.model.User;
import com.stepanov.springbootjs.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.cert.Extension;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api")
public class UserRestController {

    private final UserService userService;

    public UserRestController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/users")
    public List<User> listAllUsers() {
        List<User> allUsers = userService.getAllUsers();

        return allUsers;
    }

    @GetMapping("/users/{id}")
    public User getUser(@PathVariable("id") Long id) {
        Optional<User> user = userService.getUserById(id);

        if (user.isPresent()) {
            return user.get();
        } else {
            throw new UserNotFoundException(String.format("User with id='%s' not found", id));
        }
    }

    @PostMapping("/users")
    public User addNewUser(@RequestBody User user) {

        userService.saveUser(user);
        return user;
    }

    @PatchMapping ("/users")
    public User updateUser(@RequestBody User user) {

        userService.updateUser(user);
        return user;
    }

    @DeleteMapping("/users/{id}")
    public void deleteUser (@PathVariable Long id) {
        Optional<User> user = userService.getUserById(id);
        if (user.isPresent()) {
            userService.deleteUser(id);
        } else {
            throw new UserNotFoundException(String.format("User with id='%s' not found", id));
        }
    }

}
