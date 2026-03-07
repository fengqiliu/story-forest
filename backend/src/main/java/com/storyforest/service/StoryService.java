package com.storyforest.service;

import com.storyforest.entity.Story;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

/**
 * 故事服务接口
 */
public interface StoryService {

    /**
     * 获取故事列表（分页）
     */
    Page<Story> getStories(Pageable pageable);

    /**
     * 按年龄段获取故事
     */
    Page<Story> getStoriesByAgeGroup(String ageGroup, Pageable pageable);

    /**
     * 按分类获取故事
     */
    Page<Story> getStoriesByCategory(String category, Pageable pageable);

    /**
     * 按年龄段和分类获取故事
     */
    Page<Story> getStoriesByAgeGroupAndCategory(String ageGroup, String category, Pageable pageable);

    /**
     * 获取推荐故事
     */
    Page<Story> getRecommendedStories(Pageable pageable);

    /**
     * 获取热门故事
     */
    Page<Story> getPopularStories(Pageable pageable);

    /**
     * 搜索故事
     */
    Page<Story> searchStories(String keyword, Pageable pageable);

    /**
     * 获取故事详情
     */
    Story getStoryById(Long id);

    /**
     * 增加播放次数
     */
    void incrementPlayCount(Long storyId);

    /**
     * 获取推荐故事列表（用于首页推荐）
     */
    List<Story> getFeaturedStories();
}
