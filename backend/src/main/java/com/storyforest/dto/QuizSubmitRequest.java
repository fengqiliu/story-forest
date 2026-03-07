package com.storyforest.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 提交答案请求 DTO
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "提交答案请求")
public class QuizSubmitRequest {

    /**
     * 题目 ID
     */
    @Schema(description = "题目 ID", example = "1", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotNull(message = "题目 ID 不能为空")
    private Long quizId;

    /**
     * 故事 ID
     */
    @Schema(description = "故事 ID", example = "1", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotNull(message = "故事 ID 不能为空")
    private Long storyId;

    /**
     * 章节 ID
     */
    @Schema(description = "章节 ID", example = "1", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotNull(message = "章节 ID 不能为空")
    private Long chapterId;

    /**
     * 用户答案
     */
    @Schema(description = "用户答案", example = "A", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotBlank(message = "答案不能为空")
    private String answer;
}
