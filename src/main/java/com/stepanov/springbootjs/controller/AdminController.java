package com.stepanov.springbootjs.controller;

import com.stepanov.springbootjs.model.Role;
import com.stepanov.springbootjs.model.User;
import com.stepanov.springbootjs.service.RoleService;
import com.stepanov.springbootjs.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/admin")
public class AdminController {

    private final UserService userService;
    private final RoleService roleService;
    private final PasswordEncoder passwordEncoder;

    @GetMapping
    public String listAllUsers(Model model, Principal principal) {
        List<User> allUsers = userService.getAllUsers();
        User loggedUser = userService.getUserByEmail(principal.getName());
        List<Role> allRoles = roleService.findAllRoles();
        User newUser = new User();

        model.addAttribute("allUsers", allUsers);
        model.addAttribute("loggedUser", loggedUser);
        model.addAttribute("allRoles", allRoles);
        model.addAttribute("newUser", newUser);

        return "admin/admin-page";
    }

    @PostMapping
    public String saveUser(@ModelAttribute("newUser") User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userService.saveUser(user);
        return "redirect:/admin";
    }

    @PatchMapping("/{id}")
    public String update(@ModelAttribute("editUser") User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userService.updateUser(user);
        return "redirect:/admin";
    }

    @DeleteMapping("/{id}")
    public String deleteUser(@PathVariable("id") Long id) {
        userService.deleteUser(id);
        return "redirect:/admin";
    }
}