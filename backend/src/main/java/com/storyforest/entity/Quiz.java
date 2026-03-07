package com.storyforest.entity;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 题目实体类
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "quizzes")
@Schema(description = "题目实体")
public class Quiz {

    @Schema(description = "题目 ID", accessMode = Schema.AccessMode.READ_ONLY)
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Schema(description = "所属故事 ID", requiredMode = Schema.RequiredMode.REQUIRED)
    @Column(name = "story_id", nullable = false)
    private Long storyId;

    @Schema(description = "所属章节 ID", requiredMode = Schema.RequiredMode.REQUIRED)
    @Column(name = "chapter_id", nullable = false)
    private Long chapterId;

    @Schema(description = "题目类型", example = "CHOICE", requiredMode = Schema.RequiredMode.REQUIRED)
    @Column(name = "quiz_type", nullable = false, length = 20)
    private String quizType; // CHOICE/JUDGE/SORT/OPEN

    @Schema(description = "题目内容", requiredMode = Schema.RequiredMode.REQUIRED)
    @Column(name = "question", nullable = false, columnDefinition = "TEXT")
    private String question;

    @Schema(description = "选项 A")
    @Column(name = "option_a", length = 200)
    private String optionA;

    @Schema(description = "选项 B")
    @Column(name = "option_b", length = 200)
    private String optionB;

    @Schema(description = "选项 C")
    @Column(name = "option_c", length = 200)
    private String optionC;

    @Schema(description = "选项 D")
    @Column(name = "option_d", length = 200)
    private String optionD;

    @Schema(description = "正确答案", requiredMode = Schema.RequiredMode.REQUIRED)
    @Column(name = "correct_answer", nullable = false, length = 100)
    private String correctAnswer;

    @Schema(description = "答案解析")
    @Column(name = "explanation", columnDefinition = "TEXT")
    private String explanation;

    @Schema(description = "奖励积分", example = "10")
    @Column(name = "points_reward")
    private Integer pointsReward;

    @Schema(description = "题目顺序")
    @Column(name = "quiz_order")
    private Integer quizOrder;
}
