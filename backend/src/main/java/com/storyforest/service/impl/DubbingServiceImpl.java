package com.storyforest.service.impl;

import com.storyforest.entity.DubbingRecord;
import com.storyforest.entity.Story;
import com.storyforest.repository.DubbingRecordRepository;
import com.storyforest.repository.StoryRepository;
import com.storyforest.service.DubbingService;
import com.storyforest.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 配音服务实现类
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class DubbingServiceImpl implements DubbingService {

    private final DubbingRecordRepository dubbingRecordRepository;
    private final StoryRepository storyRepository;
    private final UserService userService;

    @Override
    @Transactional
    public DubbingRecord submitDubbing(Long userId, Long storyId, String characterName,
                                        String audioUrl, Integer audioDuration) {
        // 检查故事是否存在
        Story story = storyRepository.findById(storyId)
                .orElseThrow(() -> new RuntimeException("故事不存在：" + storyId));

        // 创建配音记录
        DubbingRecord record = DubbingRecord.builder()
                .userId(userId)
                .storyId(storyId)
                .characterName(characterName)
                .audioUrl(audioUrl)
                .audioDuration(audioDuration)
                .build();

        dubbingRecordRepository.save(record);

        // 增加积分（配音奖励）
        userService.addPoints(userId, 20, "DUBBING",
                "完成配音：" + characterName, record.getId());

        log.info("用户提交配音：用户 ID={}, 故事 ID={}, 角色={}", userId, storyId, characterName);
        return record;
    }

    @Override
    @Transactional(readOnly = true)
    public Page<DubbingRecord> getUserDubbingRecords(Long userId, Pageable pageable) {
        return dubbingRecordRepository.findByUserIdOrderByCreatedAtDesc(userId, pageable);
    }

    @Override
    @Transactional(readOnly = true)
    public List<DubbingRecord> getUserStoryDubbingRecords(Long userId, Long storyId) {
        return dubbingRecordRepository.findByUserIdAndStoryId(userId, storyId);
    }

    @Override
    @Transactional
    public DubbingRecord updateScore(Long recordId, Integer score) {
        DubbingRecord record = dubbingRecordRepository.findById(recordId)
                .orElseThrow(() -> new RuntimeException("配音记录不存在：" + recordId));

        record.setScore(score);
        dubbingRecordRepository.save(record);

        // 如果评分大于 80 分，额外奖励积分
        if (score != null && score >= 80) {
            int bonusPoints = score >= 90 ? 30 : 20;
            userService.addPoints(record.getUserId(), bonusPoints, "DUBBING",
                    "配音评分奖励：" + score + "分", recordId);
        }

        return record;
    }

    @Override
    @Transactional(readOnly = true)
    public DubbingStats getDubbingStats(Long userId) {
        long totalCount = dubbingRecordRepository.countByUserId(userId);
        double averageScore = dubbingRecordRepository.averageScoreByUserId(userId);
        return new DubbingStats(totalCount, averageScore);
    }
}
