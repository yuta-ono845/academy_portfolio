package com.spring.springbootapplication.entity;

import lombok.Data;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
public class LearningData {
    private Integer id;                // PK
    private Long userId;        // FK -> users.id
    private Integer categoryId;        // FK -> categories.id
    private String  itemName;          // 学習項目名
    private Integer studyMinutes;      // 学習時間 (分)
    private LocalDate studyMonth;      // 学習月 (YYYY-MM-01 で管理)
    private LocalDateTime createdAt;   // 登録日時
    private LocalDateTime updatedAt;   // 更新日時
}
