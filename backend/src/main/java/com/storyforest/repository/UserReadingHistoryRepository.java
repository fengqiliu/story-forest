package com.storyforest.repository;

import com.storyforest.entity.UserReadingHistory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * 用户阅读历史数据访问层
 */
@Repository
public interface UserReadingHistoryRepository extends JpaRepository<UserReadingHistory, Long> {

    /**
     * 获取用户的阅读历史
     */
    Page<UserReadingHistory> findByUserIdOrderByLastReadAtDesc(Long userId, Pageable pageable);

    /**
     * 获取用户正在阅读的故事
     */
    @Query("SELECT h FROM UserReadingHistory h WHERE h.userId = :userId AND h.progress < 100 ORDER BY h.lastReadAt DESC")
    List<UserReadingHistory> findInProgressByUserId(@Param("userId") Long userId);

    /**
     * 获取用户已完成的故事
     */
    @Query("SELECT h FROM UserReadingHistory h WHERE h.userId = :userId AND h.progress >= 100 ORDER BY h.lastReadAt DESC")
    List<UserReadingHistory> findCompletedByUserId(@Param("userId") Long userId);

    /**
     * 获取用户某故事的阅读历史
     */
    Optional<UserReadingHistory> findByUserIdAndStoryId(Long userId, Long storyId);

    /**
     * 统计用户阅读故事数量
     */
    @Query("SELECT COUNT(DISTINCT h.storyId) FROM UserReadingHistory h WHERE h.userId = :userId")
    long countDistinctStoriesByUserId(@Param("userId") Long userId);

    /**
     * 统计用户已完成故事数量
     */
    @Query("SELECT COUNT(DISTINCT h.storyId) FROM UserReadingHistory h WHERE h.userId = :userId AND h.progress >= 100")
    long countCompletedStoriesByUserId(@Param("userId") Long userId);

    /**
     * 计算用户总阅读时长
     */
    @Query("SELECT COALESCE(SUM(h.readingTime), 0) FROM UserReadingHistory h WHERE h.userId = :userId")
    int sumReadingTimeByUserId(@Param("userId") Long userId);
}
