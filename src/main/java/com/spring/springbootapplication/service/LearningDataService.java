package com.spring.springbootapplication.service;

import com.spring.springbootapplication.dto.LearningDataDto;
import com.spring.springbootapplication.entity.LearningData;
import com.spring.springbootapplication.mapper.LearningDataMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class LearningDataService {

    private final LearningDataMapper learningDataMapper;

    /**
     * 指定ユーザー・指定月(1日固定)の学習データを取得
     * @param userId  ユーザーID
     * @param month   その月の1日 (例 2025-04-01)
     */
    public List<LearningDataDto> findByUserAndMonth(Long userId, LocalDate month) {
        return learningDataMapper.findByUserAndMonth(userId, month);
    }

    /* 学習項目が同一月内で、重複していないか確認し、重複する場合trueを返す */
    public boolean isDuplicateItem(Long userId, LocalDate studyMonth, String itemName) {
    return learningDataMapper.countDuplicateItems(userId, studyMonth, itemName) > 0;
    }

    // ########　学習項目をDBに保存するメソッド　##########
    @Transactional
    public void insert(Long userId, LearningDataDto dto, LocalDate studyMonthDate) {
        // DTO から Entity に変換
        LearningData entity = new LearningData();
        entity.setUserId(userId);
        entity.setCategoryId(dto.getCategoryId());
        entity.setItemName(dto.getItemName());
        entity.setStudyMinutes(dto.getStudyMinutes());
        entity.setStudyMonth(studyMonthDate);

        // Mapperのinsertメソッドを呼び出してDBに保存
        learningDataMapper.insert(entity);
    }

    @Transactional
    public void updateStudyMinutes(Integer id, Integer studyMinutes) {
        learningDataMapper.updateStudyMinutes(id, studyMinutes);
    }
}
