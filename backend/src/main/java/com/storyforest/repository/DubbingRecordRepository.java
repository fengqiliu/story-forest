package com.storyforest.repository;

import com.storyforest.entity.DubbingRecord;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 配音记录数据访问层
 */
@Repository
public interface DubbingRecordRepository extends JpaRepository<DubbingRecord, Long> {

    /**
     * 获取用户的配音记录
     */
    Page<DubbingRecord> findByUserIdOrderByCreatedAtDesc(Long userId, Pageable pageable);

    /**
     * 获取用户在某故事的配音记录
     */
    List<DubbingRecord> findByUserIdAndStoryId(Long userId, Long storyId);

    /**
     * 统计用户配音次数
     */
    long countByUserId(Long userId);

    /**
     * 统计用户配音平均评分
     */
    @Query("SELECT COALESCE(AVG(d.score), 0) FROM DubbingRecord d WHERE d.userId = :userId")
    double averageScoreByUserId(@Param("userId") Long userId);
}
