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
        User currentUser = userService.findByEmail(loginUser.getUsername());
    
        UserBioUpdateDto dto = new UserBioUpdateDto();
        dto.setBio(currentUser.getBio());
        dto.setProfileImage(currentUser.getProfileImage());
        dto.setProfileImageName(currentUser.getProfileImageName());

        model.addAttribute("user", dto);
        return "profile_edit";
    }

    @PostMapping("/profile/edit")
    public String postProfileEdit(@Valid @ModelAttribute("user") UserBioUpdateDto user,
                                  BindingResult result,
                                  @RequestParam("image") MultipartFile image,
                                  @AuthenticationPrincipal LoginUser loginUser,
                                  Model model) {

            if (!image.isEmpty()) {
                try {
                    String email = loginUser.getUsername();
                    String sanitizedEmail = email.replace("@", "_at_").replace(".", "_");

                    String tempDirStr = "/tmp/uploads/tmp/" + sanitizedEmail;
                    Path tempDir = Paths.get(tempDirStr);
                    if (!Files.exists(tempDir)) {
                        Files.createDirectories(tempDir);
                    }

                    Path tempFilePath = tempDir.resolve("profile.png");

                    Files.copy(image.getInputStream(), tempFilePath, StandardCopyOption.REPLACE_EXISTING);

                    user.setProfileImage("/img/uploads/tmp/" + sanitizedEmail + "/profile.png");
                    user.setProfileImageName(image.getOriginalFilename());

                } catch (IOException e) {
                    e.printStackTrace();
                    model.addAttribute("user", user);
                    return "profile_edit"; 
                }
            }

            if (result.hasErrors()) {
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
                    user.setProfileImageName(image.getOriginalFilename()); 

                    System.out.println("画像保存成功: " + filePath);
                } catch (IOException e) {
                    e.printStackTrace();
                    return "profile_edit";
                }
            }else {
                // 画像が空 ⇒ 既存の画像パスを維持
                User currentUser = userService.findByEmail(loginUser.getUsername());
                user.setProfileImage(currentUser.getProfileImage());
                user.setProfileImageName(currentUser.getProfileImageName()); 
            }

            userService.updateUserProfile(loginUser.getUser().getId(), user.getBio(), user.getProfileImage(), user.getProfileImageName());

            return "redirect:/top";
        }
}
