package com.spring.springbootapplication.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
            // リクエストの認可設定
            .authorizeHttpRequests(authorize -> authorize
                // /register と静的リソースへのアクセスは認証不要にする
                .requestMatchers("/login", "/register",  "/css/**", "/js/**", "/images/**").permitAll()
                // それ以外のリクエストは認証が必要
                .anyRequest().authenticated()
            )
            // フォームログインの設定
            .formLogin(form -> form
                .loginPage("/login")
                .permitAll()
            )
            // ログアウトも認証不要にする
            .logout(logout -> logout
                .permitAll()
            );
        return http.build();
    }
}
