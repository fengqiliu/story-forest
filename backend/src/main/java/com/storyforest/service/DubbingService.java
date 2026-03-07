package com.storyforest.service;

import com.storyforest.entity.DubbingRecord;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

/**
 * 配音服务接口
 */
public interface DubbingService {

    /**
     * 提交配音作品
     */
    DubbingRecord submitDubbing(Long userId, Long storyId, String characterName,
                                 String audioUrl, Integer audioDuration);

    /**
     * 获取用户配音记录
     */
    Page<DubbingRecord> getUserDubbingRecords(Long userId, Pageable pageable);

    /**
     * 获取用户在某故事的配音记录
     */
    List<DubbingRecord> getUserStoryDubbingRecords(Long userId, Long storyId);

    /**
     * 更新配音评分
     */
    DubbingRecord updateScore(Long recordId, Integer score);

    /**
     * 获取用户配音统计
     */
    DubbingStats getDubbingStats(Long userId);

    /**
     * 配音统计内部类
     */
    record DubbingStats(long totalCount, double averageScore) {}
}
