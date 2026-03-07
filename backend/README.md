# 故事森林后端服务

> 面向 3-10 岁儿童的互动式故事阅读平台后端 API

[![Java](https://img.shields.io/badge/Java-21-blue.svg)](https://openjdk.java.net/)
[![Spring Boot](https://img.shields.io/badge/Spring%20Boot-3.2.0-brightgreen.svg)](https://spring.io/projects/spring-boot)
[![License](https://img.shields.io/badge/License-MIT-yellow.svg)](LICENSE)

## 快速开始

### 使用 Docker Compose（推荐）

```bash
# 启动所有服务（MySQL + Redis + App）
docker-compose up -d

# 查看日志
docker-compose logs -f app

# 停止服务
docker-compose down
```

访问 http://localhost:8080/api/swagger-ui.html

### 本地开发

#### 环境要求

- JDK 17+
- MySQL 8.0+
- Redis 7.0+
- Maven 3.6+

#### 启动步骤

```bash
# 1. 初始化数据库
mysql -u root -p < src/main/resources/db/init.sql

# 2. 启动 Redis
redis-server

# 3. 启动应用
mvn spring-boot:run
```

## 技术栈

| 技术 | 版本 | 用途 |
|------|------|------|
| JDK | 21 | Java 运行时 |
| Spring Boot | 3.2.0 | Web 框架 |
| Spring Security | 6.x | 安全认证 |
| Spring Data JPA | 3.x | ORM |
| MySQL | 8.0+ | 数据库 |
| Redis | 7.0+ | 缓存 |
| JWT | 0.12.3 | Token 认证 |
| OpenAPI | 3.0 | API 文档 |
| Lombok | - | 代码简化 |

## API 文档

启动服务后访问：

- **Swagger UI**: http://localhost:8080/api/swagger-ui.html
- **OpenAPI JSON**: http://localhost:8080/api/v3/api-docs

## 核心功能

### 认证模块
- [x] 用户登录（手机号 + 密码）
- [x] 用户注册（手机号 + 验证码）
- [x] JWT Token 生成与验证
- [x] Token 刷新
- [x] 短信验证码

### 故事模块
- [x] 故事列表（分页、排序）
- [x] 故事详情
- [x] 故事搜索
- [x] 故事推荐
- [x] 按年龄/分类筛选

### 用户中心
- [x] 个人信息管理
- [x] 收藏功能
- [x] 阅读历史
- [x] 阅读进度跟踪

### 互动功能
- [x] 答题闯关
- [x] 角色配音
- [x] 积分系统
- [x] 勋章成就

### 家长中心
- [x] 成长报告
- [x] 时长限制
- [x] 时间段管理

## 项目结构

```
backend/
├── src/main/java/com/storyforest/
│   ├── config/              # 配置类
│   ├── controller/          # 控制器
│   ├── dto/                 # 数据传输对象
│   ├── entity/              # 实体类
│   ├── exception/           # 异常处理
│   ├── repository/          # 数据访问层
│   ├── security/            # 安全相关
│   ├── service/             # 服务层
│   ├── util/                # 工具类
│   └── StoryForestApplication.java
├── src/main/resources/
│   ├── application.yml      # 应用配置
│   └── db/init.sql          # 数据库初始化脚本
├── src/test/java/           # 单元测试
├── Dockerfile               # Docker 镜像
├── docker-compose.yml       # Docker Compose 配置
├── pom.xml                  # Maven 配置
└── openapi.yaml             # OpenAPI 规范
```

## 配置说明

### 数据库配置

```yaml
spring:
  datasource:
    url: jdbc:mysql://localhost:3306/story_forest?...
    username: root
    password: your_password
```

### Redis 配置

```yaml
spring:
  data:
    redis:
      host: localhost
      port: 6379
```

### JWT 配置

```yaml
jwt:
  secret: "your-secret-key"
  expiration: 604800000  # 7 天
```

## 测试账号

| 手机号 | 密码 | 说明 |
|--------|------|------|
| 13800138001 | 123456 | 普通用户 |
| 13800138002 | 123456 | VIP 用户 |

## 开发指南

### 运行测试

```bash
mvn test
```

### 构建 Jar

```bash
mvn clean package
```

### 运行 Jar

```bash
java -jar target/story-forest-backend-1.0.0-SNAPSHOT.jar
```

### 构建 Docker 镜像

```bash
docker build -t story-forest-backend .
```

## 许可证

MIT License
