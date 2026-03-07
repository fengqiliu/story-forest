package com.storyforest.service.impl;

import com.storyforest.dto.FileUploadResponse;
import com.storyforest.dto.UploadCredential;
import com.storyforest.service.FileUploadService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

/**
 * 文件上传服务实现类（本地存储）
 * 生产环境可替换为腾讯云 COS 实现
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class FileUploadServiceImpl implements FileUploadService {

    @Value("${file.upload.path:./uploads}")
    private String uploadPath;

    @Value("${file.max-size:10485760}")
    private Long maxSize;

    private static final List<String> ALLOWED_IMAGE_TYPES = Arrays.asList(
            "image/jpeg", "image/jpg", "image/png", "image/gif", "image/webp"
    );

    private static final List<String> ALLOWED_AUDIO_TYPES = Arrays.asList(
            "audio/mpeg", "audio/mp3", "audio/wav", "audio/mp4", "audio/aac"
    );

    @Override
    public FileUploadResponse uploadFile(MultipartFile file, String dir) {
        validateFile(file);

        try {
            // 生成文件路径
            String dateDir = LocalDate.now().format(DateTimeFormatter.ofPattern("yyyy/MM/dd"));
            String targetDir = uploadPath + File.separator + dir + File.separator + dateDir;

            // 创建目录
            Path targetPath = Paths.get(targetDir);
            if (!Files.exists(targetPath)) {
                Files.createDirectories(targetPath);
            }

            // 生成文件名
            String originalFilename = file.getOriginalFilename();
            String extension = originalFilename != null && originalFilename.contains(".") ?
                    originalFilename.substring(originalFilename.lastIndexOf(".")) : "";
            String filename = UUID.randomUUID().toString().replace("-", "") + extension;

            // 保存文件
            Path filePath = targetPath.resolve(filename);
            file.transferTo(filePath.toFile());

            // 生成访问 URL
            String fileUrl = "/uploads/" + dir + "/" + dateDir + "/" + filename;

            log.info("文件上传成功：{}, 大小：{} bytes", fileUrl, file.getSize());

            return FileUploadResponse.builder()
                    .url(fileUrl)
                    .path(targetDir + File.separator + filename)
                    .size(file.getSize())
                    .contentType(file.getContentType())
                    .filename(filename)
                    .build();

        } catch (IOException e) {
            log.error("文件上传失败", e);
            throw new RuntimeException("文件上传失败：" + e.getMessage());
        }
    }

    @Override
    public FileUploadResponse uploadImage(MultipartFile file, String dir) {
        if (!ALLOWED_IMAGE_TYPES.contains(file.getContentType())) {
            throw new RuntimeException("不支持的图片类型：" + file.getContentType());
        }
        return uploadFile(file, dir);
    }

    @Override
    public FileUploadResponse uploadAudio(MultipartFile file, String dir) {
        if (!ALLOWED_AUDIO_TYPES.contains(file.getContentType())) {
            throw new RuntimeException("不支持的音频类型：" + file.getContentType());
        }
        return uploadFile(file, dir);
    }

    @Override
    public void deleteFile(String fileUrl) {
        try {
            // 将 URL 转换为本地路径
            String localPath = fileUrl.replace("/uploads", uploadPath);
            Path path = Paths.get(localPath);

            if (Files.exists(path)) {
                Files.delete(path);
                log.info("文件删除成功：{}", fileUrl);
            }
        } catch (IOException e) {
            log.error("文件删除失败：{}", fileUrl, e);
        }
    }

    @Override
    public UploadCredential getUploadCredential(String filename, String dir) {
        // 本地存储模式下，返回简单的上传 URL
        // 生产环境应返回腾讯云 COS 的临时凭证
        String dateDir = LocalDate.now().format(DateTimeFormatter.ofPattern("yyyy/MM/dd"));
        String key = dir + "/" + dateDir + "/" + UUID.randomUUID().toString().replace("-", "") + "_" + filename;
        String fileUrl = "/uploads/" + key;

        return new UploadCredential("/api/file/upload", fileUrl, key);
    }

    /**
     * 验证文件
     */
    private void validateFile(MultipartFile file) {
        if (file.isEmpty()) {
            throw new RuntimeException("上传文件不能为空");
        }

        if (file.getSize() > maxSize) {
            throw new RuntimeException("文件大小超过限制：" + (maxSize / 1024 / 1024) + "MB");
        }
    }
}
