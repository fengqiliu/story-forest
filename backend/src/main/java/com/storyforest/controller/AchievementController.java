package com.storyforest.controller;

import com.storyforest.dto.ApiResponse;
import com.storyforest.entity.Achievement;
import com.storyforest.entity.UserAchievement;
import com.storyforest.entity.PointsTransaction;
import com.storyforest.service.AchievementService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 成就和积分控制器
 */
@Slf4j
@RestController
@RequestMapping("/achievement")
@RequiredArgsConstructor
@Tag(name = "成就积分", description = "勋章成就和积分流水相关接口")
public class AchievementController {

    private final AchievementService achievementService;

    /**
     * 获取所有勋章
     */
    @GetMapping("/list")
    @Operation(summary = "获取勋章列表", description = "获取所有可获得的勋章")
    public ApiResponse<List<Achievement>> getAllAchievements() {
        List<Achievement> achievements = achievementService.getAllAchievements();
        return ApiResponse.success(achievements);
    }

    /**
     * 获取用户已获得的勋章
     */
    @GetMapping("/user")
    @Operation(summary = "获取用户勋章", description = "获取用户已获得的勋章列表")
    public ApiResponse<List<UserAchievement>> getUserAchievements(
            @RequestAttribute("userId") Long userId) {
        List<UserAchievement> achievements = achievementService.getUserAchievements(userId);
        return ApiResponse.success(achievements);
    }

    /**
     * 检查勋章达成状态
     */
    @GetMapping("/check/{userId}")
    @Operation(summary = "检查勋章达成", description = "检查并授予用户应得的勋章")
    public ApiResponse<List<Achievement>> checkAchievements(
            @RequestAttribute("userId") Long userId,
            @Parameter(description = "用户 ID", required = true)
            @PathVariable Long targetUserId) {
        // 只能检查自己的勋章
        if (!userId.equals(targetUserId)) {
            return ApiResponse.error(403, "无权检查其他用户的勋章");
        }

        List<Achievement> newAchievements = achievementService.checkAndAwardAchievements(userId);
        return ApiResponse.success(newAchievements);
    }

    /**
     * 获取用户勋章进度
     */
    @GetMapping("/progress")
    @Operation(summary = "获取勋章进度", description = "获取用户勋章达成进度")
    public ApiResponse<Object> getAchievementProgress(@RequestAttribute("userId") Long userId) {
        List<Achievement> allAchievements = achievementService.getAllAchievements();
        List<UserAchievement> userAchievements = achievementService.getUserAchievements(userId);

        return ApiResponse.success(new Object() {
            public int totalAchievements = allAchievements.size();
            public int obtainedAchievements = userAchievements.size();
            public double progressPercent = allAchievements.size() > 0 ?
                    Math.round((double) userAchievements.size() / allAchievements.size() * 100) : 0;
        });
    }

    /**
     * 获取积分流水
     */
    @GetMapping("/points/transactions")
    @Operation(summary = "获取积分流水", description = "获取用户的积分变动记录")
    public ApiResponse<Page<PointsTransaction>> getPointsTransactions(
            @RequestAttribute("userId") Long userId,
            @Parameter(description = "页码", example = "0")
            @RequestParam(defaultValue = "0") int page,
            @Parameter(description = "每页数量", example = "10")
            @RequestParam(defaultValue = "10") int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<PointsTransaction> transactions = achievementService.getPointsTransactions(userId, pageable);
        return ApiResponse.success(transactions);
    }

    /**
     * 获取积分统计
     */
    @GetMapping("/points/stats")
    @Operation(summary = "获取积分统计", description = "获取用户的积分统计信息")
    public ApiResponse<Object> getPointsStats(@RequestAttribute("userId") Long userId) {
        int currentPoints = achievementService.getCurrentPoints(userId);
        int totalEarned = achievementService.getTotalEarnedPoints(userId);
        int totalSpent = achievementService.getTotalSpentPoints(userId);

        return ApiResponse.success(new Object() {
            public int currentPoints = currentPoints;
            public int totalEarned = totalEarned;
            public int totalSpent = totalSpent;
        });
    }
}
