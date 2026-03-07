package com.storyforest.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * 用户信息响应 DTO
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "用户信息响应")
public class UserResponse {

    @Schema(description = "用户 ID", example = "1")
    private Long id;

    @Schema(description = "手机号", example = "13800138001")
    private String phone;

    @Schema(description = "昵称", example = "快乐小书虫")
    private String nickname;

    @Schema(description = "头像 URL", example = "https://example.com/avatar.jpg")
    private String avatar;

    @Schema(description = "年龄段", example = "5-6 岁")
    private String ageGroup;

    @Schema(description = "是否 VIP", example = "false")
    private Boolean isVip;

    @Schema(description = "积分", example = "100")
    private Integer points;

    @Schema(description = "总阅读时长（分钟）", example = "120")
    private Integer totalReadingMinutes;

    @Schema(description = "已完成故事数量", example = "5")
    private Integer completedStories;

    @Schema(description = "注册时间", example = "2026-01-01 10:00:00")
    private LocalDateTime createdAt;

    @Schema(description = "最后登录时间", example = "2026-03-06 15:30:00")
    private LocalDateTime lastLoginAt;
}
