package com.spring.springbootapplication.dto;
import jakarta.validation.constraints.Size;
import jakarta.validation.constraints.NotEmpty;


public class UserBioUpdateDto {
    @NotEmpty()
    @Size(min = 50, max = 200)
    private String bio;
    
    private String profileImage;
    private String profileImageName;

    // getter / setter
    public String getProfileImage() {
        return profileImage;
    }
    
    public void setProfileImage(String profileImage) {
        this.profileImage = profileImage;
    }
    
    public String getProfileImageName() {
        return profileImageName;
    }
    
    public void setProfileImageName(String profileImageName) {
        this.profileImageName = profileImageName;
    }

    public String getBio() {
        return bio;
    }
    
    public void setBio(String bio) {
        this.bio = bio;
    }
}
