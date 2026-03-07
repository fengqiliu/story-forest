package com.storyforest.repository;

import com.storyforest.entity.Story;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 故事数据访问层
 */
@Repository
public interface StoryRepository extends JpaRepository<Story, Long> {

    /**
     * 按年龄段查找故事
     */
    Page<Story> findByAgeGroup(String ageGroup, Pageable pageable);

    /**
     * 按分类查找故事
     */
    Page<Story> findByCategory(String category, Pageable pageable);

    /**
     * 按年龄段和分类查找故事
     */
    Page<Story> findByAgeGroupAndCategory(String ageGroup, String category, Pageable pageable);

    /**
     * 查找推荐故事
     */
    Page<Story> findByIsRecommendedTrue(Pageable pageable);

    /**
     * 按播放次数排序查找热门故事
     */
    Page<Story> findByStatusOrderByPlayCountDesc(String status, Pageable pageable);

    /**
     * 搜索故事（标题或描述）
     */
    @Query("SELECT s FROM Story s WHERE s.status = 'PUBLISHED' AND " +
           "(s.title LIKE %:keyword% OR s.description LIKE %:keyword%)")
    Page<Story> searchStories(@Param("keyword") String keyword, Pageable pageable);

    /**
     * 获取年龄段和分类的故事数量
     */
    @Query("SELECT COUNT(s) FROM Story s WHERE s.ageGroup = :ageGroup AND s.category = :category AND s.status = 'PUBLISHED'")
    long countByAgeGroupAndCategory(@Param("ageGroup") String ageGroup, @Param("category") String category);

    /**
     * 查找指定 ID 列表的故事
     */
    List<Story> findByIdIn(List<Long> ids);
}
