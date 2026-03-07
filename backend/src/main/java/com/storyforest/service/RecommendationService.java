package com.storyforest.service;

import com.storyforest.entity.Story;
import com.storyforest.entity.User;
import com.storyforest.repository.StoryRepository;
import com.storyforest.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

/**
 * AI 推荐服务
 * 基于用户行为和协同过滤的个性化推荐
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class RecommendationService {

    private final StoryRepository storyRepository;
    private final UserRepository userRepository;
    private final RedisTemplate<String, Object> redisTemplate;

    private static final String USER_PREFERENCE_KEY = "user:preference:";
    private static final String RECOMMENDATION_KEY = "recommendation:";
    private static final long CACHE_EXPIRE_HOURS = 24;

    /**
     * 获取个性化推荐故事
     */
    public List<Story> getPersonalizedRecommendations(Long userId, int limit) {
        // 尝试从缓存获取
        String cacheKey = RECOMMENDATION_KEY + userId;
        List<Story> cached = getCachedRecommendations(cacheKey);
        if (cached != null && !cached.isEmpty()) {
            return cached;
        }

        // 获取用户信息
        User user = userRepository.findById(userId).orElse(null);
        if (user == null) {
            return getPopularStories(limit);
        }

        // 基于用户年龄段推荐
        List<Story> recommendations = getRecommendationsByAgeGroup(user.getAgeGroup(), limit);

        // 缓存结果
        cacheRecommendations(cacheKey, recommendations);

        return recommendations;
    }

    /**
     * 获取相似故事推荐
     */
    public List<Story> getSimilarStories(Long storyId, int limit) {
        Story story = storyRepository.findById(storyId).orElse(null);
        if (story == null) {
            return Collections.emptyList();
        }

        // 基于分类和年龄段推荐相似故事
        return storyRepository.findByAgeGroupAndCategory(
                        story.getAgeGroup(), story.getCategory(),
                        org.springframework.data.domain.PageRequest.of(0, limit))
                .stream()
                .filter(s -> !s.getId().equals(storyId))
                .limit(limit)
                .collect(Collectors.toList());
    }

    /**
     * 记录用户行为
     */
    public void recordUserAction(Long userId, String actionType, Long storyId) {
        String key = USER_PREFERENCE_KEY + userId;

        // 使用 Hash 存储用户偏好
        redisTemplate.opsForHash().increment(key, actionType + ":" + storyId, 1);
        redisTemplate.expire(key, CACHE_EXPIRE_HOURS, TimeUnit.HOURS);

        log.debug("记录用户行为：用户 ID={}, 类型={}, 故事 ID={}", userId, actionType, storyId);
    }

    /**
     * 获取热门故事
     */
    public List<Story> getPopularStories(int limit) {
        return storyRepository.findByStatusOrderByPlayCountDesc("PUBLISHED",
                        org.springframework.data.domain.PageRequest.of(0, limit))
                .stream()
                .toList();
    }

    /**
     * 根据年龄段获取推荐
     */
    private List<Story> getRecommendationsByAgeGroup(String ageGroup, int limit) {
        // 优先获取推荐故事
        var recommendedPage = storyRepository.findByIsRecommendedTrue(
                org.springframework.data.domain.PageRequest.of(0, limit));

        if (recommendedPage.hasContent() && recommendedPage.getTotalElements() >= limit) {
            return recommendedPage.getContent();
        }

        // 如果推荐故事不足，补充同年龄段的故事
        List<Story> result = new ArrayList<>(recommendedPage.getContent());
        int remaining = limit - result.size();

        if (remaining > 0 && ageGroup != null) {
            var ageGroupPage = storyRepository.findByAgeGroup(ageGroup,
                    org.springframework.data.domain.PageRequest.of(0, remaining));
            ageGroupPage.forEach(story -> {
                if (result.stream().noneMatch(s -> s.getId().equals(story.getId()))) {
                    result.add(story);
                }
            });
        }

        return result;
    }

    /**
     * 从缓存获取推荐
     */
    @SuppressWarnings("unchecked")
    private List<Story> getCachedRecommendations(String cacheKey) {
        Object cached = redisTemplate.opsForValue().get(cacheKey);
        if (cached instanceof List) {
            return (List<Story>) cached;
        }
        return null;
    }

    /**
     * 缓存推荐结果
     */
    public void cacheRecommendations(String cacheKey, List<Story> stories) {
        redisTemplate.opsForValue().set(cacheKey, stories, CACHE_EXPIRE_HOURS, TimeUnit.HOURS);
    }

    /**
     * 清除用户推荐缓存
     */
    public void clearUserRecommendationCache(Long userId) {
        String cacheKey = RECOMMENDATION_KEY + userId;
        redisTemplate.delete(cacheKey);
    }
}
