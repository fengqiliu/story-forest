package com.storyforest.service;

import com.storyforest.dto.ChangePasswordRequest;
import com.storyforest.dto.UserResponse;
import com.storyforest.dto.UserUpdateRequest;
import com.storyforest.entity.User;

/**
 * 用户服务接口
 */
public interface UserService {

    /**
     * 获取用户信息
     */
    UserResponse getUserInfo(Long userId);

    /**
     * 更新用户信息
     */
    UserResponse updateUserInfo(Long userId, UserUpdateRequest request);

    /**
     * 修改密码
     */
    void changePassword(Long userId, ChangePasswordRequest request);

    /**
     * 设置年龄段
     */
    UserResponse setAgeGroup(Long userId, String ageGroup);

    /**
     * 增加阅读时长
     */
    void addReadingTime(Long userId, int seconds);

    /**
     * 增加已完成故事数
     */
    void addCompletedStory(Long userId);

    /**
     * 增加积分
     */
    void addPoints(Long userId, int points, String type, String description, Long referenceId);
}
