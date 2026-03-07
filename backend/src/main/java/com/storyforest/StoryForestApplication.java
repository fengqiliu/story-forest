package com.storyforest;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.scheduling.annotation.EnableScheduling;

/**
 * 故事森林后端服务启动类
 */
@SpringBootApplication
@EnableScheduling
public class StoryForestApplication {

    public static void main(String[] args) {
        SpringApplication.run(StoryForestApplication.class, args);
    }
}
