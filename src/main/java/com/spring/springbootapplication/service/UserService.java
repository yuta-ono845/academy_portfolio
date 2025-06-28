package com.spring.springbootapplication.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.spring.springbootapplication.mapper.UserMapper;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import java.time.LocalDateTime;

@Service
public class UserService {

    @Autowired
    private UserMapper userMapper;
    
    private BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    /**
    @param userDto フォームから送られてくるDTO (com.spring.springbootapplication.dto.User)
     */
    public void registerUser(com.spring.springbootapplication.dto.User userDto) {
        com.spring.springbootapplication.entity.User userEntity = new com.spring.springbootapplication.entity.User();
        userEntity.setName(userDto.getName());
        userEntity.setEmail(userDto.getEmail());
        userEntity.setPassword(passwordEncoder.encode(userDto.getPassword()));
        userEntity.setCreatedAt(LocalDateTime.now());
        userEntity.setUpdatedAt(LocalDateTime.now());
        
        userMapper.insertUser(userEntity);
    }

    public com.spring.springbootapplication.entity.User findByEmail(String email) {
        return userMapper.findByEmail(email);
    }

    public void updateUserProfile(Long id, String bio, String profileImage, String profileImageName) {
        userMapper.updateProfileById(id, bio, profileImage, profileImageName);
    }
}
