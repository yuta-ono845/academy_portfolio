package com.spring.springbootapplication.dto;

import lombok.Data;

@Data
public class TopChartDto {
    private String category;
    private String month;
    private Integer totalMinutes;
}
