package com.storyforest.controller;

import com.storyforest.dto.ApiResponse;
import com.storyforest.dto.FileUploadResponse;
import com.storyforest.service.FileUploadService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

/**
 * 文件上传控制器
 */
@Slf4j
@RestController
@RequestMapping("/file")
@RequiredArgsConstructor
@Tag(name = "文件上传", description = "图片、音频等文件上传接口")
public class FileUploadController {

    private final FileUploadService fileUploadService;

    /**
     * 通用文件上传
     */
    @PostMapping("/upload")
    @Operation(summary = "上传文件", description = "上传通用文件")
    public ApiResponse<FileUploadResponse> uploadFile(
            @Parameter(description = "文件", required = true)
            @RequestParam("file") MultipartFile file,
            @Parameter(description = "目录", example = "images")
            @RequestParam(defaultValue = "images") String dir) {
        FileUploadResponse response = fileUploadService.uploadFile(file, dir);
        return ApiResponse.success("上传成功", response);
    }

    /**
     * 图片上传
     */
    @PostMapping("/upload/image")
    @Operation(summary = "上传图片", description = "上传图片文件（支持 jpg/png/gif/webp）")
    public ApiResponse<FileUploadResponse> uploadImage(
            @Parameter(description = "图片文件", required = true)
            @RequestParam("file") MultipartFile file,
            @Parameter(description = "目录", example = "avatars")
            @RequestParam(defaultValue = "avatars") String dir) {
        FileUploadResponse response = fileUploadService.uploadImage(file, dir);
        return ApiResponse.success("上传成功", response);
    }

    /**
     * 音频上传
     */
    @PostMapping("/upload/audio")
    @Operation(summary = "上传音频", description = "上传音频文件（支持 mp3/wav/m4a）")
    public ApiResponse<FileUploadResponse> uploadAudio(
            @Parameter(description = "音频文件", required = true)
            @RequestParam("file") MultipartFile file,
            @Parameter(description = "目录", example = "audios")
            @RequestParam(defaultValue = "audios") String dir) {
        FileUploadResponse response = fileUploadService.uploadAudio(file, dir);
        return ApiResponse.success("上传成功", response);
    }

    /**
     * 获取上传凭证
     */
    @GetMapping("/upload/credential")
    @Operation(summary = "获取上传凭证", description = "获取文件上传凭证（用于前端直传）")
    public ApiResponse<Object> getUploadCredential(
            @Parameter(description = "文件名", example = "avatar.jpg", required = true)
            @RequestParam String filename,
            @Parameter(description = "目录", example = "avatars")
            @RequestParam(defaultValue = "avatars") String dir) {
        FileUploadService.UploadCredential credential = fileUploadService.getUploadCredential(filename, dir);
        return ApiResponse.success(new Object() {
            public String uploadUrl = credential.uploadUrl();
            public String fileUrl = credential.fileUrl();
            public String key = credential.key();
        });
    }

    /**
     * 删除文件
     */
    @DeleteMapping("/delete")
    @Operation(summary = "删除文件", description = "删除已上传的文件")
    public ApiResponse<Void> deleteFile(
            @Parameter(description = "文件 URL", example = "/uploads/images/2026/03/xxx.jpg", required = true)
            @RequestParam String fileUrl) {
        fileUploadService.deleteFile(fileUrl);
        return ApiResponse.success("删除成功", null);
    }
}
