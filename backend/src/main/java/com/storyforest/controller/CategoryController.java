package com.storyforest.controller;

import com.storyforest.dto.ApiResponse;
import com.storyforest.entity.Category;
import com.storyforest.service.CategoryService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 分类控制器
 */
@Slf4j
@RestController
@RequestMapping("/categories")
@RequiredArgsConstructor
@Tag(name = "分类管理", description = "故事分类相关接口")
public class CategoryController {

    private final CategoryService categoryService;

    /**
     * 获取所有分类
     */
    @GetMapping
    @Operation(summary = "获取所有分类", description = "获取所有启用的故事分类")
    public ApiResponse<List<Category>> getAllCategories() {
        List<Category> categories = categoryService.getAllCategories();
        return ApiResponse.success(categories);
    }

    /**
     * 根据年龄段获取分类
     */
    @GetMapping("/age-group/{ageGroup}")
    @Operation(summary = "按年龄段获取分类", description = "获取适合特定年龄段的分类")
    public ApiResponse<List<Category>> getCategoriesByAgeGroup(
            @Parameter(description = "年龄段", example = "5-6 岁", required = true)
            @PathVariable String ageGroup) {
        List<Category> categories = categoryService.getCategoriesByAgeGroup(ageGroup);
        return ApiResponse.success(categories);
    }

    /**
     * 获取分类详情
     */
    @GetMapping("/{id}")
    @Operation(summary = "获取分类详情", description = "根据分类 ID 获取详细信息")
    public ApiResponse<Category> getCategoryDetail(
            @Parameter(description = "分类 ID", example = "1", required = true)
            @PathVariable Long id) {
        Category category = categoryService.getCategoryById(id);
        return ApiResponse.success(category);
    }

    /**
     * 统计分类下的故事数量
     */
    @GetMapping("/{id}/count")
    @Operation(summary = "统计故事数量", description = "统计分类下的故事数量")
    public ApiResponse<Integer> countStoriesInCategory(
            @Parameter(description = "分类 ID", example = "1", required = true)
            @PathVariable Long id) {
        Category category = categoryService.getCategoryById(id);
        int count = categoryService.countStoriesInCategory(category.getName());
        return ApiResponse.success(count);
    }
}
