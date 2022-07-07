package com.stepanov.springbootjs.controller;

import com.stepanov.springbootjs.model.User;
import com.stepanov.springbootjs.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@Controller
@RequiredArgsConstructor
@RequestMapping("/admin")
public class AdminController {

    private final UserService userService;

    @GetMapping()
    public String jsAdminPageShow(Model model, Principal principal) {
        User loggedUser = userService.getUserByEmail(principal.getName());
        model.addAttribute("loggedUser", loggedUser);
        return "js/admin-page-js";
    }
}
