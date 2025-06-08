package com.spring.springbootapplication.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import com.spring.springbootapplication.dto.TopChartDto;

import java.util.List;

@Mapper
public interface TopChartMapper {

    @Select("""
        SELECT 
            c.category_name AS category,
            TO_CHAR(ld.study_month, 'YYYY-MM') AS month,
            SUM(ld.study_minutes) AS total_minutes
        FROM 
            learning_data ld
        JOIN 
            categories c ON ld.category_id = c.id
        WHERE 
            ld.user_id = #{userId}
            AND ld.study_month >= date_trunc('month', CURRENT_DATE) - INTERVAL '2 months'
        GROUP BY 
            c.id, c.category_name, TO_CHAR(ld.study_month, 'YYYY-MM')
        ORDER BY 
            month ASC, c.id ASC
    """)
    List<TopChartDto> findChartDataByUserId(@Param("userId") Long userId);
}
