package com.storyforest.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 用户信息更新请求 DTO
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "用户信息更新请求")
public class UserUpdateRequest {

    /**
     * 昵称
     */
    @Schema(description = "昵称", example = "快乐小书虫")
    @Size(max = 50, message = "昵称长度不能超过 50 个字符")
    private String nickname;

    /**
     * 头像 URL
     */
    @Schema(description = "头像 URL", example = "https://example.com/avatar.jpg")
    @Size(max = 500, message = "头像 URL 长度不能超过 500 个字符")
    private String avatar;

    /**
     * 年龄段
     */
    @Schema(description = "年龄段", example = "5-6 岁")
    @Size(max = 20, message = "年龄段长度不能超过 20 个字符")
    private String ageGroup;
}
