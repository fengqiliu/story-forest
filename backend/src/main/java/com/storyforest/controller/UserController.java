package com.storyforest.controller;

import com.storyforest.dto.*;
import com.storyforest.entity.UserFavorite;
import com.storyforest.entity.UserReadingHistory;
import com.storyforest.service.FavoriteService;
import com.storyforest.service.StoryService;
import com.storyforest.service.UserReadingHistoryService;
import com.storyforest.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 用户中心控制器
 */
@Slf4j
@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
@Tag(name = "用户中心", description = "用户信息、收藏、阅读历史等接口")
public class UserController {

    private final UserService userService;
    private final FavoriteService favoriteService;
    private final UserReadingHistoryService userReadingHistoryService;
    private final StoryService storyService;

    /**
     * 获取用户信息
     */
    @GetMapping("/info")
    @Operation(summary = "获取用户信息", description = "获取当前登录用户的详细信息")
    public ApiResponse<UserResponse> getUserInfo(@RequestAttribute("userId") Long userId) {
        UserResponse userInfo = userService.getUserInfo(userId);
        return ApiResponse.success(userInfo);
    }

    /**
     * 更新用户信息
     */
    @PutMapping("/info")
    @Operation(summary = "更新用户信息", description = "更新用户昵称、头像、年龄段等信息")
    public ApiResponse<UserResponse> updateUserInfo(
            @RequestAttribute("userId") Long userId,
            @Valid @RequestBody UserUpdateRequest request) {
        UserResponse userInfo = userService.updateUserInfo(userId, request);
        return ApiResponse.success("更新成功", userInfo);
    }

    /**
     * 修改密码
     */
    @PutMapping("/password")
    @Operation(summary = "修改密码", description = "修改用户登录密码")
    public ApiResponse<Void> changePassword(
            @RequestAttribute("userId") Long userId,
            @Valid @RequestBody ChangePasswordRequest request) {
        userService.changePassword(userId, request);
        return ApiResponse.success("密码修改成功", null);
    }

    /**
     * 设置年龄段
     */
    @PutMapping("/age-group")
    @Operation(summary = "设置年龄段", description = "设置用户的年龄段，用于个性化推荐")
    public ApiResponse<UserResponse> setAgeGroup(
            @RequestAttribute("userId") Long userId,
            @Parameter(description = "年龄段", example = "5-6 岁", required = true)
            @RequestParam String ageGroup) {
        UserResponse userInfo = userService.setAgeGroup(userId, ageGroup);
        return ApiResponse.success("设置成功", userInfo);
    }

    /**
     * 获取收藏列表
     */
    @GetMapping("/favorites")
    @Operation(summary = "获取收藏列表", description = "获取用户收藏的故事列表")
    public ApiResponse<Page<UserFavorite>> getFavorites(
            @RequestAttribute("userId") Long userId,
            @Parameter(description = "页码", example = "0")
            @RequestParam(defaultValue = "0") int page,
            @Parameter(description = "每页数量", example = "10")
            @RequestParam(defaultValue = "10") int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<UserFavorite> favorites = favoriteService.getUserFavorites(userId, pageable);
        return ApiResponse.success(favorites);
    }

    /**
     * 添加收藏
     */
    @PostMapping("/favorites/{storyId}")
    @Operation(summary = "添加收藏", description = "收藏指定故事")
    public ApiResponse<UserFavorite> addFavorite(
            @RequestAttribute("userId") Long userId,
            @Parameter(description = "故事 ID", example = "1", required = true)
            @PathVariable Long storyId) {
        UserFavorite favorite = favoriteService.addFavorite(userId, storyId);
        return ApiResponse.success("收藏成功", favorite);
    }

    /**
     * 取消收藏
     */
    @DeleteMapping("/favorites/{storyId}")
    @Operation(summary = "取消收藏", description = "取消收藏指定故事")
    public ApiResponse<Void> removeFavorite(
            @RequestAttribute("userId") Long userId,
            @Parameter(description = "故事 ID", example = "1", required = true)
            @PathVariable Long storyId) {
        favoriteService.removeFavorite(userId, storyId);
        return ApiResponse.success("取消收藏成功", null);
    }

    /**
     * 检查是否已收藏
     */
    @GetMapping("/favorites/{storyId}/check")
    @Operation(summary = "检查收藏状态", description = "检查用户是否已收藏某故事")
    public ApiResponse<Boolean> checkFavorite(
            @RequestAttribute("userId") Long userId,
            @Parameter(description = "故事 ID", example = "1", required = true)
            @PathVariable Long storyId) {
        boolean isFavorite = favoriteService.isFavorite(userId, storyId);
        return ApiResponse.success(isFavorite);
    }

    /**
     * 获取阅读历史
     */
    @GetMapping("/history")
    @Operation(summary = "获取阅读历史", description = "获取用户的阅读历史记录")
    public ApiResponse<Page<UserReadingHistory>> getReadingHistory(
            @RequestAttribute("userId") Long userId,
            @Parameter(description = "页码", example = "0")
            @RequestParam(defaultValue = "0") int page,
            @Parameter(description = "每页数量", example = "10")
            @RequestParam(defaultValue = "10") int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<UserReadingHistory> history = userReadingHistoryService.getReadingHistory(userId, pageable);
        return ApiResponse.success(history);
    }

    /**
     * 获取正在阅读的故事
     */
    @GetMapping("/history/in-progress")
    @Operation(summary = "获取正在阅读", description = "获取用户正在阅读的故事列表")
    public ApiResponse<List<UserReadingHistory>> getInProgressStories(
            @RequestAttribute("userId") Long userId) {
        List<UserReadingHistory> stories = userReadingHistoryService.getInProgressStories(userId);
        return ApiResponse.success(stories);
    }

    /**
     * 更新阅读进度
     */
    @PostMapping("/history/{storyId}/progress")
    @Operation(summary = "更新阅读进度", description = "更新故事的阅读进度")
    public ApiResponse<UserReadingHistory> updateReadingProgress(
            @RequestAttribute("userId") Long userId,
            @Parameter(description = "故事 ID", example = "1", required = true)
            @PathVariable Long storyId,
            @Parameter(description = "章节 ID", example = "1")
            @RequestParam(required = false) Long chapterId,
            @Parameter(description = "阅读进度（0-100）", example = "50")
            @RequestParam(defaultValue = "0") int progress,
            @Parameter(description = "阅读时长（秒）", example = "300")
            @RequestParam(defaultValue = "0") int readingTime) {
        UserReadingHistory history = userReadingHistoryService.updateProgress(
                userId, storyId, chapterId, progress, readingTime);
        return ApiResponse.success(history);
    }

    /**
     * 获取用户统计信息
     */
    @GetMapping("/stats")
    @Operation(summary = "获取统计信息", description = "获取用户的阅读统计数据")
    public ApiResponse<Object> getStats(@RequestAttribute("userId") Long userId) {
        UserResponse userInfo = userService.getUserInfo(userId);
        long favoriteCount = favoriteService.countFavorites(userId);
        long readingHistoryCount = userReadingHistoryService.countReadingHistory(userId);

        return ApiResponse.success(new Object() {
            public Long userId = userInfo.getId();
            public String nickname = userInfo.getNickname();
            public Integer totalReadingMinutes = userInfo.getTotalReadingMinutes();
            public Integer completedStories = userInfo.getCompletedStories();
            public Integer points = userInfo.getPoints();
            public Long favoriteCount = favoriteCount;
            public Long readingHistoryCount = readingHistoryCount;
        });
    }
}
