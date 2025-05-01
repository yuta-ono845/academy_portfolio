package com.spring.springbootapplication.dto;

import lombok.Data;

@Data
public class LearningDataDto {
    private Integer id;
    private String  categoryName;
    private String  itemName;
    private Integer studyMinutes;
}
