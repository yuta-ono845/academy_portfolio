package com.spring.springbootapplication.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class ChartDatasetDto {
    private String label;
    private List<Integer> data;
}
