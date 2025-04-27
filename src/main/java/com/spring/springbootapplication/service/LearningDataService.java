package com.spring.springbootapplication.service;

import com.spring.springbootapplication.dto.LearningDataDto;
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
}
