package com.stepanov.springbootjs.service;

import com.stepanov.springbootjs.model.User;

import java.util.List;
import java.util.Optional;

public interface UserService  {

    List<User> getAllUsers();

    void saveUser(User user);

    void deleteUser(Long id);

    void updateUser(User user);

    User getUserByEmail(String email);

    Optional<User> getUserById(Long id);
}
