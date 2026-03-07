package com.storyforest.service.impl;

import com.storyforest.entity.Story;
import com.storyforest.entity.UserReadingHistory;
import com.storyforest.repository.StoryRepository;
import com.storyforest.repository.UserReadingHistoryRepository;
import com.storyforest.service.UserReadingHistoryService;
import com.storyforest.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

/**
 * 阅读历史服务实现类
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class UserReadingHistoryServiceImpl implements UserReadingHistoryService {

    private final UserReadingHistoryRepository userReadingHistoryRepository;
    private final StoryRepository storyRepository;
    private final UserService userService;

    @Override
    @Transactional(readOnly = true)
    public Page<UserReadingHistory> getReadingHistory(Long userId, Pageable pageable) {
        return userReadingHistoryRepository.findByUserIdOrderByLastReadAtDesc(userId, pageable);
    }

    @Override
    @Transactional(readOnly = true)
    public List<UserReadingHistory> getInProgressStories(Long userId) {
        return userReadingHistoryRepository.findInProgressByUserId(userId);
    }

    @Override
    @Transactional(readOnly = true)
    public List<UserReadingHistory> getCompletedStories(Long userId) {
        return userReadingHistoryRepository.findCompletedByUserId(userId);
    }

    @Override
    @Transactional
    public UserReadingHistory updateProgress(Long userId, Long storyId, Long chapterId, int progress, int readingTime) {
        // 检查故事是否存在
        Story story = storyRepository.findById(storyId)
                .orElseThrow(() -> new RuntimeException("故事不存在：" + storyId));

        // 查找或创建阅读历史
        UserReadingHistory history = userReadingHistoryRepository.findByUserIdAndStoryId(userId, storyId)
                .orElse(null);

        if (history == null) {
            // 创建新的阅读历史
            history = UserReadingHistory.builder()
                    .userId(userId)
                    .storyId(storyId)
                    .chapterId(chapterId)
                    .progress(progress)
                    .readingTime(readingTime)
                    .lastReadAt(LocalDateTime.now())
                    .build();
        } else {
            // 更新现有记录
            if (chapterId != null) {
                history.setChapterId(chapterId);
            }
            history.setProgress(Math.max(history.getProgress(), progress));
            history.setReadingTime(history.getReadingTime() + readingTime);
            history.setLastReadAt(LocalDateTime.now());
        }

        userReadingHistoryRepository.save(history);

        // 如果阅读完成，更新用户统计
        if (progress >= 100) {
            userService.addCompletedStory(userId);
        }

        // 增加阅读时长
        if (readingTime > 0) {
            userService.addReadingTime(userId, readingTime);
        }

        log.info("更新阅读进度：用户 ID={}, 故事 ID={}, 进度={}%, 时长={}秒", userId, storyId, progress, readingTime);
        return history;
    }

    @Override
    @Transactional(readOnly = true)
    public long countReadingHistory(Long userId) {
        return userReadingHistoryRepository.countDistinctStoriesByUserId(userId);
    }
}
