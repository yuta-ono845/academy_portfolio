package com.spring.springbootapplication.entity;

import java.time.LocalDateTime;

public class User {
    private Long id;
    private String name;
    private String email;
    private String password;
    private String profileImage;
    private String bio;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private String profileImageName;

    public User() {}

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

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
  
    public LocalDateTime getCreatedAt() {
        return createdAt;
    }
  
    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }
  
    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }
  
    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }

    public String getProfileImageName() {
        return profileImageName;
    }
    
    public void setProfileImageName(String profileImageName) {
        this.profileImageName = profileImageName;
    }
}
