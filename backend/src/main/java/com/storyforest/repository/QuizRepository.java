package com.storyforest.repository;

import com.storyforest.entity.Quiz;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 题目数据访问层
 */
@Repository
public interface QuizRepository extends JpaRepository<Quiz, Long> {

    /**
     * 获取章节的所有题目
     */
    List<Quiz> findByChapterIdOrderByQuizOrder(Long chapterId);

    /**
     * 获取故事的所有题目
     */
    List<Quiz> findByStoryIdOrderByQuizOrder(Long storyId);

    /**
     * 统计章节的题目数量
     */
    long countByChapterId(Long chapterId);

    /**
     * 统计故事的题目数量
     */
    long countByStoryId(Long storyId);

    /**
     * 获取章节的题目（按顺序）
     */
    @Query("SELECT q FROM Quiz q WHERE q.chapterId = :chapterId ORDER BY q.quizOrder")
    List<Quiz> findByChapterId(@Param("chapterId") Long chapterId);
}
