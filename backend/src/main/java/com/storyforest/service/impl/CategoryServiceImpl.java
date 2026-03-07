package com.storyforest.service.impl;

import com.storyforest.entity.Category;
import com.storyforest.repository.CategoryRepository;
import com.storyforest.repository.StoryRepository;
import com.storyforest.service.CategoryService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 分类服务实现类
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class CategoryServiceImpl implements CategoryService {

    private final CategoryRepository categoryRepository;
    private final StoryRepository storyRepository;

    @Override
    @Transactional(readOnly = true)
    public List<Category> getAllCategories() {
        return categoryRepository.findByIsActiveTrueOrderBySortOrder();
    }

    @Override
    @Transactional(readOnly = true)
    public List<Category> getCategoriesByAgeGroup(String ageGroup) {
        return categoryRepository.findByIsActiveTrueAndAgeGroupWithAll(ageGroup);
    }

    @Override
    @Transactional(readOnly = true)
    public Category getCategoryById(Long id) {
        return categoryRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("分类不存在：" + id));
    }

    @Override
    @Transactional(readOnly = true)
    public int countStoriesInCategory(String categoryName) {
        return (int) storyRepository.findByCategory(categoryName, org.springframework.data.domain.Pageable.unpaged())
                .getTotalElements();
    }
}
