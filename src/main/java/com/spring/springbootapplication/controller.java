package com.spring.springbootapplication;

import org.springframework.stereotype.Controller;
//import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/register")
public class controller {

    @GetMapping
    public String showRegisterForm() {
        // Thymeleafテンプレート "regist.html" を返す
        return "regist";
    }
}
