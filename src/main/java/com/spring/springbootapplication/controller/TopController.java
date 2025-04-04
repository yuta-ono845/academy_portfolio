package com.spring.springbootapplication.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.ui.Model;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import com.spring.springbootapplication.entity.User;

import com.spring.springbootapplication.security.LoginUser;

@Controller
public class TopController {

    @GetMapping("/top")
    public String showTopPage(@AuthenticationPrincipal LoginUser loginUser, Model model) { // @AuthenticationPrincipalで認証されたuser情報を取得
        User user = loginUser.getUser();
        String profileImage = user.getProfileImage();

        model.addAttribute("profileImage", profileImage);
        return "top";
    }
}
