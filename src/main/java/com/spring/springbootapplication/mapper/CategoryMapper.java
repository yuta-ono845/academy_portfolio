package com.spring.springbootapplication.mapper;

import com.spring.springbootapplication.entity.Category;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface CategoryMapper {

    /** カテゴリーマスタを ID 昇順で取得 */
    @Select("""
        SELECT id, category_name, created_at
        FROM categories
        ORDER BY id
    """)
    List<Category> findAll();

    @Select("SELECT category_name FROM categories ORDER BY id ASC")
    List<String> findAllCategoryNames();    
}
