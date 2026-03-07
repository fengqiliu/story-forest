package com.storyforest.service;

import com.storyforest.entity.Category;

import java.util.List;

/**
 * 分类服务接口
 */
public interface CategoryService {

    /**
     * 获取所有分类
     */
    List<Category> getAllCategories();

    /**
     * 根据年龄段获取分类
     */
    List<Category> getCategoriesByAgeGroup(String ageGroup);

    /**
     * 获取分类详情
     */
    Category getCategoryById(Long id);

    /**
     * 统计分类下的故事数量
     */
    int countStoriesInCategory(String categoryName);
}
