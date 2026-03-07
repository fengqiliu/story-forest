package com.storyforest.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 文件上传凭证 DTO
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "文件上传凭证")
public class UploadCredential {

    /**
     * 上传 URL
     */
    @Schema(description = "上传 URL", example = "/api/file/upload")
    private String uploadUrl;

    /**
     * 文件 URL
     */
    @Schema(description = "文件 URL", example = "/uploads/images/2026/03/xx/file.jpg")
    private String fileUrl;

    /**
     * 文件 Key（存储路径）
     */
    @Schema(description = "文件 Key", example = "images/2026/03/xx/file.jpg")
    private String key;
}
