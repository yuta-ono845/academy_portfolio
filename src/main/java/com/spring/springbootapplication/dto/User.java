package com.spring.springbootapplication.dto;

//import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import jakarta.validation.constraints.Pattern;

public class User {

    @NotEmpty(message = "氏名は必ず入力してください")
    @Size(max = 255, message = "氏名は255文字以内で入力してください")
    private String name;

    @Pattern(regexp = "^$|^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$", message = "メールアドレスが正しい形式ではありません")
    @NotEmpty(message = "メールアドレスは必ず入力してください")
    @Size(max = 320, message = "メールアドレスは320文字以内で入力してください")
    private String email;

    @NotEmpty(message = "パスワードは必ず入力してください")
    @Pattern(regexp = "^$|^(?=.*[A-Za-z])(?=.*\\d)[A-Za-z\\d]{8,}$", message = "英数字8文字以上で入力してください")
    private String password;
    private String profileImage;
    private String bio;

    // getterとsetter
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }
  
    public void setEmail(String email) {
        this.email = email;
    }
  
    public String getPassword() {
        return password;
    }
  
    public void setPassword(String password) {
        this.password = password;
    }

    public String getProfileImage() {
        return profileImage;
    }
    
    public void setProfileImage(String profileImage) {
        this.profileImage = profileImage;
    }

    public String getBio() {
        return bio;
    }
    
    public void setBio(String bio) {
        this.bio = bio;
    }
}
