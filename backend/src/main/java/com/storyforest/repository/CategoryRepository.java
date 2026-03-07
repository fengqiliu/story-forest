package com.storyforest.repository;

import com.storyforest.entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 分类数据访问层
 */
@Repository
public interface CategoryRepository extends JpaRepository<Category, Long> {

    /**
     * 获取所有启用的分类
     */
    List<Category> findByIsActiveTrueOrderBySortOrder();

    /**
     * 根据年龄段获取分类
     */
    List<Category> findByIsActiveTrueAndAgeGroupOrderBySortOrder(String ageGroup);

    /**
     * 根据年龄段获取分类（包含所有年龄段）
     */
    @Query("SELECT c FROM Category c WHERE c.isActive = true AND (c.ageGroup = 'all' OR c.ageGroup = :ageGroup) ORDER BY c.sortOrder")
    List<Category> findByIsActiveTrueAndAgeGroupWithAll(@Param("ageGroup") String ageGroup);

    /**
     * 检查分类名称是否存在
     */
    boolean existsByName(String name);
}
