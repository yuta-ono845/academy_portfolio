package com.spring.springbootapplication.controller;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.nio.file.StandardOpenOption;

import com.spring.springbootapplication.security.LoginUser;
import org.springframework.security.core.annotation.AuthenticationPrincipal;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import com.spring.springbootapplication.dto.UserBioUpdateDto;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import com.spring.springbootapplication.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import com.spring.springbootapplication.entity.User;

import jakarta.validation.Valid;

import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;

@Controller
public class ProfileEditController {

    @Autowired
    private UserService userService;

    @GetMapping("/profile/edit")
    public String showProfileEditPage(Model model, @AuthenticationPrincipal LoginUser loginUser) {
        // ログインユーザーの情報をDBから取得
        User currentUser = userService.findByEmail(loginUser.getUsername());
    
        // DTOに現在の自己紹介文と画像パスをセット
        UserBioUpdateDto dto = new UserBioUpdateDto();
        dto.setBio(currentUser.getBio());
        dto.setProfileImage(currentUser.getProfileImage());
    
        // 画面に渡す
        model.addAttribute("user", dto);
        return "profile_edit";
    }

    @PostMapping("/profile/edit")
    public String postProfileEdit(@Valid @ModelAttribute("user") UserBioUpdateDto user,
                                  BindingResult result,
                                  @RequestParam("image") MultipartFile image,
                                  @AuthenticationPrincipal LoginUser loginUser,
                                  Model model) {
            if (result.hasErrors()) {
                System.out.println("▼▼ バリデーションエラーが発生しました ▼▼");
                result.getAllErrors().forEach(error ->
                    System.out.println(" - " + error.getDefaultMessage())
                );
                model.addAttribute("user", user); 
                return "profile_edit";
            }

            if (!image.isEmpty()) {
            try {
                // ログイン中のユーザーの email を取得し、ファイル名に使用
                String email = loginUser.getUsername();
                String sanitizedEmail = email.replace("@", "_at_").replace(".", "_");

                // ディレクトリを作成
                String uploadDirStr = "/tmp/uploads/" + sanitizedEmail;
                Path uploadDir = Paths.get(uploadDirStr);
                if (!Files.exists(uploadDir)) {
                    Files.createDirectories(uploadDir);
                }

                Path filePath = uploadDir.resolve("profile.png");
                Files.copy(image.getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING);

                // DB用パスをDTOに格納
                user.setProfileImage("/img/uploads/" + sanitizedEmail + "/profile.png");

                System.out.println("画像保存成功: " + filePath);
            } catch (IOException e) {
                e.printStackTrace();
                return "profile_edit";
            }
        }

            userService.updateUserProfile(loginUser.getUser().getId(), user.getBio(), user.getProfileImage());

            return "redirect:/top";
        }
}
