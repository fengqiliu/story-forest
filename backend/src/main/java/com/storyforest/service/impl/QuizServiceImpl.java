package com.storyforest.service.impl;

import com.storyforest.dto.QuizSubmitRequest;
import com.storyforest.dto.QuizSubmitResponse;
import com.storyforest.entity.Quiz;
import com.storyforest.entity.QuizRecord;
import com.storyforest.repository.QuizRecordRepository;
import com.storyforest.repository.QuizRepository;
import com.storyforest.service.QuizService;
import com.storyforest.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 答题服务实现类
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class QuizServiceImpl implements QuizService {

    private final QuizRepository quizRepository;
    private final QuizRecordRepository quizRecordRepository;
    private final UserService userService;

    @Override
    @Transactional(readOnly = true)
    public List<Quiz> getQuizzesByChapterId(Long chapterId) {
        return quizRepository.findByChapterIdOrderByQuizOrder(chapterId);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Quiz> getQuizzesByStoryId(Long storyId) {
        return quizRepository.findByStoryIdOrderByQuizOrder(storyId);
    }

    @Override
    @Transactional
    public QuizSubmitResponse submitAnswer(Long userId, QuizSubmitRequest request) {
        // 获取题目
        Quiz quiz = quizRepository.findById(request.getQuizId())
                .orElseThrow(() -> new RuntimeException("题目不存在：" + request.getQuizId()));

        // 检查是否已答题
        if (quizRecordRepository.existsByUserIdAndQuizId(userId, request.getQuizId())) {
            throw new RuntimeException("该题目已作答");
        }

        // 判断答案是否正确
        boolean isCorrect = quiz.getCorrectAnswer().equalsIgnoreCase(request.getAnswer());

        // 计算积分
        int pointsEarned = isCorrect ? (quiz.getPointsReward() != null ? quiz.getPointsReward() : 10) : 0;

        // 保存答题记录
        QuizRecord record = QuizRecord.builder()
                .userId(userId)
                .storyId(request.getStoryId())
                .chapterId(request.getChapterId())
                .quizId(request.getQuizId())
                .userAnswer(request.getAnswer())
                .isCorrect(isCorrect)
                .pointsEarned(pointsEarned)
                .build();
        quizRecordRepository.save(record);

        // 增加积分
        if (pointsEarned > 0) {
            userService.addPoints(userId, pointsEarned, "QUIZ",
                    "答题正确：" + quiz.getQuestion().substring(0, Math.min(20, quiz.getQuestion().length())),
                    record.getId());
        }

        // 获取累计积分
        int totalPoints = quizRecordRepository.sumPointsEarnedByUserId(userId);

        log.info("用户答题：用户 ID={}, 题目 ID={}, 答案={}, 正确={}",
                userId, request.getQuizId(), request.getAnswer(), isCorrect);

        return QuizSubmitResponse.builder()
                .isCorrect(isCorrect)
                .correctAnswer(quiz.getCorrectAnswer())
                .userAnswer(request.getAnswer())
                .pointsEarned(pointsEarned)
                .explanation(quiz.getExplanation())
                .totalPoints(totalPoints)
                .build();
    }

    @Override
    @Transactional(readOnly = true)
    public Page<QuizRecord> getUserQuizRecords(Long userId, Pageable pageable) {
        return quizRecordRepository.findByUserIdOrderByCreatedAtDesc(userId, pageable);
    }

    @Override
    @Transactional(readOnly = true)
    public List<QuizRecord> getUserStoryQuizRecords(Long userId, Long storyId) {
        return quizRecordRepository.findByUserIdAndStoryId(userId, storyId);
    }

    @Override
    @Transactional(readOnly = true)
    public boolean hasCompletedQuiz(Long userId, Long quizId) {
        return quizRecordRepository.existsByUserIdAndQuizId(userId, quizId);
    }

    @Override
    @Transactional(readOnly = true)
    public QuizStats getQuizStats(Long userId) {
        long totalCount = quizRecordRepository.countByUserId(userId);
        long correctCount = quizRecordRepository.countCorrectByUserId(userId);
        int totalPoints = quizRecordRepository.sumPointsEarnedByUserId(userId);
        return new QuizStats(totalCount, correctCount, totalPoints);
    }
}
