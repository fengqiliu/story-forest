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
 * 答题记录实体类
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "quiz_records")
@Schema(description = "答题记录实体")
public class QuizRecord {

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

    @Schema(description = "章节 ID", requiredMode = Schema.RequiredMode.REQUIRED)
    @Column(name = "chapter_id", nullable = false)
    private Long chapterId;

    @Schema(description = "题目 ID", requiredMode = Schema.RequiredMode.REQUIRED)
    @Column(name = "quiz_id", nullable = false)
    private Long quizId;

    @Schema(description = "用户答案", requiredMode = Schema.RequiredMode.REQUIRED)
    @Column(name = "user_answer", nullable = false, columnDefinition = "TEXT")
    private String userAnswer;

    @Schema(description = "是否正确")
    @Column(name = "is_correct")
    private Boolean isCorrect;

    @Schema(description = "获得积分", example = "10")
    @Column(name = "points_earned")
    private Integer pointsEarned;

    @Schema(description = "答题时间", accessMode = Schema.AccessMode.READ_ONLY)
    @CreationTimestamp
    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;
}
