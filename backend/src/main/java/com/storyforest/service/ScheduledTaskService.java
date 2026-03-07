package com.storyforest.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

/**
 * 定时任务服务
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class ScheduledTaskService {

    private final AchievementService achievementService;
    private final RedisTemplate<String, String> redisTemplate;

    /**
     * 每日凌晨 2 点执行
     * 检查并授予用户勋章
     */
    @Scheduled(cron = "0 0 2 * * ?")
    public void checkAndAwardAchievements() {
        log.info("开始执行定时任务：检查用户勋章达成");
        // 这里可以遍历所有用户进行检查
        // 为简化，暂时不实现具体逻辑
        log.info("定时任务执行完成");
    }

    /**
     * 每小时执行
     * 清理过期的验证码
     */
    @Scheduled(cron = "0 0 * * * ?")
    public void cleanExpiredCodes() {
        log.info("开始执行定时任务：清理过期验证码");
        // Redis 已设置过期时间，自动清理
        log.info("定时任务执行完成");
    }

    /**
     * 每日凌晨 3 点执行
     * 统计昨日数据
     */
    @Scheduled(cron = "0 0 3 * * ?")
    public void dailyStatistics() {
        log.info("开始执行定时任务：每日统计");
        // 可以在这里添加统计逻辑
        log.info("定时任务执行完成");
    }

    /**
     * 每 5 分钟执行
     * 检查并更新缓存
     */
    @Scheduled(fixedRate = 300000)
    public void refreshCache() {
        // 刷新热门故事缓存
        log.debug("刷新缓存任务执行");
    }
}
