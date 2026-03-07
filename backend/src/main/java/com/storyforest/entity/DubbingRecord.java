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
 * 配音记录实体类
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "dubbing_records")
@Schema(description = "配音记录实体")
public class DubbingRecord {

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

    @Schema(description = "角色名称", requiredMode = Schema.RequiredMode.REQUIRED)
    @Column(name = "character_name", nullable = false, length = 50)
    private String characterName;

    @Schema(description = "录音 URL")
    @Column(name = "audio_url", length = 500)
    private String audioUrl;

    @Schema(description = "录音时长（秒）")
    @Column(name = "audio_duration")
    private Integer audioDuration;

    @Schema(description = "评分")
    @Column(name = "score")
    private Integer score;

    @Schema(description = "配音时间", accessMode = Schema.AccessMode.READ_ONLY)
    @CreationTimestamp
    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;
}
