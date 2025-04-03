package com.spring.springbootapplication.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import com.spring.springbootapplication.service.CustomUserDetailsService;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.core.Authentication;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.springframework.security.web.context.HttpSessionSecurityContextRepository;

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
    @Autowired
    @Lazy
    private CustomUserDetailsService userDetailsService;

    @GetMapping
    public String showRegisterForm(@ModelAttribute("user") User user) {
        return "regist";
    }

    // 新規登録処理を実行する
    @PostMapping
    public String registerUser(@Valid @ModelAttribute("user") User user, BindingResult bindingResult, HttpServletRequest request) {
        if (bindingResult.hasErrors()) {
            // バリデーションエラーがある場合、再度フォーム画面に戻す
            return "regist";
        }

        // エラーがなければ、サービス層に処理を委譲してユーザー情報をDBに保存する
        userService.registerUser(user);

        // 新規登録後の認証処理を実行
        // SpringSecurityが読み込める形にするために、UserDetailsServiceを利用して認証情報を取得
        UserDetails userDetails = userDetailsService.loadUserByUsername(user.getEmail());
        // 認証情報を作成
        Authentication auth = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
        // SpringSecurityが認証情報を管理する場所へ認証情報をセット
        SecurityContextHolder.getContext().setAuthentication(auth);

        // 認証情報をセッションに保存
        HttpSession session = request.getSession(true);
        session.setAttribute(HttpSessionSecurityContextRepository.SPRING_SECURITY_CONTEXT_KEY,SecurityContextHolder.getContext());

        return "redirect:/top";
    }
}
