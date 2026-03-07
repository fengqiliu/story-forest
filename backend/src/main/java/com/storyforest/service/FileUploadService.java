package com.storyforest.service;

import com.storyforest.dto.FileUploadResponse;
import com.storyforest.dto.UploadCredential;
import org.springframework.web.multipart.MultipartFile;

/**
 * 文件上传服务接口
 */
public interface FileUploadService {

    /**
     * 上传文件
     *
     * @param file 文件
     * @param dir 目录
     * @return 上传响应
     */
    FileUploadResponse uploadFile(MultipartFile file, String dir);

    /**
     * 上传图片
     *
     * @param file 图片文件
     * @param dir 目录
     * @return 上传响应
     */
    FileUploadResponse uploadImage(MultipartFile file, String dir);

    /**
     * 上传音频
     *
     * @param file 音频文件
     * @param dir 目录
     * @return 上传响应
     */
    FileUploadResponse uploadAudio(MultipartFile file, String dir);

    /**
     * 删除文件
     *
     * @param fileUrl 文件 URL
     */
    void deleteFile(String fileUrl);

    /**
     * 获取文件上传凭证（用于前端直传）
     *
     * @param filename 文件名
     * @param dir 目录
     * @return 上传凭证
     */
    UploadCredential getUploadCredential(String filename, String dir);
}
