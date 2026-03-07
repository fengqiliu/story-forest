package com.storyforest.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 认证响应 DTO
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "认证响应")
public class AuthResponse {

    /**
     * 访问 Token
     */
    @Schema(description = "访问 Token", example = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9...", requiredMode = Schema.RequiredMode.REQUIRED)
    private String accessToken;

    /**
     * 刷新 Token
     */
    @Schema(description = "刷新 Token", example = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9...", requiredMode = Schema.RequiredMode.REQUIRED)
    private String refreshToken;

    /**
     * Token 类型
     */
    @Schema(description = "Token 类型", example = "Bearer", defaultValue = "Bearer")
    @Builder.Default
    private String tokenType = "Bearer";

    /**
     * 过期时间（秒）
     */
    @Schema(description = "过期时间（秒）", example = "604800")
    private Long expiresIn;

    /**
     * 用户信息
     */
    @Schema(description = "用户信息", requiredMode = Schema.RequiredMode.REQUIRED)
    private UserInfoDTO userInfo;

    /**
     * 内部类：用户信息 DTO
     */
    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    @Schema(description = "用户信息")
    public static class UserInfoDTO {
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
        @Schema(description = "总阅读时长（分钟）", example = "0")
        private Integer totalReadingMinutes;
        @Schema(description = "已完成故事数量", example = "0")
        private Integer completedStories;
    }
}
