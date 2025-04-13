package com.spring.springbootapplication.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.ui.Model;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import com.spring.springbootapplication.entity.User;
import com.spring.springbootapplication.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;

import com.spring.springbootapplication.security.LoginUser;

@Controller
public class TopController {

    @Autowired
    private UserService userService;

    @GetMapping("/top")
    public String showTopPage(Model model, @AuthenticationPrincipal LoginUser loginUser) {
        User updatedUser = userService.findByEmail(loginUser.getUsername());
        model.addAttribute("userName", updatedUser.getName());
        model.addAttribute("bio", updatedUser.getBio());
        model.addAttribute("profileImage", updatedUser.getProfileImage());
        return "top";
}
}
