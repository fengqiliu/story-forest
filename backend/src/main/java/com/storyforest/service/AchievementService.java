package com.storyforest.service;

import com.storyforest.entity.Achievement;
import com.storyforest.entity.UserAchievement;
import com.storyforest.entity.PointsTransaction;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

/**
 * 成就服务接口
 */
public interface AchievementService {

    /**
     * 获取所有勋章
     */
    List<Achievement> getAllAchievements();

    /**
     * 获取用户已获得的勋章
     */
    List<UserAchievement> getUserAchievements(Long userId);

    /**
     * 检查并授予用户应得的勋章
     */
    List<Achievement> checkAndAwardAchievements(Long userId);

    /**
     * 授予用户勋章
     */
    UserAchievement awardAchievement(Long userId, Long achievementId);

    /**
     * 获取积分流水
     */
    Page<PointsTransaction> getPointsTransactions(Long userId, Pageable pageable);

    /**
     * 获取当前积分
     */
    int getCurrentPoints(Long userId);

    /**
     * 获取累计获得积分
     */
    int getTotalEarnedPoints(Long userId);

    /**
     * 获取累计消耗积分
     */
    int getTotalSpentPoints(Long userId);

    /**
     * 消耗积分
     */
    void spendPoints(Long userId, int points, String description, Long referenceId);
}
