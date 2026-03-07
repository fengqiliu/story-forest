package com.storyforest.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 文件上传响应 DTO
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "文件上传响应")
public class FileUploadResponse {

    /**
     * 文件 URL
     */
    @Schema(description = "文件 URL", example = "https://example.com/file.jpg")
    private String url;

    /**
     * 文件路径
     */
    @Schema(description = "文件路径", example = "/images/2026/03/file.jpg")
    private String path;

    /**
     * 文件大小（字节）
     */
    @Schema(description = "文件大小（字节）", example = "102400")
    private Long size;

    /**
     * 文件类型
     */
    @Schema(description = "文件类型", example = "image/jpeg")
    private String contentType;

    /**
     * 文件名称
     */
    @Schema(description = "文件名称", example = "file.jpg")
    private String filename;
}
