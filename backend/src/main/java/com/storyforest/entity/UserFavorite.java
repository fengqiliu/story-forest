package com.storyforest.entity;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

/**
 * 用户收藏实体类
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "user_favorites")
@Schema(description = "用户收藏实体")
public class UserFavorite {

    @Schema(description = "收藏 ID", accessMode = Schema.AccessMode.READ_ONLY)
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Schema(description = "用户 ID", requiredMode = Schema.RequiredMode.REQUIRED)
    @Column(name = "user_id", nullable = false)
    private Long userId;

    @Schema(description = "故事 ID", requiredMode = Schema.RequiredMode.REQUIRED)
    @Column(name = "story_id", nullable = false)
    private Long storyId;

    @Schema(description = "收藏时间", accessMode = Schema.AccessMode.READ_ONLY)
    @CreationTimestamp
    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;
}
