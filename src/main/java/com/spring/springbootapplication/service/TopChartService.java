package com.spring.springbootapplication.service;

import com.spring.springbootapplication.dto.TopChartDto;
import com.spring.springbootapplication.mapper.CategoryMapper;
import com.spring.springbootapplication.mapper.TopChartMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.spring.springbootapplication.dto.ChartDatasetDto;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@Service
public class TopChartService {

    @Autowired
    private TopChartMapper topChartMapper;
    @Autowired
    private CategoryMapper categoryMapper;

    public List<TopChartDto> getChartData(Long userId) {
        return topChartMapper.findChartDataByUserId(userId);
    }

    public Map<String, Object> buildChartData(Long userId) {
        List<TopChartDto> rawData = topChartMapper.findChartDataByUserId(userId);
        List<String> allCategories = categoryMapper.findAllCategoryNames();
        List<String> chartLabels = new ArrayList<>();
        LocalDate now = LocalDate.now().withDayOfMonth(1);


        for (int i = 2; i >= 0; i--) {
            LocalDate target = now.minusMonths(i);
            chartLabels.add(target.toString().substring(0, 7));
        }

        Map<String, Map<String, Integer>> categoryMonthMap = new LinkedHashMap<>();
        for (String category : allCategories) {
            categoryMonthMap.put(category, new HashMap<>());
        }
        for (TopChartDto dto : rawData) {
            categoryMonthMap
                .get(dto.getCategory())
                .put(dto.getMonth(), dto.getTotalMinutes());
        }

        List<ChartDatasetDto> chartDatasets = new ArrayList<>();
        for (Map.Entry<String, Map<String, Integer>> entry : categoryMonthMap.entrySet()) {
            String category = entry.getKey();
            Map<String, Integer> monthData = entry.getValue();

            List<Integer> data = new ArrayList<>();
            for (String label : chartLabels) {
                data.add(monthData.getOrDefault(label, 0)); 
            }

            chartDatasets.add(new ChartDatasetDto(category, data));
        }

        Map<String, Object> chartDataMap = new HashMap<>();
        chartDataMap.put("chartLabels", chartLabels);
        chartDataMap.put("chartDatasets", chartDatasets);

        return chartDataMap;
    }
}
