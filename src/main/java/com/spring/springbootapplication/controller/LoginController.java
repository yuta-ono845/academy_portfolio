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
        // handlerでFlashmapを作成しようとしたが、Securityの処理はDispatcherServletより前に行われるため、リクエスト情報格納に失敗。
        // そのため、controllerでFlashMapを作成することにした
        // クエリパラメータが "error=true" のときだけ処理する
        if ("true".equals(request.getParameter("error"))) {
            FlashMap flashMap = new FlashMap(); // FlashMap を生成
            flashMap.put("loginError", "メールアドレス、もしくはパスワードが間違っています"); //FlashMapという箱にエラーメッセージを格納

            FlashMapManager flashMapManager = RequestContextUtils.getFlashMapManager(request); // FlashMapManager（FlashMapの管理システム）を取得
            if (flashMapManager != null) {
                flashMapManager.saveOutputFlashMap(flashMap, request, response); //FlashMapに格納された情報を保存(リクエスト紐付ける処理、情報の格納とは少し違う模様)
            }
            return "redirect:/login"; // loginにリダイレクト
        }

        Map<String, ?> inputFlashMap = RequestContextUtils.getInputFlashMap(request); // inputFlashMapにrequest内のflashMapの情報を格納
        if (inputFlashMap != null && inputFlashMap.containsKey("loginError")){  // inputFlashMapがnullでないandキー名がloginErrorの場合、
            request.setAttribute("loginError", inputFlashMap.get("loginError")); //setAtttributeでリクエストスコープにエラーメッセージを格納
        }

        return "login";
    }
}
