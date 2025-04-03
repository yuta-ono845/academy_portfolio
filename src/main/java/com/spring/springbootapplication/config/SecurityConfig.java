package com.spring.springbootapplication.config;

import com.spring.springbootapplication.service.CustomUserDetailsService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
public class SecurityConfig {
    //CustomUserDetailsServiceー＞情報取得のサービス
    private final CustomUserDetailsService userDetailsService;

    //取得したデータを代入
    public SecurityConfig(CustomUserDetailsService userDetailsService) {
        this.userDetailsService = userDetailsService;
    }

    @Bean
    //PasswordEncoder ->　ハッシュ化のためのライブラリ
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    // 認証マネージャー（ログイン時の認証を処理）
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager();
    }

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
                .loginProcessingUrl("/login") // フォームのactionと一致させる
                .defaultSuccessUrl("/top", true) // 成功後にリダイレクト
                .failureUrl("/login?error=true") 
                .permitAll()
            )
            // ログアウトも認証不要にする
            .logout(logout -> logout
                .permitAll()
            );
        return http.build();
    }
}
