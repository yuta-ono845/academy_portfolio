package com.spring.springbootapplication.mapper;

import com.spring.springbootapplication.dto.LearningDataDto;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.time.LocalDate;
import java.util.List;

@Mapper
public interface LearningDataMapper {

    /**
     * 指定ユーザー & 指定月(1日固定) の学習データを
     * カテゴリー名 JOIN 付きで取得
     */
    @Select("""
        SELECT l.id,
               c.category_name,
               l.item_name,
               l.study_minutes   AS minutes
        FROM learning_data l
        JOIN categories c ON c.id = l.category_id
        WHERE l.user_id = #{userId}
          AND l.study_month = #{month}
        ORDER BY c.id, l.id
    """)
    List<LearningDataDto> findByUserAndMonth(@Param("userId") Long userId,
                                         @Param("month")  LocalDate month);
}
