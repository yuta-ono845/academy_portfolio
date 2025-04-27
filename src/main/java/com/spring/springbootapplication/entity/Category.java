package com.spring.springbootapplication.entity;

import lombok.Data;
import java.time.LocalDateTime;

@Data
public class Category {
    private Integer id;                // PK
    private String categoryName;       // カテゴリー名
    private LocalDateTime createdAt;   // INSERT 時に自動で入る
}
