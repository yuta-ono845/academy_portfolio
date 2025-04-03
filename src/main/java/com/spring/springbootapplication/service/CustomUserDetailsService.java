package com.spring.springbootapplication.service;

import com.spring.springbootapplication.entity.User;
import com.spring.springbootapplication.security.LoginUser;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    @Autowired
    private UserService userService;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        // DBからメールアドレスでユーザー検索
        User user = userService.findByEmail(email);
        if (user == null) {
            throw new UsernameNotFoundException("該当するユーザーが見つかりませんでした: " + email);
        }

        // User を LoginUser（UserDetails実装）にラップして返す
        return new LoginUser(user);
    }
}
