package com.storyforest.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import io.swagger.v3.oas.models.servers.Server;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

/**
 * OpenAPI / Swagger 配置类
 */
@Configuration
public class OpenApiConfig {

    @Bean
    public OpenAPI storyForestOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("故事森林 API")
                        .description("""
                                ## 故事森林后端 API 文档
                                
                                面向 3-10 岁儿童的互动式故事阅读平台后端 API。
                                
                                ### 认证说明
                                大部分接口需要 JWT Token 认证，在请求头中携带：
                                ```
                                Authorization: Bearer <your_access_token>
                                ```
                                
                                ### 响应格式
                                所有接口统一返回格式：
                                ```json
                                {
                                  "code": 0,
                                  "message": "success",
                                  "data": {}
                                }
                                ```
                                
                                ### 错误码说明
                                - `0`: 成功
                                - `400`: 请求参数错误
                                - `401`: 未授权/Token 无效
                                - `403`: 权限不足
                                - `404`: 资源不存在
                                - `500`: 服务器内部错误
                                """)
                        .version("1.0.0")
                        .contact(new Contact()
                                .name("故事森林团队")
                                .email("support@storyforest.com"))
                        .license(new License()
                                .name("Copyright © 2026 故事森林")))
                .servers(List.of(
                        new Server()
                                .url("http://localhost:8080/api")
                                .description("本地开发环境"),
                        new Server()
                                .url("https://api.storyforest.com/api")
                                .description("生产环境"),
                        new Server()
                                .url("https://test-api.storyforest.com/api")
                                .description("测试环境")))
                .schemaRequirement("Bearer Authentication", new SecurityScheme()
                        .type(SecurityScheme.Type.HTTP)
                        .scheme("bearer")
                        .bearerFormat("JWT")
                        .description("JWT Token 认证，格式：Bearer <token>"))
                .security(List.of(new SecurityRequirement().addList("Bearer Authentication")));
    }
}
