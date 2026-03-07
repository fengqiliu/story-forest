package com.storyforest.controller;

import com.storyforest.dto.ApiResponse;
import com.storyforest.entity.Story;
import com.storyforest.service.RecommendationService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 推荐控制器
 */
@Slf4j
@RestController
@RequestMapping("/recommend")
@RequiredArgsConstructor
@Tag(name = "推荐服务", description = "个性化推荐相关接口")
public class RecommendationController {

    private final RecommendationService recommendationService;

    /**
     * 获取个性化推荐
     */
    @GetMapping("/for-you")
    @Operation(summary = "获取个性化推荐", description = "根据用户偏好获取个性化故事推荐")
    public ApiResponse<List<Story>> getPersonalizedRecommendations(
            @RequestAttribute("userId") Long userId,
            @Parameter(description = "推荐数量", example = "10")
            @RequestParam(defaultValue = "10") int limit) {
        List<Story> stories = recommendationService.getPersonalizedRecommendations(userId, limit);
        return ApiResponse.success(stories);
    }

    /**
     * 获取相似故事
     */
    @GetMapping("/similar/{storyId}")
    @Operation(summary = "获取相似故事", description = "获取与指定故事相似的故事推荐")
    public ApiResponse<List<Story>> getSimilarStories(
            @Parameter(description = "故事 ID", example = "1", required = true)
            @PathVariable Long storyId,
            @Parameter(description = "推荐数量", example = "5")
            @RequestParam(defaultValue = "5") int limit) {
        List<Story> stories = recommendationService.getSimilarStories(storyId, limit);
        return ApiResponse.success(stories);
    }

    /**
     * 获取热门故事
     */
    @GetMapping("/popular")
    @Operation(summary = "获取热门故事", description = "获取播放量最高的热门故事")
    public ApiResponse<List<Story>> getPopularStories(
            @Parameter(description = "数量", example = "10")
            @RequestParam(defaultValue = "10") int limit) {
        List<Story> stories = recommendationService.getPopularStories(limit);
        return ApiResponse.success(stories);
    }

    /**
     * 记录用户行为
     */
    @PostMapping("/track")
    @Operation(summary = "记录用户行为", description = "记录用户对故事的操作行为")
    public ApiResponse<Void> trackUserAction(
            @RequestAttribute("userId") Long userId,
            @Parameter(description = "行为类型", example = "play", required = true)
            @RequestParam String actionType,
            @Parameter(description = "故事 ID", example = "1", required = true)
            @RequestParam Long storyId) {
        recommendationService.recordUserAction(userId, actionType, storyId);
        return ApiResponse.success();
    }
}
