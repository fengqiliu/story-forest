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
 * 积分流水实体类
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "points_transactions")
@Schema(description = "积分流水实体")
public class PointsTransaction {

    @Schema(description = "流水 ID", accessMode = Schema.AccessMode.READ_ONLY)
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Schema(description = "用户 ID", requiredMode = Schema.RequiredMode.REQUIRED)
    @Column(name = "user_id", nullable = false)
    private Long userId;

    @Schema(description = "积分变动（正数增加，负数减少）", example = "10", requiredMode = Schema.RequiredMode.REQUIRED)
    @Column(name = "amount", nullable = false)
    private Integer amount;

    @Schema(description = "变动后余额", example = "110", requiredMode = Schema.RequiredMode.REQUIRED)
    @Column(name = "balance", nullable = false)
    private Integer balance;

    @Schema(description = "类型", example = "READ", requiredMode = Schema.RequiredMode.REQUIRED)
    @Column(name = "type", nullable = false, length = 20)
    private String type; // READ/QUIZ/DUBBING/EXCHANGE

    @Schema(description = "描述")
    @Column(name = "description", length = 200)
    private String description;

    @Schema(description = "关联 ID")
    @Column(name = "reference_id")
    private Long referenceId;

    @Schema(description = "创建时间", accessMode = Schema.AccessMode.READ_ONLY)
    @CreationTimestamp
    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;
}
