package com.storyforest.service;

import com.storyforest.entity.UserReadingHistory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

/**
 * 阅读历史服务接口
 */
public interface UserReadingHistoryService {

    /**
     * 获取阅读历史
     */
    Page<UserReadingHistory> getReadingHistory(Long userId, Pageable pageable);

    /**
     * 获取正在阅读的故事
     */
    List<UserReadingHistory> getInProgressStories(Long userId);

    /**
     * 获取已完成的故事
     */
    List<UserReadingHistory> getCompletedStories(Long userId);

    /**
     * 更新阅读进度
     */
    UserReadingHistory updateProgress(Long userId, Long storyId, Long chapterId, int progress, int readingTime);

    /**
     * 统计阅读历史数量
     */
    long countReadingHistory(Long userId);
}
