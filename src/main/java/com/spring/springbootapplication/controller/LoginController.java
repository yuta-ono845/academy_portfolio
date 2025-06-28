package com.spring.springbootapplication.controller;

import java.util.Map;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.support.RequestContextUtils;
import org.springframework.web.servlet.FlashMap;
import org.springframework.web.servlet.FlashMapManager;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Controller
public class LoginController {

    @GetMapping("/login")
    public String showLoginForm(HttpServletRequest request, HttpServletResponse response) {
        if ("true".equals(request.getParameter("error"))) {
            FlashMap flashMap = new FlashMap();
            flashMap.put("loginError", "メールアドレス、もしくはパスワードが間違っています"); 

            FlashMapManager flashMapManager = RequestContextUtils.getFlashMapManager(request);
            if (flashMapManager != null) {
                flashMapManager.saveOutputFlashMap(flashMap, request, response);
            }
            return "redirect:/login";
        }

        Map<String, ?> inputFlashMap = RequestContextUtils.getInputFlashMap(request);
        if (inputFlashMap != null && inputFlashMap.containsKey("loginError")){
            request.setAttribute("loginError", inputFlashMap.get("loginError"));
        }

        return "login";
    }
}
