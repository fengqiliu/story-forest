package com.storyforest.entity;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;

/**
 * 用户阅读历史实体类
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "user_reading_history")
@Schema(description = "用户阅读历史实体")
public class UserReadingHistory {

    @Schema(description = "记录 ID", accessMode = Schema.AccessMode.READ_ONLY)
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Schema(description = "用户 ID", requiredMode = Schema.RequiredMode.REQUIRED)
    @Column(name = "user_id", nullable = false)
    private Long userId;

    @Schema(description = "故事 ID", requiredMode = Schema.RequiredMode.REQUIRED)
    @Column(name = "story_id", nullable = false)
    private Long storyId;

    @Schema(description = "章节 ID")
    @Column(name = "chapter_id")
    private Long chapterId;

    @Schema(description = "阅读进度（百分比）", example = "50")
    @Column(name = "progress")
    private Integer progress;

    @Schema(description = "阅读时长（秒）", example = "300")
    @Column(name = "reading_time")
    private Integer readingTime;

    @Schema(description = "最后阅读时间")
    @Column(name = "last_read_at")
    private LocalDateTime lastReadAt;

    @Schema(description = "创建时间", accessMode = Schema.AccessMode.READ_ONLY)
    @CreationTimestamp
    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @Schema(description = "更新时间", accessMode = Schema.AccessMode.READ_ONLY)
    @UpdateTimestamp
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;
}
