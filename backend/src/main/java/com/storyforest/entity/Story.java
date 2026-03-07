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
import java.util.ArrayList;
import java.util.List;

/**
 * 故事实体类
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "stories")
@Schema(description = "故事实体")
public class Story {

    @Schema(description = "故事 ID", example = "1", accessMode = Schema.AccessMode.READ_ONLY)
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Schema(description = "故事标题", example = "小红帽", requiredMode = Schema.RequiredMode.REQUIRED)
    @Column(name = "title", nullable = false, length = 100)
    private String title;

    @Schema(description = "副标题", example = "经典童话故事")
    @Column(name = "subtitle", length = 200)
    private String subtitle;

    @Schema(description = "封面图 URL", example = "/images/stories/little-red-riding-hood.jpg")
    @Column(name = "cover_image", length = 500)
    private String coverImage;

    @Schema(description = "故事简介")
    @Column(name = "description", columnDefinition = "TEXT")
    private String description;

    @Schema(description = "年龄段", example = "5-6 岁", requiredMode = Schema.RequiredMode.REQUIRED)
    @Column(name = "age_group", nullable = false, length = 20)
    private String ageGroup;

    @Schema(description = "主题分类", example = "童话故事", requiredMode = Schema.RequiredMode.REQUIRED)
    @Column(name = "category", nullable = false, length = 50)
    private String category;

    @Schema(description = "时长分类", example = "short", requiredMode = Schema.RequiredMode.REQUIRED)
    @Column(name = "duration_type", nullable = false, length = 20)
    private String durationType;

    @Schema(description = "预计阅读时长（分钟）", example = "5")
    @Column(name = "reading_minutes")
    private Integer readingMinutes;

    @Schema(description = "章节数量", example = "3")
    @Column(name = "chapter_count")
    @Builder.Default
    private Integer chapterCount = 0;

    @Schema(description = "播放次数", example = "1000", accessMode = Schema.AccessMode.READ_ONLY)
    @Column(name = "play_count", nullable = false)
    @Builder.Default
    private Long playCount = 0L;

    @Schema(description = "收藏次数", example = "200", accessMode = Schema.AccessMode.READ_ONLY)
    @Column(name = "favorite_count", nullable = false)
    @Builder.Default
    private Long favoriteCount = 0L;

    @Schema(description = "平均评分", example = "4.5")
    @Column(name = "average_rating", precision = 2)
    private Double averageRating;

    @Schema(description = "是否推荐", example = "true")
    @Column(name = "is_recommended", nullable = false)
    @Builder.Default
    private Boolean isRecommended = false;

    @Schema(description = "是否 VIP 专享", example = "false")
    @Column(name = "is_vip", nullable = false)
    @Builder.Default
    private Boolean isVip = false;

    @Schema(description = "状态", example = "PUBLISHED")
    @Column(name = "status", nullable = false, length = 20)
    @Builder.Default
    private String status = "DRAFT";

    @Schema(description = "章节列表", accessMode = Schema.AccessMode.READ_ONLY)
    @OneToMany(mappedBy = "story", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @Builder.Default
    private List<StoryChapter> chapters = new ArrayList<>();

    @Schema(description = "创建时间", accessMode = Schema.AccessMode.READ_ONLY)
    @CreationTimestamp
    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @Schema(description = "更新时间", accessMode = Schema.AccessMode.READ_ONLY)
    @UpdateTimestamp
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;
}
