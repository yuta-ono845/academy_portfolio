package com.spring.springbootapplication.controller;

import jakarta.servlet.http.HttpServletRequest; //セッション情報取得に使用
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class LoginController {

    @GetMapping("/login-error")
    public String handleLoginError(HttpServletRequest request, RedirectAttributes redirectAttributes) {
        // セッションからエラーメッセージを取得
        Object error = request.getSession().getAttribute("loginError");

        // Flash属性に変換して /login に渡す
        if (error != null) {
            redirectAttributes.addFlashAttribute("errorMessage", error.toString());
            request.getSession().removeAttribute("loginError"); // セッションからは削除
        }

        // Flash属性付きで /login にリダイレクト
        return "redirect:/login";
    }

    @GetMapping("/login")
    public String showLoginForm() {
        return "login"; // login.html にエラーメッセージを表示する処理は Thymeleaf 側に任せる
    }
}
