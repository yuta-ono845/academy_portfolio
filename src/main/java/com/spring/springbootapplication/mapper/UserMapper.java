package com.spring.springbootapplication.mapper;

import org.apache.ibatis.annotations.Mapper;
import com.spring.springbootapplication.entity.User;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface UserMapper {

    @Insert("INSERT INTO users (name, email, password, created_at, updated_at) " +
            "VALUES (#{name}, #{email}, #{password}, #{createdAt}, #{updatedAt})")
    @Options(useGeneratedKeys = true, keyProperty = "id")
    int insertUser(User user);

    @Select("SELECT id, name, email, password, profile_image AS profileImage, bio FROM users WHERE email = #{email}")
    public User findByEmail(String email);
}
