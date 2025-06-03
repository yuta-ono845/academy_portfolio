package com.spring.springbootapplication.dto;

import jakarta.validation.constraints.*;
import lombok.Data;

@Data
public class LearningDataDto {
    private Integer id;
    private String  categoryName;

    @NotBlank(message = "項目名は必ず入力してください")
    @Size(max = 50, message = "項目名は50文字以内で入力してください")
    private String itemName;

    @NotNull(message = "学習時間は必ず入力してください")
    @Min(value = 0, message = "学習時間は0以上の数字で入力してください")
    private Integer studyMinutes;

    //ユーザーが入力する部分ではないが、URL直打ち対策して記載
    @NotBlank(message = "学習月の情報が不正です")
    private String studyMonth;

    //ユーザーが入力する部分ではないが、URL直打ち対策して記載
    @NotNull(message = "カテゴリ情報が不正です")
    private Integer categoryId;
}
