package com.storyforest.service;

import com.storyforest.dto.UserResponse;
import com.storyforest.entity.User;
import com.storyforest.entity.UserFavorite;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

/**
 * 收藏服务接口
 */
public interface FavoriteService {

    /**
     * 获取用户收藏列表
     */
    Page<UserFavorite> getUserFavorites(Long userId, Pageable pageable);

    /**
     * 添加收藏
     */
    UserFavorite addFavorite(Long userId, Long storyId);

    /**
     * 取消收藏
     */
    void removeFavorite(Long userId, Long storyId);

    /**
     * 检查是否已收藏
     */
    boolean isFavorite(Long userId, Long storyId);

    /**
     * 统计收藏数量
     */
    long countFavorites(Long userId);

    /**
     * 获取用户收藏的故事 ID 列表
     */
    List<Long> getFavoriteStoryIds(Long userId);
}
