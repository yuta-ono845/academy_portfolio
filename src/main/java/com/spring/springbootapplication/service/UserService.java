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
    
    // BCryptPasswordEncoder を利用してパスワードのハッシュ化を行う
    private BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    /**
     * ユーザー登録処理
     * @param userDto フォームから送られてくるDTO (com.spring.springbootapplication.dto.User)
     */
    public void registerUser(com.spring.springbootapplication.dto.User userDto) {
        // DTOからエンティティへの変換
        com.spring.springbootapplication.entity.User userEntity = new com.spring.springbootapplication.entity.User();
        userEntity.setName(userDto.getName());
        userEntity.setEmail(userDto.getEmail());
        // パスワードをハッシュ化
        userEntity.setPassword(passwordEncoder.encode(userDto.getPassword()));
        // 作成日時・更新日時は現在時刻で設定
        userEntity.setCreatedAt(LocalDateTime.now());
        userEntity.setUpdatedAt(LocalDateTime.now());
        
        // MyBatisのMapperを呼び出して、ユーザー情報をデータベースに登録
        userMapper.insertUser(userEntity);
    }

    public com.spring.springbootapplication.entity.User findByEmail(String email) {
        return userMapper.findByEmail(email);
    }

    public void updateUserProfile(Long userId, String bio, String profileImage) {
        userMapper.updateProfileById(userId, bio, profileImage);
    }
}
