package com.storyforest.service.impl;

import com.storyforest.dto.UserResponse;
import com.storyforest.entity.Story;
import com.storyforest.entity.User;
import com.storyforest.entity.UserFavorite;
import com.storyforest.repository.StoryRepository;
import com.storyforest.repository.UserFavoriteRepository;
import com.storyforest.repository.UserRepository;
import com.storyforest.service.FavoriteService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

/**
 * 收藏服务实现类
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class FavoriteServiceImpl implements FavoriteService {

    private final UserFavoriteRepository userFavoriteRepository;
    private final StoryRepository storyRepository;
    private final UserRepository userRepository;

    @Override
    @Transactional(readOnly = true)
    public Page<UserFavorite> getUserFavorites(Long userId, Pageable pageable) {
        return userFavoriteRepository.findByUserId(userId, pageable);
    }

    @Override
    @Transactional
    public UserFavorite addFavorite(Long userId, Long storyId) {
        // 检查用户是否存在
        userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("用户不存在：" + userId));

        // 检查故事是否存在
        Story story = storyRepository.findById(storyId)
                .orElseThrow(() -> new RuntimeException("故事不存在：" + storyId));

        // 检查是否已收藏
        if (userFavoriteRepository.existsByUserIdAndStoryId(userId, storyId)) {
            throw new RuntimeException("已收藏该故事");
        }

        // 创建收藏记录
        UserFavorite favorite = UserFavorite.builder()
                .userId(userId)
                .storyId(storyId)
                .build();

        userFavoriteRepository.save(favorite);

        // 更新故事收藏数
        story.setFavoriteCount(story.getFavoriteCount() + 1);
        storyRepository.save(story);

        log.info("用户收藏故事：用户 ID={}, 故事 ID={}", userId, storyId);
        return favorite;
    }

    @Override
    @Transactional
    public void removeFavorite(Long userId, Long storyId) {
        // 检查收藏是否存在
        UserFavorite favorite = userFavoriteRepository.findByUserIdAndStoryId(userId, storyId)
                .orElseThrow(() -> new RuntimeException("未收藏该故事"));

        userFavoriteRepository.delete(favorite);

        // 更新故事收藏数
        Story story = storyRepository.findById(storyId)
                .orElseThrow(() -> new RuntimeException("故事不存在：" + storyId));
        story.setFavoriteCount(Math.max(0, story.getFavoriteCount() - 1));
        storyRepository.save(story);

        log.info("用户取消收藏：用户 ID={}, 故事 ID={}", userId, storyId);
    }

    @Override
    @Transactional(readOnly = true)
    public boolean isFavorite(Long userId, Long storyId) {
        return userFavoriteRepository.existsByUserIdAndStoryId(userId, storyId);
    }

    @Override
    @Transactional(readOnly = true)
    public long countFavorites(Long userId) {
        return userFavoriteRepository.countByUserId(userId);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Long> getFavoriteStoryIds(Long userId) {
        return userFavoriteRepository.findByUserId(userId, Pageable.unpaged())
                .stream()
                .map(UserFavorite::getStoryId)
                .collect(Collectors.toList());
    }
}
