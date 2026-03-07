package com.storyforest.repository;

import com.storyforest.entity.QuizRecord;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 答题记录数据访问层
 */
@Repository
public interface QuizRecordRepository extends JpaRepository<QuizRecord, Long> {

    /**
     * 获取用户的答题记录
     */
    Page<QuizRecord> findByUserIdOrderByCreatedAtDesc(Long userId, Pageable pageable);

    /**
     * 获取用户在某故事的答题记录
     */
    List<QuizRecord> findByUserIdAndStoryId(Long userId, Long storyId);

    /**
     * 获取用户在某章节的答题记录
     */
    List<QuizRecord> findByUserIdAndChapterId(Long userId, Long chapterId);

    /**
     * 统计用户答题总数
     */
    long countByUserId(Long userId);

    /**
     * 统计用户答对题目数量
     */
    @Query("SELECT COUNT(r) FROM QuizRecord r WHERE r.userId = :userId AND r.isCorrect = true")
    long countCorrectByUserId(@Param("userId") Long userId);

    /**
     * 统计用户获得积分总数
     */
    @Query("SELECT COALESCE(SUM(r.pointsEarned), 0) FROM QuizRecord r WHERE r.userId = :userId")
    int sumPointsEarnedByUserId(@Param("userId") Long userId);

    /**
     * 检查用户是否已完成某题目
     */
    boolean existsByUserIdAndQuizId(Long userId, Long quizId);
}
