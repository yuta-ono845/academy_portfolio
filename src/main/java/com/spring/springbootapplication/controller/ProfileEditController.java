package com.spring.springbootapplication.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ProfileEditController {

    @GetMapping("/profile/edit")
    public String showProfileEditPage() {
        return "profile_edit"; // resources/templates/profile_edit.html を返す
    }
}
