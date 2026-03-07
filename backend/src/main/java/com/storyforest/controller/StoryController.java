package com.storyforest.controller;

import com.storyforest.dto.ApiResponse;
import com.storyforest.entity.Story;
import com.storyforest.service.StoryService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 故事控制器
 */
@Slf4j
@RestController
@RequestMapping("/stories")
@RequiredArgsConstructor
@Tag(name = "故事管理", description = "故事列表、详情、搜索、推荐等故事相关接口")
public class StoryController {

    private final StoryService storyService;

    /**
     * 获取故事列表
     */
    @GetMapping
    @Operation(summary = "获取故事列表", description = "分页获取故事列表，支持排序")
    public ApiResponse<Page<Story>> getStories(
            @Parameter(description = "页码（从 0 开始）", example = "0")
            @RequestParam(defaultValue = "0") int page,
            @Parameter(description = "每页数量", example = "10")
            @RequestParam(defaultValue = "10") int size,
            @Parameter(description = "排序字段", example = "playCount")
            @RequestParam(defaultValue = "playCount") String sort,
            @Parameter(description = "排序方向", example = "desc")
            @RequestParam(defaultValue = "desc") String direction) {

        Pageable pageable = PageRequest.of(page, size,
                Sort.Direction.fromString(direction), sort);

        Page<Story> stories = storyService.getStories(pageable);
        return ApiResponse.success(stories);
    }

    /**
     * 按年龄段获取故事
     */
    @GetMapping("/age-group/{ageGroup}")
    @Operation(summary = "按年龄段获取故事", description = "根据年龄段筛选故事")
    public ApiResponse<Page<Story>> getStoriesByAgeGroup(
            @Parameter(description = "年龄段", example = "5-6 岁", required = true)
            @PathVariable String ageGroup,
            @Parameter(description = "页码", example = "0")
            @RequestParam(defaultValue = "0") int page,
            @Parameter(description = "每页数量", example = "10")
            @RequestParam(defaultValue = "10") int size) {

        Pageable pageable = PageRequest.of(page, size);
        Page<Story> stories = storyService.getStoriesByAgeGroup(ageGroup, pageable);
        return ApiResponse.success(stories);
    }

    /**
     * 按分类获取故事
     */
    @GetMapping("/category/{category}")
    @Operation(summary = "按分类获取故事", description = "根据主题分类筛选故事")
    public ApiResponse<Page<Story>> getStoriesByCategory(
            @Parameter(description = "分类", example = "童话故事", required = true)
            @PathVariable String category,
            @Parameter(description = "页码", example = "0")
            @RequestParam(defaultValue = "0") int page,
            @Parameter(description = "每页数量", example = "10")
            @RequestParam(defaultValue = "10") int size) {

        Pageable pageable = PageRequest.of(page, size);
        Page<Story> stories = storyService.getStoriesByCategory(category, pageable);
        return ApiResponse.success(stories);
    }

    /**
     * 按年龄段和分类获取故事
     */
    @GetMapping("/filter")
    @Operation(summary = "组合筛选故事", description = "按年龄段和/或分类筛选故事")
    public ApiResponse<Page<Story>> getStoriesByFilter(
            @Parameter(description = "年龄段（可选）", example = "5-6 岁")
            @RequestParam(required = false) String ageGroup,
            @Parameter(description = "分类（可选）", example = "童话故事")
            @RequestParam(required = false) String category,
            @Parameter(description = "页码", example = "0")
            @RequestParam(defaultValue = "0") int page,
            @Parameter(description = "每页数量", example = "10")
            @RequestParam(defaultValue = "10") int size) {

        Pageable pageable = PageRequest.of(page, size);
        Page<Story> stories;

        if (ageGroup != null && category != null) {
            stories = storyService.getStoriesByAgeGroupAndCategory(ageGroup, category, pageable);
        } else if (ageGroup != null) {
            stories = storyService.getStoriesByAgeGroup(ageGroup, pageable);
        } else if (category != null) {
            stories = storyService.getStoriesByCategory(category, pageable);
        } else {
            stories = storyService.getStories(pageable);
        }

        return ApiResponse.success(stories);
    }

    /**
     * 获取推荐故事
     */
    @GetMapping("/recommended")
    @Operation(summary = "获取推荐故事", description = "获取系统推荐的故事列表")
    public ApiResponse<Page<Story>> getRecommendedStories(
            @Parameter(description = "页码", example = "0")
            @RequestParam(defaultValue = "0") int page,
            @Parameter(description = "每页数量", example = "10")
            @RequestParam(defaultValue = "10") int size) {

        Pageable pageable = PageRequest.of(page, size);
        Page<Story> stories = storyService.getRecommendedStories(pageable);
        return ApiResponse.success(stories);
    }

    /**
     * 获取热门故事
     */
    @GetMapping("/popular")
    @Operation(summary = "获取热门故事", description = "按播放量获取热门故事")
    public ApiResponse<Page<Story>> getPopularStories(
            @Parameter(description = "页码", example = "0")
            @RequestParam(defaultValue = "0") int page,
            @Parameter(description = "每页数量", example = "10")
            @RequestParam(defaultValue = "10") int size) {

        Pageable pageable = PageRequest.of(page, size);
        Page<Story> stories = storyService.getPopularStories(pageable);
        return ApiResponse.success(stories);
    }

    /**
     * 获取首页精选故事
     */
    @GetMapping("/featured")
    @Operation(summary = "获取首页精选", description = "获取首页今日精选故事（3 个）")
    public ApiResponse<List<Story>> getFeaturedStories() {
        List<Story> stories = storyService.getFeaturedStories();
        return ApiResponse.success(stories);
    }

    /**
     * 搜索故事
     */
    @GetMapping("/search")
    @Operation(summary = "搜索故事", description = "根据关键词搜索故事标题或描述")
    public ApiResponse<Page<Story>> searchStories(
            @Parameter(description = "搜索关键词", example = "小红帽", required = true)
            @RequestParam String keyword,
            @Parameter(description = "页码", example = "0")
            @RequestParam(defaultValue = "0") int page,
            @Parameter(description = "每页数量", example = "10")
            @RequestParam(defaultValue = "10") int size) {

        Pageable pageable = PageRequest.of(page, size);
        Page<Story> stories = storyService.searchStories(keyword, pageable);
        return ApiResponse.success(stories);
    }

    /**
     * 获取故事详情
     */
    @GetMapping("/{id}")
    @Operation(summary = "获取故事详情", description = "根据故事 ID 获取详细信息")
    public ApiResponse<Story> getStoryDetail(
            @Parameter(description = "故事 ID", example = "1", required = true)
            @PathVariable Long id) {
        Story story = storyService.getStoryById(id);
        return ApiResponse.success(story);
    }

    /**
     * 增加播放次数
     */
    @PostMapping("/{id}/play")
    @Operation(summary = "增加播放次数", description = "记录故事播放，增加播放计数")
    public ApiResponse<Void> incrementPlayCount(
            @Parameter(description = "故事 ID", example = "1", required = true)
            @PathVariable Long id) {
        storyService.incrementPlayCount(id);
        return ApiResponse.success();
    }
}
