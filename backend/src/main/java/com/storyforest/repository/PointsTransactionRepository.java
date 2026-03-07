package com.storyforest.repository;

import com.storyforest.entity.PointsTransaction;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 * 积分流水数据访问层
 */
@Repository
public interface PointsTransactionRepository extends JpaRepository<PointsTransaction, Long> {

    /**
     * 获取用户的积分流水
     */
    Page<PointsTransaction> findByUserIdOrderByCreatedAtDesc(Long userId, Pageable pageable);

    /**
     * 统计用户总积分变动
     */
    @Query("SELECT COALESCE(SUM(p.amount), 0) FROM PointsTransaction p WHERE p.userId = :userId")
    int sumAmountByUserId(@Param("userId") Long userId);

    /**
     * 获取用户某类型的积分流水
     */
    Page<PointsTransaction> findByUserIdAndTypeOrderByCreatedAtDesc(Long userId, String type, Pageable pageable);
}
