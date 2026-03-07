package com.storyforest.repository;

import com.storyforest.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * 用户数据访问层
 */
@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    /**
     * 根据手机号查找用户
     */
    Optional<User> findByPhone(String phone);

    /**
     * 检查手机号是否存在
     */
    boolean existsByPhone(String phone);

    /**
     * 根据昵称查找用户
     */
    Optional<User> findByNickname(String nickname);

    /**
     * 统计用户总数
     */
    long count();

    /**
     * 统计 VIP 用户数量
     */
    @Query("SELECT COUNT(u) FROM User u WHERE u.isVip = true")
    long countVipUsers();

    /**
     * 获取用户阅读时长排名
     */
    @Query("SELECT u FROM User u ORDER BY u.totalReadingMinutes DESC LIMIT :limit")
    java.util.List<User> findTopByTotalReadingMinutes(@Param("limit") int limit);
}
