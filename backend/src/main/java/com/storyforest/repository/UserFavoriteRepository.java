package com.storyforest.repository;

import com.storyforest.entity.UserFavorite;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * 用户收藏数据访问层
 */
@Repository
public interface UserFavoriteRepository extends JpaRepository<UserFavorite, Long> {

    /**
     * 检查用户是否已收藏某故事
     */
    Optional<UserFavorite> findByUserIdAndStoryId(Long userId, Long storyId);

    /**
     * 获取用户的收藏列表
     */
    Page<UserFavorite> findByUserId(Long userId, Pageable pageable);

    /**
     * 统计用户收藏数量
     */
    long countByUserId(Long userId);

    /**
     * 删除用户收藏
     */
    void deleteByUserIdAndStoryId(Long userId, Long storyId);

    /**
     * 检查用户是否已收藏某故事（返回布尔值）
     */
    boolean existsByUserIdAndStoryId(Long userId, Long storyId);

    /**
     * 获取故事的收藏数量
     */
    @Query("SELECT COUNT(uf) FROM UserFavorite uf WHERE uf.storyId = :storyId")
    long countByStoryId(@Param("storyId") Long storyId);
}
