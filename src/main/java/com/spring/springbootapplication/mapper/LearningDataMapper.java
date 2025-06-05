package com.spring.springbootapplication.mapper;

import com.spring.springbootapplication.dto.LearningDataDto;
import com.spring.springbootapplication.entity.LearningData;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.apache.ibatis.annotations.Delete;

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
               l.study_minutes
        FROM learning_data l
        JOIN categories c ON c.id = l.category_id
        WHERE l.user_id = #{userId}
          AND l.study_month = #{month}
        ORDER BY c.id, l.id
    """)
    List<LearningDataDto> findByUserAndMonth(@Param("userId") Long userId,
                                         @Param("month")  LocalDate month);


    /* カテゴリーが違っても、項目名の重複は許可しない設計 */                                  
    @Select("""
    SELECT COUNT(*) FROM learning_data
    WHERE user_id = #{userId}
      AND study_month = #{studyMonth}
      AND item_name = #{itemName}
    """)
    int countDuplicateItems(@Param("userId") Long userId,
                          @Param("studyMonth") LocalDate studyMonth,
                          @Param("itemName") String itemName);

    /* 学習項目を保存するクエリ */
    @Insert("""
        INSERT INTO learning_data (user_id, category_id, item_name, study_minutes, study_month, created_at, updated_at)
        VALUES (#{userId}, #{categoryId}, #{itemName}, #{studyMinutes}, #{studyMonth}, NOW(), NOW())
        """)
       void insert(LearningData entity);

    @Update("""
        update learning_data
        set study_minutes = #{studyMinutes},
            updated_at = NOW()
        where id = #{id}
        """)
        void updateStudyMinutes(@Param("id") Integer id,
                                @Param("studyMinutes") Integer studyMinutes);

    @Delete("""
        DELETE FROM learning_data
        WHERE id = #{id}
        """)
        void deleteSkill(@Param("id") Integer id);
}
