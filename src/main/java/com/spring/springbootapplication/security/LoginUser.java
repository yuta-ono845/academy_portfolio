package com.spring.springbootapplication.security;

import com.spring.springbootapplication.entity.User;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Collections;

public class LoginUser implements UserDetails {

    private final User user;

    public LoginUser(User user) {
        this.user = user;
    }

    // 必須：ユーザーの権限（今回は1つも持っていない前提）
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return Collections.emptyList(); // 権限なし（必要なら追加）
    }

    // パスワードを返す
    @Override
    public String getPassword() {
        return user.getPassword();
    }

    // ユーザー名としてメールアドレスを返す
    @Override
    public String getUsername() {
        return user.getEmail();
    }

    // アカウントが期限切れでないか
    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    // アカウントがロックされていないか
    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    // 資格情報が期限切れでないか
    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    // アカウントが有効か
    @Override
    public boolean isEnabled() {
        return true;
    }

    // 外から元の User を使いたい場合用
    public User getUser() {
        return this.user;
    }
}
