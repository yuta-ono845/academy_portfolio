package com.spring.springbootapplication.controller;

import org.springframework.ui.Model;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.spring.springbootapplication.service.CategoryService;
import com.spring.springbootapplication.service.LearningDataService;
import org.springframework.security.core.annotation.AuthenticationPrincipal;

import com.spring.springbootapplication.dto.LearningDataDto;
import com.spring.springbootapplication.security.LoginUser;

@Controller
public class ChartEditController {

    @Autowired CategoryService categoryService;
    @Autowired LearningDataService learningDataService;

    @GetMapping("/chart/edit")
    public String showChartEditPage(@AuthenticationPrincipal LoginUser loginUser,
                                    @RequestParam(value = "month", required = false) Integer month,
                                    Model model) {
    
        Long userId = loginUser.getUser().getId();
    
        // --- 基準月を決定 ---
        LocalDate base = LocalDate.now().withDayOfMonth(1);
        if (month != null) {
            int year = base.getYear();
            if (month > base.getMonthValue()) { year -= 1; }
            base = LocalDate.of(year, month, 1);
        }
    
        // --- プルダウンは「今月から過去 2 か月」固定 ---
        LocalDate now = LocalDate.now();
        List<Integer> months = IntStream.rangeClosed(0, 2)
                .mapToObj(i -> now.minusMonths(i).getMonthValue())
                .toList();
    
        model.addAttribute("months", months);
        model.addAttribute("currentMonth", base.getMonthValue());
        model.addAttribute("categories", categoryService.getAll());
    
        List<LearningDataDto> all =
            learningDataService.findByUserAndMonth(userId, base);
    
        List<LearningDataDto> backendItems  = all.stream()
            .filter(i -> i.getCategoryName().equals("バックエンド"))
            .toList();
        List<LearningDataDto> frontendItems = all.stream()
            .filter(i -> i.getCategoryName().equals("フロントエンド"))
            .toList();
        List<LearningDataDto> infraItems    = all.stream()
            .filter(i -> i.getCategoryName().equals("インフラ"))
            .toList();
    
        model.addAttribute("backendItems",  backendItems);
        model.addAttribute("frontendItems", frontendItems);
        model.addAttribute("infraItems",    infraItems);
    
        return "chart_edit";
    }

    @PostMapping("/skill/update")
    public String updateStudyMinutes(@RequestParam("id") Integer id,
                                     @RequestParam("studyMinutes") Integer studyMinutes,
                                     @RequestParam("itemName") String itemName,
                                     @RequestParam("studyMonth") String studyMonth,
                                     RedirectAttributes redirectAttributes) {

        learningDataService.updateStudyMinutes(id, studyMinutes);
    
        redirectAttributes.addFlashAttribute("updated", true);
        redirectAttributes.addFlashAttribute("itemName", itemName);
        redirectAttributes.addFlashAttribute("month", studyMonth);
        return "redirect:/chart/edit?month=" + studyMonth;
    }

    @PostMapping("/skill/delete")
    public String deleteSkill(@RequestParam("id") Integer id,
                              @RequestParam("itemName") String itemName,
                              @RequestParam("studyMonth") String studyMonth,
                                RedirectAttributes redirectAttributes) {

        learningDataService.deleteSkill(id);

        redirectAttributes.addFlashAttribute("deleted", true);
        redirectAttributes.addFlashAttribute("itemName", itemName);
        redirectAttributes.addFlashAttribute("month", studyMonth);
        return "redirect:/chart/edit?month=" + studyMonth;
    }
}
