package com.spring.springbootapplication.security;

import com.spring.springbootapplication.entity.User;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Collections;

public class LoginUser implements UserDetails {

    //ここにDBから取り出したuser情報を入れてspringが読める形にする
    private final User user;

    public LoginUser(User user) {
        this.user = user;
    }

    // 必須：ユーザーの権限　今回は権限機能が必要な機能はなし
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return Collections.emptyList(); // 権限なし（必要なら追加）
    }

    @Override
    public String getPassword() {
        return user.getPassword();
    }

    @Override
    public String getUsername() {
        return user.getEmail();
    }

    // アカウント期限切れ・ロック等は今回の仕様では使用しないので全て true
    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    // 外から元の User を使いたい場合用
    public User getUser() {
        return this.user;
    }
}
