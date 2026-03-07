package com.storyforest.repository;

import com.storyforest.entity.UserAchievement;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * 用户勋章数据访问层
 */
@Repository
public interface UserAchievementRepository extends JpaRepository<UserAchievement, Long> {

    /**
     * 获取用户的所有勋章
     */
    List<UserAchievement> findByUserIdOrderByAchievedAtDesc(Long userId);

    /**
     * 检查用户是否已获得某勋章
     */
    Optional<UserAchievement> findByUserIdAndAchievementId(Long userId, Long achievementId);

    /**
     * 检查用户是否已获得某勋章（返回布尔值）
     */
    boolean existsByUserIdAndAchievementId(Long userId, Long achievementId);

    /**
     * 统计用户勋章数量
     */
    long countByUserId(Long userId);

    /**
     * 获取用户已获得的勋章 ID 列表
     */
    @Query("SELECT ua.achievementId FROM UserAchievement ua WHERE ua.userId = :userId")
    List<Long> findAchievementIdsByUserId(@Param("userId") Long userId);
}
