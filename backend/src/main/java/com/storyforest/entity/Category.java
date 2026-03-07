package com.storyforest.entity;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 故事分类实体类
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "categories")
@Schema(description = "故事分类实体")
public class Category {

    @Schema(description = "分类 ID", accessMode = Schema.AccessMode.READ_ONLY)
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Schema(description = "分类名称", example = "童话故事", requiredMode = Schema.RequiredMode.REQUIRED)
    @Column(name = "name", nullable = false, unique = true, length = 50)
    private String name;

    @Schema(description = "分类图标 URL")
    @Column(name = "icon_url", length = 500)
    private String iconUrl;

    @Schema(description = "分类描述")
    @Column(name = "description", length = 200)
    private String description;

    @Schema(description = "排序", example = "1")
    @Column(name = "sort_order")
    private Integer sortOrder;

    @Schema(description = "是否启用", example = "true")
    @Column(name = "is_active")
    @Builder.Default
    private Boolean isActive = true;

    @Schema(description = "年龄段", example = "all")
    @Column(name = "age_group", length = 20)
    private String ageGroup; // all/3-4/5-6/7-8/9-10
}
