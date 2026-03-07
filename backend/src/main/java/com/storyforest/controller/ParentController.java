package com.storyforest.controller;

import com.storyforest.dto.ApiResponse;
import com.storyforest.entity.User;
import com.storyforest.repository.UserRepository;
import com.storyforest.service.QuizService;
import com.storyforest.service.UserReadingHistoryService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.time.DayOfWeek;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.temporal.TemporalAdjusters;
import java.util.HashMap;
import java.util.Map;

/**
 * 家长中心控制器
 */
@Slf4j
@RestController
@RequestMapping("/parent")
@RequiredArgsConstructor
@Tag(name = "家长中心", description = "家长管理、成长报告等接口")
public class ParentController {

    private final UserRepository userRepository;
    private final UserReadingHistoryService userReadingHistoryService;
    private final QuizService quizService;

    /**
     * 获取成长报告
     */
    @GetMapping("/report")
    @Operation(summary = "获取成长报告", description = "获取孩子的阅读成长报告")
    public ApiResponse<Object> getGrowthReport(
            @RequestAttribute("userId") Long userId,
            @Parameter(description = "用户 ID（孩子 ID）", required = true)
            @RequestParam Long childId) {
        // 获取孩子信息
        User child = userRepository.findById(childId)
                .orElseThrow(() -> new RuntimeException("用户不存在：" + childId));

        // 统计数据
        var stats = userReadingHistoryService.getReadingHistory(
                childId, org.springframework.data.domain.PageRequest.of(0, 1000));
        var quizStats = quizService.getQuizStats(childId);

        return ApiResponse.success(new Object() {
            public Long childId = child.getId();
            public String nickname = child.getNickname();
            public String ageGroup = child.getAgeGroup();
            public Integer totalReadingMinutes = child.getTotalReadingMinutes();
            public Integer completedStories = child.getCompletedStories();
            public Long totalReadCount = stats.getTotalElements();
            public Long quizCorrectCount = quizStats.correctCount();
            public Integer quizTotalPoints = quizStats.totalPoints();
        });
    }

    /**
     * 获取周报告
     */
    @GetMapping("/report/weekly")
    @Operation(summary = "获取周报告", description = "获取孩子的周度阅读报告")
    public ApiResponse<Object> getWeeklyReport(
            @RequestAttribute("userId") Long userId,
            @Parameter(description = "用户 ID（孩子 ID）", required = true)
            @RequestParam Long childId) {
        // 获取本周一和周日
        LocalDateTime startOfWeek = LocalDateTime.now()
                .with(TemporalAdjusters.previousOrSame(DayOfWeek.MONDAY))
                .with(LocalTime.MIN);
        LocalDateTime endOfWeek = LocalDateTime.now()
                .with(TemporalAdjusters.nextOrSame(DayOfWeek.SUNDAY))
                .with(LocalTime.MAX);

        // 这里简化处理，实际应该查询数据库
        Map<String, Object> report = new HashMap<>();
        report.put("childId", childId);
        report.put("weekStart", startOfWeek.toString());
        report.put("weekEnd", endOfWeek.toString());
        report.put("readingDays", 5);
        report.put("newStories", 3);
        report.put("totalMinutes", 120);
        report.put("quizzesCompleted", 10);

        return ApiResponse.success(report);
    }

    /**
     * 设置阅读时长限制
     */
    @PostMapping("/time-limit")
    @Operation(summary = "设置阅读时长限制", description = "设置孩子每日阅读时长限制")
    public ApiResponse<Void> setTimeLimit(
            @RequestAttribute("userId") Long userId,
            @Parameter(description = "用户 ID（孩子 ID）", required = true)
            @RequestParam Long childId,
            @Parameter(description = "每日限制时长（分钟）", example = "60", required = true)
            @RequestParam int dailyLimitMinutes) {
        // 这里简化处理，实际应该存储到数据库
        log.info("设置阅读时长限制：孩子 ID={}, 限制={}分钟", childId, dailyLimitMinutes);
        return ApiResponse.success("设置成功", null);
    }

    /**
     * 获取阅读时长限制
     */
    @GetMapping("/time-limit")
    @Operation(summary = "获取阅读时长限制", description = "获取孩子的阅读时长限制设置")
    public ApiResponse<Object> getTimeLimit(
            @RequestAttribute("userId") Long userId,
            @Parameter(description = "用户 ID（孩子 ID）", required = true)
            @RequestParam Long childId) {
        // 返回默认值
        Map<String, Object> limit = new HashMap<>();
        limit.put("childId", childId);
        limit.put("dailyLimitMinutes", 60);
        limit.put("usedMinutes", 30);
        limit.put("remainingMinutes", 30);

        return ApiResponse.success(limit);
    }

    /**
     * 设置使用时间段
     */
    @PostMapping("/time-schedule")
    @Operation(summary = "设置使用时间段", description = "设置孩子可以使用应用的时间段")
    public ApiResponse<Void> setTimeSchedule(
            @RequestAttribute("userId") Long userId,
            @Parameter(description = "用户 ID（孩子 ID）", required = true)
            @RequestParam Long childId,
            @Parameter(description = "开始时间", example = "09:00", required = true)
            @RequestParam LocalTime startTime,
            @Parameter(description = "结束时间", example = "21:00", required = true)
            @RequestParam LocalTime endTime) {
        log.info("设置使用时间段：孩子 ID={}, {}-{}", childId, startTime, endTime);
        return ApiResponse.success("设置成功", null);
    }

    /**
     * 获取使用时间段
     */
    @GetMapping("/time-schedule")
    @Operation(summary = "获取使用时间段", description = "获取孩子的使用时间段设置")
    public ApiResponse<Object> getTimeSchedule(
            @RequestAttribute("userId") Long userId,
            @Parameter(description = "用户 ID（孩子 ID）", required = true)
            @RequestParam Long childId) {
        Map<String, Object> schedule = new HashMap<>();
        schedule.put("childId", childId);
        schedule.put("startTime", "09:00");
        schedule.put("endTime", "21:00");
        schedule.put("enabled", true);

        return ApiResponse.success(schedule);
    }

    /**
     * 获取孩子列表（家长绑定的孩子）
     */
    @GetMapping("/children")
    @Operation(summary = "获取孩子列表", description = "获取家长绑定的所有孩子")
    public ApiResponse<Object> getChildren(@RequestAttribute("userId") Long userId) {
        // 这里简化处理，返回示例数据
        return ApiResponse.success(new Object() {
            public Object[] children = new Object[]{
                new Object() {
                    public Long id = 1L;
                    public String nickname = "快乐小书虫";
                    public String ageGroup = "5-6 岁";
                    public Integer totalReadingMinutes = 120;
                    public Integer completedStories = 5;
                }
            };
        });
    }
}
