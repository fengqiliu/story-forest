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
 * 勋章实体类
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "achievements")
@Schema(description = "勋章实体")
public class Achievement {

    @Schema(description = "勋章 ID", accessMode = Schema.AccessMode.READ_ONLY)
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Schema(description = "勋章名称", example = "阅读新手", requiredMode = Schema.RequiredMode.REQUIRED)
    @Column(name = "name", nullable = false, length = 50)
    private String name;

    @Schema(description = "勋章描述", example = "完成第一个故事")
    @Column(name = "description", length = 200)
    private String description;

    @Schema(description = "勋章图标 URL")
    @Column(name = "icon_url", length = 500)
    private String iconUrl;

    @Schema(description = "达成条件类型", example = "COMPLETED_STORIES", requiredMode = Schema.RequiredMode.REQUIRED)
    @Column(name = "condition_type", nullable = false, length = 50)
    private String conditionType;

    @Schema(description = "达成条件值", example = "1", requiredMode = Schema.RequiredMode.REQUIRED)
    @Column(name = "condition_value", nullable = false)
    private Integer conditionValue;

    @Schema(description = "奖励积分", example = "50")
    @Column(name = "points")
    private Integer points;

    @Schema(description = "创建时间", accessMode = Schema.AccessMode.READ_ONLY)
    @CreationTimestamp
    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;
}
