package com.spring.springbootapplication.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.ui.Model;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import com.spring.springbootapplication.entity.User;
import com.spring.springbootapplication.service.UserService;
import com.spring.springbootapplication.service.TopChartService;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;

import com.spring.springbootapplication.security.LoginUser;

@Controller
public class TopController {

    @Autowired
    private UserService userService;
    @Autowired
    private TopChartService topChartService;


    @GetMapping("/top")
    public String showTopPage(Model model, @AuthenticationPrincipal LoginUser loginUser) {
        User user = userService.findByEmail(loginUser.getUsername());
        Long userId = user.getId();

        model.addAttribute("userName", user.getName());
        model.addAttribute("bio", user.getBio());
        model.addAttribute("profileImage", user.getProfileImage());

        Map<String, Object> chartData = topChartService.buildChartData(userId);
        model.addAttribute("chartLabels", chartData.get("chartLabels"));
        model.addAttribute("chartDatasets", chartData.get("chartDatasets"));

        return "top";
}
}
