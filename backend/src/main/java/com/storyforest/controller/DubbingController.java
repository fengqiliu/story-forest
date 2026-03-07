package com.storyforest.controller;

import com.storyforest.dto.ApiResponse;
import com.storyforest.entity.DubbingRecord;
import com.storyforest.service.DubbingService;
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
 * 配音控制器
 */
@Slf4j
@RestController
@RequestMapping("/dubbing")
@RequiredArgsConstructor
@Tag(name = "角色配音", description = "配音互动相关接口")
public class DubbingController {

    private final DubbingService dubbingService;

    /**
     * 提交配音作品
     */
    @PostMapping
    @Operation(summary = "提交配音作品", description = "上传配音录音作品")
    public ApiResponse<DubbingRecord> submitDubbing(
            @RequestAttribute("userId") Long userId,
            @Parameter(description = "故事 ID", example = "1", required = true)
            @RequestParam Long storyId,
            @Parameter(description = "角色名称", example = "小红帽", required = true)
            @RequestParam String characterName,
            @Parameter(description = "音频 URL", required = true)
            @RequestParam String audioUrl,
            @Parameter(description = "音频时长（秒）", example = "30")
            @RequestParam(required = false) Integer audioDuration) {
        DubbingRecord record = dubbingService.submitDubbing(
                userId, storyId, characterName, audioUrl, audioDuration);
        return ApiResponse.success("配音提交成功", record);
    }

    /**
     * 获取用户配音记录
     */
    @GetMapping("/records")
    @Operation(summary = "获取配音记录", description = "获取用户的配音历史记录")
    public ApiResponse<Page<DubbingRecord>> getDubbingRecords(
            @RequestAttribute("userId") Long userId,
            @Parameter(description = "页码", example = "0")
            @RequestParam(defaultValue = "0") int page,
            @Parameter(description = "每页数量", example = "10")
            @RequestParam(defaultValue = "10") int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<DubbingRecord> records = dubbingService.getUserDubbingRecords(userId, pageable);
        return ApiResponse.success(records);
    }

    /**
     * 获取用户在某故事的配音记录
     */
    @GetMapping("/story/{storyId}/records")
    @Operation(summary = "获取故事配音记录", description = "获取用户在指定故事的配音记录")
    public ApiResponse<List<DubbingRecord>> getStoryDubbingRecords(
            @RequestAttribute("userId") Long userId,
            @Parameter(description = "故事 ID", example = "1", required = true)
            @PathVariable Long storyId) {
        List<DubbingRecord> records = dubbingService.getUserStoryDubbingRecords(userId, storyId);
        return ApiResponse.success(records);
    }

    /**
     * 更新配音评分
     */
    @PutMapping("/records/{recordId}/score")
    @Operation(summary = "更新配音评分", description = "为配音作品评分（家长或系统）")
    public ApiResponse<DubbingRecord> updateScore(
            @Parameter(description = "配音记录 ID", example = "1", required = true)
            @PathVariable Long recordId,
            @Parameter(description = "评分（0-100）", example = "85", required = true)
            @RequestParam Integer score) {
        DubbingRecord record = dubbingService.updateScore(recordId, score);
        return ApiResponse.success("评分成功", record);
    }

    /**
     * 获取用户配音统计
     */
    @GetMapping("/stats")
    @Operation(summary = "获取配音统计", description = "获取用户的配音统计数据")
    public ApiResponse<Object> getDubbingStats(@RequestAttribute("userId") Long userId) {
        DubbingService.DubbingStats stats = dubbingService.getDubbingStats(userId);
        return ApiResponse.success(new Object() {
            public long totalCount = stats.totalCount();
            public double averageScore = stats.averageScore();
        });
    }
}
