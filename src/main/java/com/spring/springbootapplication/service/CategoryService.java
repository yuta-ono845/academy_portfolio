package com.spring.springbootapplication.service;

import com.spring.springbootapplication.entity.Category;
import com.spring.springbootapplication.mapper.CategoryMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor          // ← コンストラクタ注入
@Transactional(readOnly = true)   // 参照専用
public class CategoryService {

    private final CategoryMapper categoryMapper;

    /** 全カテゴリーを ID 順で取得 */
    public List<Category> getAll() {
        return categoryMapper.findAll();
    }
}
