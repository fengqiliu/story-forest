package com.storyforest.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 提交答案响应 DTO
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "提交答案响应")
public class QuizSubmitResponse {

    /**
     * 是否正确
     */
    @Schema(description = "是否正确", example = "true")
    private Boolean isCorrect;

    /**
     * 正确答案
     */
    @Schema(description = "正确答案", example = "A")
    private String correctAnswer;

    /**
     * 用户答案
     */
    @Schema(description = "用户答案", example = "A")
    private String userAnswer;

    /**
     * 获得积分
     */
    @Schema(description = "获得积分", example = "10")
    private Integer pointsEarned;

    /**
     * 答案解析
     */
    @Schema(description = "答案解析")
    private String explanation;

    /**
     * 累计积分
     */
    @Schema(description = "累计积分", example = "100")
    private Integer totalPoints;
}
