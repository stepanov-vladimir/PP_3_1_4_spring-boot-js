package com.stepanov.springbootjs.controller;

import com.stepanov.springbootjs.exception.UserNotFoundException;
import com.stepanov.springbootjs.model.User;
import com.stepanov.springbootjs.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
    public ResponseEntity<List<User>> listAllUsers() {
        List<User> allUsers = userService.getAllUsers();

        return ResponseEntity.ok(allUsers);
    }

    @GetMapping("/users/{id}")
    public ResponseEntity<User> getUser(@PathVariable("id") Long id) {
        Optional<User> user = userService.getUserById(id);
        if (user.isPresent()) {
            return ResponseEntity.ok(user.get());
        } else {
            throw new UserNotFoundException(String.format("User with id='%s' not found", id));
        }
    }

    @PostMapping("/users")
    public ResponseEntity<User> addNewUser(@RequestBody User user) {
        userService.saveUser(user);

        return ResponseEntity.ok(user);
    }

    @PatchMapping ("/users")
    public ResponseEntity<User> updateUser(@RequestBody User user) {
        userService.updateUser(user);

        return new ResponseEntity<>(user, HttpStatus.OK);
    }

    @DeleteMapping("/users/{id}")
    public ResponseEntity<Void> deleteUser (@PathVariable Long id) {
        Optional<User> user = userService.getUserById(id);
        if (user.isPresent()) {
            userService.deleteUser(id);
        } else {
            throw new UserNotFoundException(String.format("User with id='%s' not found", id));
        }
        return new ResponseEntity<>(HttpStatus.OK);
    }

}
