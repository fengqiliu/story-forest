package com.storyforest.service.impl;

import com.storyforest.entity.*;
import com.storyforest.repository.*;
import com.storyforest.service.AchievementService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

/**
 * 成就服务实现类
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class AchievementServiceImpl implements AchievementService {

    private final AchievementRepository achievementRepository;
    private final UserAchievementRepository userAchievementRepository;
    private final PointsTransactionRepository pointsTransactionRepository;
    private final UserRepository userRepository;
    private final QuizRecordRepository quizRecordRepository;
    private final DubbingRecordRepository dubbingRecordRepository;
    private final UserReadingHistoryRepository userReadingHistoryRepository;

    @Override
    @Transactional(readOnly = true)
    public List<Achievement> getAllAchievements() {
        return achievementRepository.findByIsActiveTrueOrderBySortOrder();
    }

    @Override
    @Transactional(readOnly = true)
    public List<UserAchievement> getUserAchievements(Long userId) {
        return userAchievementRepository.findByUserIdOrderByAchievedAtDesc(userId);
    }

    @Override
    @Transactional
    public List<Achievement> checkAndAwardAchievements(Long userId) {
        List<Achievement> newAchievements = new ArrayList<>();

        // 获取用户统计数据
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("用户不存在：" + userId));
        long completedStories = userReadingHistoryRepository.countCompletedStoriesByUserId(userId);
        long correctQuizzes = quizRecordRepository.countCorrectByUserId(userId);
        long dubbingCount = dubbingRecordRepository.countByUserId(userId);

        // 获取所有勋章
        List<Achievement> allAchievements = getAllAchievements();

        // 检查每个勋章是否达成
        for (Achievement achievement : allAchievements) {
            // 如果已获得，跳过
            if (userAchievementRepository.existsByUserIdAndAchievementId(userId, achievement.getId())) {
                continue;
            }

            boolean achieved = false;

            // 根据条件类型检查
            switch (achievement.getConditionType()) {
                case "COMPLETED_STORIES" -> {
                    if (completedStories >= achievement.getConditionValue()) {
                        achieved = true;
                    }
                }
                case "QUIZ_CORRECT" -> {
                    if (correctQuizzes >= achievement.getConditionValue()) {
                        achieved = true;
                    }
                }
                case "DUBBING_COUNT" -> {
                    if (dubbingCount >= achievement.getConditionValue()) {
                        achieved = true;
                    }
                }
                case "TOTAL_POINTS" -> {
                    if (user.getPoints() >= achievement.getConditionValue()) {
                        achieved = true;
                    }
                }
            }

            // 达成条件，授予勋章
            if (achieved) {
                awardAchievement(userId, achievement.getId());
                newAchievements.add(achievement);

                // 奖励积分
                if (achievement.getPoints() != null && achievement.getPoints() > 0) {
                    addPoints(userId, achievement.getPoints(), "ACHIEVEMENT",
                            "获得勋章：" + achievement.getName(), achievement.getId());
                }
            }
        }

        if (!newAchievements.isEmpty()) {
            log.info("用户获得新勋章：用户 ID={}, 勋章数={}", userId, newAchievements.size());
        }

        return newAchievements;
    }

    @Override
    @Transactional
    public UserAchievement awardAchievement(Long userId, Long achievementId) {
        // 检查是否已获得
        if (userAchievementRepository.existsByUserIdAndAchievementId(userId, achievementId)) {
            throw new RuntimeException("已获得该勋章");
        }

        UserAchievement userAchievement = UserAchievement.builder()
                .userId(userId)
                .achievementId(achievementId)
                .build();

        userAchievementRepository.save(userAchievement);
        log.info("授予用户勋章：用户 ID={}, 勋章 ID={}", userId, achievementId);

        return userAchievement;
    }

    @Override
    @Transactional(readOnly = true)
    public Page<PointsTransaction> getPointsTransactions(Long userId, Pageable pageable) {
        return pointsTransactionRepository.findByUserIdOrderByCreatedAtDesc(userId, pageable);
    }

    @Override
    @Transactional(readOnly = true)
    public int getCurrentPoints(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("用户不存在：" + userId));
        return user.getPoints();
    }

    @Override
    @Transactional(readOnly = true)
    public int getTotalEarnedPoints(Long userId) {
        return pointsTransactionRepository.sumAmountByUserId(userId);
    }

    @Override
    @Transactional(readOnly = true)
    public int getTotalSpentPoints(Long userId) {
        // 这里简化处理，实际应该单独统计支出
        return 0;
    }

    @Override
    @Transactional
    public void spendPoints(Long userId, int points, String description, Long referenceId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("用户不存在：" + userId));

        if (user.getPoints() < points) {
            throw new RuntimeException("积分不足");
        }

        // 扣除积分
        int newBalance = user.getPoints() - points;
        user.setPoints(newBalance);
        userRepository.save(user);

        // 记录积分流水
        PointsTransaction transaction = PointsTransaction.builder()
                .userId(userId)
                .amount(-points)
                .balance(newBalance)
                .type("EXCHANGE")
                .description(description)
                .referenceId(referenceId)
                .build();
        pointsTransactionRepository.save(transaction);

        log.info("用户消耗积分：用户 ID={}, 积分={}, 描述={}", userId, points, description);
    }

    /**
     * 增加积分（内部方法）
     */
    private void addPoints(Long userId, int points, String type, String description, Long referenceId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("用户不存在：" + userId));

        int newBalance = user.getPoints() + points;
        user.setPoints(newBalance);
        userRepository.save(user);

        PointsTransaction transaction = PointsTransaction.builder()
                .userId(userId)
                .amount(points)
                .balance(newBalance)
                .type(type)
                .description(description)
                .referenceId(referenceId)
                .build();
        pointsTransactionRepository.save(transaction);
    }
}
