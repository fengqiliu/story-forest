package com.storyforest.service.impl;

import com.storyforest.entity.Story;
import com.storyforest.repository.StoryRepository;
import com.storyforest.service.StoryService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Random;

/**
 * 故事服务实现类
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class StoryServiceImpl implements StoryService {

    private final StoryRepository storyRepository;

    @Override
    public Page<Story> getStories(Pageable pageable) {
        return storyRepository.findByStatusOrderByPlayCountDesc("PUBLISHED", pageable);
    }

    @Override
    public Page<Story> getStoriesByAgeGroup(String ageGroup, Pageable pageable) {
        return storyRepository.findByAgeGroupAndCategory(ageGroup, null, pageable);
    }

    @Override
    public Page<Story> getStoriesByCategory(String category, Pageable pageable) {
        return storyRepository.findByCategory(category, pageable);
    }

    @Override
    public Page<Story> getStoriesByAgeGroupAndCategory(String ageGroup, String category, Pageable pageable) {
        return storyRepository.findByAgeGroupAndCategory(ageGroup, category, pageable);
    }

    @Override
    public Page<Story> getRecommendedStories(Pageable pageable) {
        return storyRepository.findByIsRecommendedTrue(pageable);
    }

    @Override
    public Page<Story> getPopularStories(Pageable pageable) {
        return storyRepository.findByStatusOrderByPlayCountDesc("PUBLISHED", pageable);
    }

    @Override
    public Page<Story> searchStories(String keyword, Pageable pageable) {
        return storyRepository.searchStories(keyword, pageable);
    }

    @Override
    @Transactional(readOnly = true)
    public Story getStoryById(Long id) {
        return storyRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("故事不存在：" + id));
    }

    @Override
    @Transactional
    public void incrementPlayCount(Long storyId) {
        Story story = getStoryById(storyId);
        story.setPlayCount(story.getPlayCount() + 1);
        storyRepository.save(story);
    }

    @Override
    public List<Story> getFeaturedStories() {
        // 获取推荐故事
        Page<Story> recommendedPage = storyRepository.findByIsRecommendedTrue(
                PageRequest.of(0, 3, Sort.by("playCount").descending())
        );

        if (recommendedPage.hasContent()) {
            return recommendedPage.getContent();
        }

        // 如果没有推荐故事，返回播放量最高的 3 个
        Page<Story> popularPage = storyRepository.findByStatusOrderByPlayCountDesc(
                "PUBLISHED",
                PageRequest.of(0, 3)
        );

        return popularPage.getContent();
    }
}
