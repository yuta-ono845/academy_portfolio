package com.spring.springbootapplication.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ChartEditController {

    @GetMapping("/chart/edit")
    public String showChartEditPage() {
        return "chart_edit";
    }
}
