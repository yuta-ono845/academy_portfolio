package com.spring.springbootapplication.mapper;

import org.apache.ibatis.annotations.Mapper;
import com.spring.springbootapplication.entity.User;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

@Mapper
public interface UserMapper {

    @Insert("INSERT INTO users (name, email, password, created_at, updated_at) " +
            "VALUES (#{name}, #{email}, #{password}, #{createdAt}, #{updatedAt})")
    @Options(useGeneratedKeys = true, keyProperty = "id")
    int insertUser(User user);

    @Select("SELECT id, name, email, password, profile_image AS profileImage, profile_image_name AS profileImageName, bio FROM users WHERE email = #{email}")
    public User findByEmail(String email);

    @Update("UPDATE users SET bio = #{bio}, profile_image = #{profileImage}, profile_image_name = #{profileImageName}, updated_at = NOW() WHERE id = #{id}")
    void updateProfileById(@Param("id") Long id, @Param("bio") String bio, @Param("profileImage") String profileImage, @Param("profileImageName") String profileImageName);
}
