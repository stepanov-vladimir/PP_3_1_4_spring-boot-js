package com.stepanov.springbootjs.service;

import com.stepanov.springbootjs.model.User;

import java.util.List;

public interface UserService  {

    List<User> getAllUsers();

    void saveUser(User user);

    void deleteUser(Long id);

    void updateUser(User user);

    User getUserByEmail(String email);
}
