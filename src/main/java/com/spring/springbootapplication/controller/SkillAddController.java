package com.spring.springbootapplication.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.spring.springbootapplication.dto.LearningDataDto;

@Slf4j
@Controller
public class SkillAddController {

    @GetMapping("/skill/add")
    public String showSkillAddPage(@RequestParam("category") int categoryId,
                                   @RequestParam("month") String month,
                                   Model model) {
        // 学習月の "yyyy-MM" を "yyyy-MM-01" に変換して表示用に渡す（DB用にも後で使える）
        String monthFormatted = month + "-01";

        // 必要な情報をModelに渡す
        model.addAttribute("learningDataDto", new LearningDataDto());
        model.addAttribute("categoryId", categoryId);
        model.addAttribute("month", month);
        model.addAttribute("monthFormatted", monthFormatted);

        // ログ出力（開発中の確認用）
        log.info("SkillAdd画面へ遷移: categoryId={}, month={}", categoryId, month);

        return "skill_add"; // templates/skill_add.html に遷移
    }
}
