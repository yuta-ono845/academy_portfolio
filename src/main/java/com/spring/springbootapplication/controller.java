package com.spring.springbootapplication;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.spring.springbootapplication.dto.User; // DTO（入力用）
import com.spring.springbootapplication.service.UserService;

import jakarta.validation.Valid;

public class controller {
    // このクラス自体は直接利用されません。
}

// 新規登録用のコントローラー（package-private）
@Controller
@RequestMapping("/register")
class RegisterController {

    @Autowired
    private UserService userService; 

    @GetMapping
    public String showRegisterForm(@ModelAttribute("user") User user) {
        return "regist";
    }

    // 新規登録処理を実行する
    @PostMapping
    public String registerUser(@Valid @ModelAttribute("user") User user, BindingResult bindingResult) {
    if (bindingResult.hasErrors()) {
        // バリデーションエラーがある場合、再度フォーム画面に戻す
        return "regist";
    }
    // エラーがなければ、サービス層に処理を委譲してユーザー情報をDBに保存する
    userService.registerUser(user);
        return "redirect:/login";
}
}

// ログイン画面用のコントローラー（package-private）
@Controller
class LoginController {

    // GET: ログイン画面の表示
    @GetMapping("/login")
    public String showLoginForm() {
        return "login"; // login.html をレンダリング
    }
}
