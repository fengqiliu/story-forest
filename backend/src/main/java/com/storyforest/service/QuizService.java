package com.storyforest.service;

import com.storyforest.dto.QuizSubmitRequest;
import com.storyforest.dto.QuizSubmitResponse;
import com.storyforest.entity.Quiz;
import com.storyforest.entity.QuizRecord;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

/**
 * 答题服务接口
 */
public interface QuizService {

    /**
     * 获取章节的题目列表
     */
    List<Quiz> getQuizzesByChapterId(Long chapterId);

    /**
     * 获取故事的题目列表
     */
    List<Quiz> getQuizzesByStoryId(Long storyId);

    /**
     * 提交答案
     */
    QuizSubmitResponse submitAnswer(Long userId, QuizSubmitRequest request);

    /**
     * 获取用户答题记录
     */
    Page<QuizRecord> getUserQuizRecords(Long userId, Pageable pageable);

    /**
     * 获取用户在某故事的答题记录
     */
    List<QuizRecord> getUserStoryQuizRecords(Long userId, Long storyId);

    /**
     * 检查用户是否已完成某题目
     */
    boolean hasCompletedQuiz(Long userId, Long quizId);

    /**
     * 获取用户答题统计
     */
    QuizStats getQuizStats(Long userId);

    /**
     * 答题统计内部类
     */
    record QuizStats(long totalCount, long correctCount, int totalPoints) {}
}
