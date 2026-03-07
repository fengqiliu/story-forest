package com.storyforest.controller;

import com.storyforest.dto.ApiResponse;
import com.storyforest.dto.QuizSubmitRequest;
import com.storyforest.dto.QuizSubmitResponse;
import com.storyforest.entity.Quiz;
import com.storyforest.entity.QuizRecord;
import com.storyforest.service.QuizService;
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
 * 答题控制器
 */
@Slf4j
@RestController
@RequestMapping("/quiz")
@RequiredArgsConstructor
@Tag(name = "答题闯关", description = "答题互动相关接口")
public class QuizController {

    private final QuizService quizService;

    /**
     * 获取章节题目列表
     */
    @GetMapping("/chapter/{chapterId}")
    @Operation(summary = "获取章节题目", description = "获取指定章节的所有题目")
    public ApiResponse<List<Quiz>> getQuizzesByChapter(
            @Parameter(description = "章节 ID", example = "1", required = true)
            @PathVariable Long chapterId) {
        List<Quiz> quizzes = quizService.getQuizzesByChapterId(chapterId);
        return ApiResponse.success(quizzes);
    }

    /**
     * 获取故事题目列表
     */
    @GetMapping("/story/{storyId}")
    @Operation(summary = "获取故事题目", description = "获取指定故事的所有题目")
    public ApiResponse<List<Quiz>> getQuizzesByStory(
            @Parameter(description = "故事 ID", example = "1", required = true)
            @PathVariable Long storyId) {
        List<Quiz> quizzes = quizService.getQuizzesByStoryId(storyId);
        return ApiResponse.success(quizzes);
    }

    /**
     * 提交答案
     */
    @PostMapping("/submit")
    @Operation(summary = "提交答案", description = "提交答题答案并获取结果")
    public ApiResponse<QuizSubmitResponse> submitAnswer(
            @RequestAttribute("userId") Long userId,
            @Valid @RequestBody QuizSubmitRequest request) {
        QuizSubmitResponse response = quizService.submitAnswer(userId, request);
        return ApiResponse.success(response);
    }

    /**
     * 获取用户答题记录
     */
    @GetMapping("/records")
    @Operation(summary = "获取答题记录", description = "获取用户的答题历史记录")
    public ApiResponse<Page<QuizRecord>> getQuizRecords(
            @RequestAttribute("userId") Long userId,
            @Parameter(description = "页码", example = "0")
            @RequestParam(defaultValue = "0") int page,
            @Parameter(description = "每页数量", example = "10")
            @RequestParam(defaultValue = "10") int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<QuizRecord> records = quizService.getUserQuizRecords(userId, pageable);
        return ApiResponse.success(records);
    }

    /**
     * 获取用户在某故事的答题记录
     */
    @GetMapping("/story/{storyId}/records")
    @Operation(summary = "获取故事答题记录", description = "获取用户在指定故事的答题记录")
    public ApiResponse<List<QuizRecord>> getStoryQuizRecords(
            @RequestAttribute("userId") Long userId,
            @Parameter(description = "故事 ID", example = "1", required = true)
            @PathVariable Long storyId) {
        List<QuizRecord> records = quizService.getUserStoryQuizRecords(userId, storyId);
        return ApiResponse.success(records);
    }

    /**
     * 检查题目是否已完成
     */
    @GetMapping("/{quizId}/completed")
    @Operation(summary = "检查题目完成状态", description = "检查用户是否已完成某题目")
    public ApiResponse<Boolean> checkQuizCompleted(
            @RequestAttribute("userId") Long userId,
            @Parameter(description = "题目 ID", example = "1", required = true)
            @PathVariable Long quizId) {
        boolean completed = quizService.hasCompletedQuiz(userId, quizId);
        return ApiResponse.success(completed);
    }

    /**
     * 获取用户答题统计
     */
    @GetMapping("/stats")
    @Operation(summary = "获取答题统计", description = "获取用户的答题统计数据")
    public ApiResponse<Object> getQuizStats(@RequestAttribute("userId") Long userId) {
        QuizService.QuizStats stats = quizService.getQuizStats(userId);
        return ApiResponse.success(new Object() {
            public long totalCount = stats.totalCount();
            public long correctCount = stats.correctCount();
            public int totalPoints = stats.totalPoints();
            public double accuracyRate = stats.totalCount() > 0 ?
                    Math.round((double) stats.correctCount() / stats.totalCount() * 100) : 0;
        });
    }
}
