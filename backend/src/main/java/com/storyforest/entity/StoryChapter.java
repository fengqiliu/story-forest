package com.storyforest.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;

/**
 * 故事章节实体类
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "story_chapters")
public class StoryChapter {

    /**
     * 章节 ID
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * 所属故事 ID
     */
    @Column(name = "story_id", nullable = false)
    private Long storyId;

    /**
     * 章节标题
     */
    @Column(name = "title", nullable = false, length = 100)
    private String title;

    /**
     * 章节序号
     */
    @Column(name = "chapter_order", nullable = false)
    private Integer chapterOrder;

    /**
     * 章节内容（HTML 格式）
     */
    @Column(name = "content", columnDefinition = "TEXT")
    private String content;

    /**
     * 音频 URL
     */
    @Column(name = "audio_url", length = 500)
    private String audioUrl;

    /**
     * 音频时长（秒）
     */
    @Column(name = "audio_duration")
    private Integer audioDuration;

    /**
     * 插图 URL
     */
    @Column(name = "illustration_url", length = 500)
    private String illustrationUrl;

    /**
     * Lottie 动画 URL
     */
    @Column(name = "lottie_url", length = 500)
    private String lottieUrl;

    /**
     * 是否包含互动问题
     */
    @Column(name = "has_quiz", nullable = false)
    @Builder.Default
    private Boolean hasQuiz = false;

    /**
     * 播放次数
     */
    @Column(name = "play_count", nullable = false)
    @Builder.Default
    private Long playCount = 0L;

    /**
     * 创建时间
     */
    @CreationTimestamp
    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    /**
     * 更新时间
     */
    @UpdateTimestamp
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;
}
