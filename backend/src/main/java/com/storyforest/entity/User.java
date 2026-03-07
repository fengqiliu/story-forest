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
 * 用户实体类
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "users")
@Schema(description = "用户实体")
public class User {

    @Schema(description = "用户 ID", example = "1", accessMode = Schema.AccessMode.READ_ONLY)
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Schema(description = "手机号", example = "13800138001", requiredMode = Schema.RequiredMode.REQUIRED)
    @Column(name = "phone", unique = true, nullable = false, length = 11)
    private String phone;

    @Schema(description = "密码（加密存储）", accessMode = Schema.AccessMode.WRITE_ONLY)
    @Column(name = "password", nullable = false)
    private String password;

    @Schema(description = "昵称", example = "快乐小书虫")
    @Column(name = "nickname", length = 50)
    private String nickname;

    @Schema(description = "头像 URL", example = "https://example.com/avatar.jpg")
    @Column(name = "avatar", length = 500)
    private String avatar;

    @Schema(description = "年龄段", example = "5-6 岁")
    @Column(name = "age_group", length = 20)
    private String ageGroup;

    @Schema(description = "是否 VIP", example = "false")
    @Column(name = "is_vip", nullable = false)
    @Builder.Default
    private Boolean isVip = false;

    @Schema(description = "积分", example = "100")
    @Column(name = "points", nullable = false)
    @Builder.Default
    private Integer points = 0;

    @Schema(description = "总阅读时长（分钟）", example = "120")
    @Column(name = "total_reading_minutes", nullable = false)
    @Builder.Default
    private Integer totalReadingMinutes = 0;

    @Schema(description = "已完成故事数量", example = "5")
    @Column(name = "completed_stories", nullable = false)
    @Builder.Default
    private Integer completedStories = 0;

    @Schema(description = "账号状态", example = "ACTIVE")
    @Column(name = "status", nullable = false, length = 20)
    @Builder.Default
    private String status = "ACTIVE";

    @Schema(description = "创建时间", accessMode = Schema.AccessMode.READ_ONLY)
    @CreationTimestamp
    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @Schema(description = "更新时间", accessMode = Schema.AccessMode.READ_ONLY)
    @UpdateTimestamp
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @Schema(description = "最后登录时间", accessMode = Schema.AccessMode.READ_ONLY)
    @Column(name = "last_login_at")
    private LocalDateTime lastLoginAt;
}
