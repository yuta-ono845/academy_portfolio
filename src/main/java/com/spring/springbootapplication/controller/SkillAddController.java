package com.spring.springbootapplication.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.time.LocalDate;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import com.spring.springbootapplication.service.LearningDataService;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.spring.springbootapplication.dto.LearningDataDto;
import com.spring.springbootapplication.security.LoginUser;

@RequiredArgsConstructor
@Slf4j
@Controller
public class SkillAddController {
    private final LearningDataService learningDataService;

    // #################　getMapping　##########################

    @GetMapping("/skill/add")
    public String showSkillAddPage(@RequestParam("category") int categoryId,
                                   @RequestParam("month") String studyMonth,
                                   Model model) {

        LearningDataDto dto = new LearningDataDto();
        dto.setCategoryId(categoryId);
        dto.setStudyMonth(studyMonth);

        // 必要な情報をModelに渡す
        model.addAttribute("learningDataDto", dto);
        model.addAttribute("categoryId", categoryId);
        model.addAttribute("month", studyMonth);

        // ログ出力（開発中の確認用）
        log.info("SkillAdd画面へ遷移: categoryId={}, month={}", categoryId, studyMonth);

        return "skill_add"; 
    }

    // #################　postMapping　##########################

    @PostMapping("/skill/add/submit")
    public String submitSkill(@AuthenticationPrincipal LoginUser user,
                            @Validated @ModelAttribute LearningDataDto dto,
                            BindingResult bindingResult,
                            Model model,
                            RedirectAttributes redirectAttributes) {

        // 学習月データがString型で渡ってくるので、LocalDate型に変換
        //　年を跨ぐ場合の（現在一月、選択月12月の場合、つまり数字が大きい場合）年を−１する処理を追加
        int currentYear = LocalDate.now().getYear();
        int month = Integer.parseInt(dto.getStudyMonth());
        if (month > LocalDate.now().getMonthValue()) {
            currentYear -= 1;
        }
        String formattedMonth = String.format("%02d", month);
        LocalDate studyMonthDate = LocalDate.parse(currentYear + "-" + formattedMonth + "-01");


        // 項目名の重複チェック（重複があれば、bindingResultにエラーを追加）
        if (dto.getItemName() != null && !dto.getItemName().isBlank() &&
            learningDataService.isDuplicateItem(user.getUser().getId(), studyMonthDate, dto.getItemName())) {
            bindingResult.rejectValue("itemName", "duplicate", dto.getItemName() + "は既に登録されています");
        }

        // バリデーションエラーがあれば戻る
        if (bindingResult.hasErrors()) {
            model.addAttribute("categoryId", dto.getCategoryId());
            model.addAttribute("studyMonth", dto.getStudyMonth());
            return "skill_add";
        }

        // 保存処理
        learningDataService.insert(user.getUser().getId(), dto, studyMonthDate);
            redirectAttributes.addFlashAttribute("showModal", true);
            redirectAttributes.addFlashAttribute("studyMinutes", dto.getStudyMinutes());
            redirectAttributes.addFlashAttribute("itemName", dto.getItemName());
            redirectAttributes.addFlashAttribute("categoryId", dto.getCategoryId());
            return "redirect:/skill/add?category=" + dto.getCategoryId() + "&month=" + dto.getStudyMonth();
    }       
}
