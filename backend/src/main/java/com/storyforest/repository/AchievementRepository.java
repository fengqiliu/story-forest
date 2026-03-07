package com.storyforest.repository;

import com.storyforest.entity.Achievement;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 勋章数据访问层
 */
@Repository
public interface AchievementRepository extends JpaRepository<Achievement, Long> {

    /**
     * 获取所有启用的勋章
     */
    List<Achievement> findByIsActiveTrueOrderBySortOrder();

    /**
     * 根据条件类型查找勋章
     */
    List<Achievement> findByConditionTypeOrderByConditionValue(String conditionType);

    /**
     * 检查是否已存在相同条件的勋章
     */
    boolean existsByConditionTypeAndConditionValue(String conditionType, Integer conditionValue);
}
