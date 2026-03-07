# CLAUDE.md

This file provides guidance to Claude Code (claude.ai/code) when working with code in this repository.

## Project Overview

**故事森林 (Story Forest)** - 面向 3-10 岁儿童的互动式故事阅读小程序

- **目标用户**: 3-6 岁（听故事）、7-10 岁（自主阅读）、家长（控制/记录）
- **核心功能**: 故事库、互动答题、角色扮演配音、家长控制、成长体系
- **开发阶段**: v0.8 - 前端功能基本完成，待后端 API 对接
- **代码仓库**: 前后端分离，前端为 uni-app 多端应用，后端为 Spring Boot REST API

## Quick Start

### Frontend Development
```bash
cd frontend

# Install dependencies
npm install

# Development mode - WeChat Mini Program
npm run dev:mp-weixin

# Development mode - H5 (Material Design)
npm run dev:h5

# Build production - WeChat Mini Program
npm run build:mp-weixin

# Build production - H5
npm run build:h5
```

### Backend Development
```bash
cd backend

# Start with Docker Compose (MySQL + Redis + App)
docker-compose up -d

# Local development with Maven
mvn spring-boot:run

# Run tests
mvn test

# Build JAR
mvn clean package
```

## Tech Stack

### Frontend
- **Framework**: uni-app 3.x (Vue 3 Composition API)
- **UI Library**: uView Plus (小程序/App), Custom Material Design 3 (H5)
- **State Management**: Pinia 2.0.33
- **Build Tool**: Vite 4.5 with uni-app plugin
- **Preprocessor**: Sass 1.62 with Material 3 design tokens
- **Target Platforms**: 微信小程序 / H5 / App (uni-app multi-platform)

### Backend
- **Java**: JDK 17 (Spring Boot 3.2.0)
- **API Framework**: Spring Boot Web, Spring Security, Spring Data JPA
- **Database**: MySQL 8.0 (JPA), Redis 7.0 (cache/session)
- **Authentication**: JWT with Spring Security
- **API Documentation**: SpringDoc OpenAPI 3.0 (Swagger UI)
- **Build Tool**: Maven 3.6+
- **Container**: Docker with docker-compose

### Planned AI Service
- **Framework**: FastAPI (Python + LangChain)
- **Integration**: REST API calls from backend service

## Architecture

### High-Level System Architecture
```
┌─────────────────────────────────────────────────────────┐
│  前端层：微信小程序 / H5 / App (uni-app)                 │
│  • Vue 3 Composition API + Pinia                        │
│  • Platform-specific UI (uView Plus / MD3)              │
│  • API calls to backend via axios wrapper               │
└─────────────────────────────────────────────────────────┘
                            │ HTTP/HTTPS + WebSocket
                            ▼
┌─────────────────────────────────────────────────────────┐
│  网关层：Nginx + WebSocket Proxy (production)           │
│  • Request routing & load balancing                     │
│  • WebSocket connections for real-time features         │
└─────────────────────────────────────────────────────────┘
                            │
                            ▼
┌─────────────────────────────────────────────────────────┐
│  服务层：Spring Boot REST API                           │
│  • Controllers → Services → Repositories pattern        │
│  • JWT authentication & Spring Security                 │
│  • MySQL (JPA) + Redis (caching/session)                │
│  • OpenAPI documentation at /api/swagger-ui.html        │
└─────────────────────────────────────────────────────────┘
                            │
                            ▼
┌─────────────────────────────────────────────────────────┐
│  数据层：MySQL 8.0 / Redis 7.0 / 腾讯云 COS              │
│  • MySQL: User data, stories, progress, etc.            │
│  • Redis: Session cache, rate limiting                  │
│  • COS: Media storage (audio, images)                   │
└─────────────────────────────────────────────────────────┘
```

### Frontend Architecture
- **Component Structure**: Custom MD3 components (`components/md3-*/`) + business components
- **State Management**: Pinia stores (`store/`) for user, story, and audio state
- **Routing**: uni-app pages auto-registered via `pages.json`
- **Styling**: SCSS with Material 3 design tokens (`styles/md3-theme.scss`)
- **API Layer**: Axios wrapper (`utils/request.js`) with JWT interception
- **Platform Adaptation**: Conditional compilation (`#ifdef MP-WEIXIN`) for platform-specific code

### Backend Architecture
- **Layered Architecture**: Controllers → Services → Repositories → Entities
- **Package Structure**: `com.storyforest.{config,controller,dto,entity,exception,repository,security,service,util}`
- **Security**: Spring Security with JWT authentication filter
- **Data Access**: Spring Data JPA repositories with MySQL
- **Caching**: Redis integration via Spring Data Redis
- **API Design**: RESTful endpoints following OpenAPI 3.0 specification

## Project Structure

```
app/
├── frontend/                    # uni-app frontend
│   ├── src/
│   │   ├── pages/              # 18 pages (auto-registered via pages.json)
│   │   ├── components/         # Reusable components
│   │   │   ├── md3-button/     # Material 3 button
│   │   │   ├── md3-input/      # Material 3 input
│   │   │   ├── md3-card/       # Material 3 card
│   │   │   ├── StoryCard.vue   # Story card component
│   │   │   ├── ChapterItem.vue # Chapter item component
│   │   │   └── AudioPlayer.vue # Audio player component
│   │   ├── styles/             # Global styles
│   │   │   ├── md3-theme.scss  # Material 3 design tokens
│   │   │   └── md3-global.scss # Material 3 global styles
│   │   ├── store/              # Pinia stores
│   │   │   ├── user.js         # User authentication state
│   │   │   ├── story.js        # Story data and favorites
│   │   │   └── audio.js        # Audio playback state
│   │   ├── api/                # API service modules
│   │   │   └── auth.js         # Authentication API
│   │   ├── utils/              # Utilities
│   │   │   ├── request.js      # HTTP request wrapper (axios)
│   │   │   └── storage.js      # Local storage utilities
│   │   ├── static/             # Static assets
│   │   ├── App.vue             # Root component
│   │   ├── main.js             # Application entry
│   │   ├── pages.json          # Page routing configuration
│   │   ├── manifest.json       # App configuration
│   │   └── uni.scss            # Global uni-app styles
│   ├── vite.config.js          # Vite configuration with uni-app plugin
│   ├── package.json            # npm dependencies and scripts
│   └── index.html              # HTML entry point
├── backend/                    # Spring Boot backend
│   ├── src/main/java/com/storyforest/
│   │   ├── config/             # Configuration classes
│   │   ├── controller/         # REST controllers
│   │   ├── dto/                # Data transfer objects
│   │   ├── entity/             # JPA entities
│   │   ├── exception/          # Custom exceptions
│   │   ├── repository/         # JPA repositories
│   │   ├── security/           # Security configuration
│   │   ├── service/            # Business logic services
│   │   └── util/               # Utility classes
│   ├── src/main/resources/
│   │   ├── application.yml     # Spring configuration
│   │   └── db/init.sql         # Database initialization
│   ├── src/test/java/          # Unit tests (JUnit 5)
│   ├── pom.xml                 # Maven configuration
│   ├── Dockerfile              # Docker image definition
│   ├── docker-compose.yml      # Local development with MySQL+Redis
│   └── openapi.yaml            # OpenAPI specification
└── docs/                       # Product documentation
    ├── 原型设计.md             # Wireframes and interaction specs
    ├── 用户流程.md             # User journey and state machine
    └── UI 组件库规范.md        # UI component library specs
```

## Common Development Tasks

### Frontend Tasks
```bash
# Install/update dependencies
cd frontend && npm install

# Start H5 development server (http://localhost:5173)
npm run dev:h5

# Start WeChat Mini Program dev (requires微信开发者工具)
npm run dev:mp-weixin

# Build for production
npm run build:h5           # H5 build in dist/build/h5
npm run build:mp-weixin    # Mini program build in dist/build/mp-weixin

# Check package.json for other platform targets (app, etc.)
```

### Backend Tasks
```bash
# Start with Docker Compose (MySQL + Redis + App)
docker-compose up -d
# Access: http://localhost:8080/api/swagger-ui.html

# Start locally (requires MySQL & Redis running)
mvn spring-boot:run

# Run all tests
mvn test

# Run specific test class
mvn test -Dtest=AuthServiceTest

# Build executable JAR
mvn clean package
# Output: target/story-forest-backend-1.0.0-SNAPSHOT.jar

# Run JAR
java -jar target/story-forest-backend-1.0.0-SNAPSHOT.jar

# Build Docker image
docker build -t story-forest-backend .
```

### Database Management
```bash
# Initialize database (from backend directory)
mysql -u root -p < src/main/resources/db/init.sql

# Start Redis locally
redis-server
```

## Key Configuration Files

### Frontend Configuration
- `frontend/vite.config.js` - Vite + uni-app configuration with SCSS preprocessing
- `frontend/src/pages.json` - Page routing and uView Plus auto-import configuration
- `frontend/src/manifest.json` - App metadata and platform settings
- `frontend/src/styles/md3-theme.scss` - Material 3 design tokens (colors, typography)

### Backend Configuration
- `backend/src/main/resources/application.yml` - Spring Boot configuration (DB, Redis, JWT)
- `backend/pom.xml` - Maven dependencies and build configuration
- `backend/docker-compose.yml` - Local development environment (MySQL 8.0 + Redis 7.0)
- `backend/openapi.yaml` - API specification (OpenAPI 3.0)

## API Integration

### Frontend API Layer
- Base URL: Configured in `frontend/src/utils/request.js` (currently `https://api.example.com`)
- Authentication: JWT Bearer token automatically added to requests
- Response format: `{ "code": 0, "message": "success", "data": {} }`
- Error handling: Global interceptor in request.js

### Backend API Endpoints
- Swagger UI: http://localhost:8080/api/swagger-ui.html (when running)
- API Base Path: `/api` (configured in Spring Boot)
- Authentication: JWT via `Authorization: Bearer <token>` header
- Current modules: Auth, User, Story, Quiz, Dubbing, Parent controls

## Design System

### Material Design 3 Implementation
- **Design Tokens**: Defined in `frontend/src/styles/md3-theme.scss`
- **Color Palette**: Primary (#2D6A4F), Secondary (#FF8C42), Tertiary (#4ECDC4), Surface (#FFFBF0)
- **Components**: Custom MD3 Button, Input, Card with variants
- **Usage**: Import `md3-global.scss` in main.js for global styles

### Component Variants
- **Md3Button**: `variant` (filled|outlined|text|contained), `size` (small|medium|large)
- **Md3Input**: `variant` (outlined|filled), `label`, `error`, `counter`, `clearable`
- **Md3Card**: `variant` (outlined|elevated|filled|tonal), `clickable`, `media` slot, `actions` slot

## Development Notes

### uni-app Specific Considerations
- Pages are auto-registered via `pages.json` (no manual import needed)
- Use `uni.` prefix for platform APIs (e.g., `uni.navigateTo`, `uni.request`)
- Platform-specific code: `#ifdef MP-WEIXIN` / `#ifndef MP-WEIXIN`
- uView Plus components auto-imported via `pages.json` configuration
- H5 build uses custom MD3 components; mini-program uses uView Plus

### Code Style Guidelines
- Vue 3 Composition API preferred over Options API
- SCSS with Material 3 design tokens for styling
- Component names: PascalCase (e.g., `StoryCard.vue`)
- File naming: kebab-case for non-components
- Alias `@` resolves to `frontend/src` (configured in vite.config.js)

### State Management (Pinia)
- **useUserStore**: Authentication state, user info, token, age group
- **useStoryStore**: Current story, favorites, reading history
- **useAudioStore**: Playback state, current time, progress, timer

## Testing Strategy

### Backend Testing
- **Framework**: JUnit 5 + Spring Boot Test
- **Test Location**: `backend/src/test/java/`
- **Current Tests**: `JwtUtilTest`, `AuthServiceTest`
- **Run Tests**: `mvn test` or `mvn test -Dtest=ClassName`

### Frontend Testing
- Testing framework not yet configured (planned)
- Manual testing via development builds recommended

## Current Status & Next Steps

### Completed (v0.8)
- ✅ Frontend: 18 pages with complete UI
- ✅ Frontend: Pinia state management (3 stores)
- ✅ Frontend: Material Design 3 component library
- ✅ Frontend: Audio playback functionality
- ✅ Frontend: Login/Register flow
- ✅ Backend: Spring Boot REST API structure
- ✅ Backend: JWT authentication implementation
- ✅ Backend: MySQL + Redis integration
- ✅ Backend: Docker Compose development environment
- ✅ Backend: OpenAPI documentation

### Next Steps
- [ ] Backend: Complete remaining service implementations
- [ ] Integration: Update frontend `BASE_URL` to point to local backend
- [ ] Integration: Connect frontend API calls to backend endpoints
- [ ] WeChat: Implement WeChat login integration
- [ ] Media: Integrate Lottie animations for story reading
- [ ] Testing: Add comprehensive unit/integration tests
- [ ] Deployment: Production deployment configuration

---

## 产品文档

### 产品定位
**故事森林 (Story Forest)** - 面向 3-10 岁儿童的互动式故事阅读平台

| 用户类型 | 年龄段 | 核心需求 |
|---------|--------|----------|
| 儿童用户 | 3-6 岁 | 听故事、看动画、简单互动 |
| 儿童用户 | 7-10 岁 | 自主阅读、角色扮演、挑战答题 |
| 家长用户 | 25-40 岁 | 控制时长、内容筛选、成长记录 |

### 核心功能模块

#### 1. 故事库模块
- **分类体系**: 按年龄 (3-4/5-6/7-8/9-10 岁)、主题 (童话/科普/寓言/历史/成语/英文)、时长 (短篇/中篇/长篇)
- **每日推荐**: 基于阅读习惯的个性化推荐
- **音频 + 图文**: TTS 语音合成 + Lottie 动画插画

#### 2. 互动模块
- **问答互动**: 选择题/判断题/排序题，答对解锁下一章
- **角色扮演**: 录音配音功能，AI 变声处理

#### 3. 家长控制模块
- **时长管理**: 每日/单次时长限制，禁用时段设置
- **内容过滤**: 敏感词库，黑名单功能
- **成长报告**: 周报/月报阅读数据统计

#### 4. 成长体系
- **积分系统**: 阅读 +1/分钟，答题 +10/题，连续打卡 +50/天
- **勋章系统**: 阅读类/答题类/配音类/打卡类勋章
- **积分商城**: 兑换角色皮肤、专属勋章、实体奖品

### 用户流程

#### 新用户首次使用
```
启动页 → 授权登录 → 年龄选择 → 首页
```

#### 阅读故事流程
```
浏览故事 → 查看详情 → 选择章节 → 听故事/看插画 → 完成阅读 → 解锁成就
```

#### 互动答题流程
```
阅读到互动节点 → 弹出答题卡 → 选择答案 → 提交 → 答对 (+积分解锁) / 答错 (看解析重试)
```

#### 家长设置流程
```
个人中心 → 家长验证 (数学题) → 时长管理/内容过滤/成长报告
```

### 状态流转

#### 用户状态机
```
游客 → 待完善信息 → 正常用户 ↔ 受限用户 (时长超限)
```

#### 章节解锁状态
```
已锁定 → 答题正确 → 可阅读 → 阅读中 → 已完成
```

---

## UI 组件库规范

### 色彩系统

```scss
// 主色调
$primary-500: #2D6A4F;      // 森林绿
$secondary-500: #FF8C42;    // 暖橙色
$accent-500: #4ECDC4;       // 天空蓝

// 背景色
$bg-primary: #FFFBF0;       // 奶油白
$bg-secondary: #FFFFFF;     // 纯白
$bg-tertiary: #F0F7F4;      // 浅绿

// 文字色
$text-primary: #1B4332;
$text-secondary: #52796F;
$text-hint: #94B49F;

// 状态色
$success: #4CAF50;
$warning: #FFC107;
$error: #F44336;
```

### 字体系统

| 场景 | 大小 | 字重 | 行高 |
|------|------|------|------|
| 页面标题 | 24px | 600 | 1.25 |
| 卡片标题 | 18px | 600 | 1.4 |
| 正文 | 16px | 400 | 1.6 |
| 辅助文字 | 14px | 400 | 1.5 |

### 间距系统

```scss
$spacing-xs: 4px;
$spacing-sm: 8px;
$spacing-base: 12px;
$spacing-md: 16px;
$spacing-lg: 24px;
$spacing-xl: 32px;
```

### 圆角系统

```scss
$radius-sm: 4px;      // 小按钮/标签
$radius-base: 8px;    // 卡片/输入框
$radius-md: 12px;     // 大卡片
$radius-lg: 16px;     // 弹窗
$radius-full: 9999px; // 圆形/胶囊
```

### 业务组件

| 组件名 | 说明 | 使用场景 |
|--------|------|----------|
| StoryCard | 故事卡片 | 首页/分类页 |
| ChapterItem | 章节列表项 | 故事详情页 |
| AudioPlayer | 音频播放器 | 阅读页底部 |
| QuizOption | 答题选项卡 | 答题页 |
| BadgeIcon | 勋章图标 | 个人中心 |
| ParentVerify | 家长验证弹窗 | 家长中心 |

### 动画规范

| 动画类型 | 时长 | 缓动函数 |
|---------|------|---------|
| 页面切换 | 300ms | ease-out |
| 卡片点击 | 150ms | ease-in-out |
| 收藏爱心 | 400ms | spring |
| 答题正确 | 600ms | ease-out |
| 答题错误 | 300ms | ease-in-out |

---

## 页面列表 (18 个)

| 页面 | 路径 | 状态 |
|------|------|------|
| 启动页 | `/pages/splash/splash` | ✅ |
| 登录页 | `/pages/login/login` | ✅ |
| 注册页 | `/pages/register/register` | ✅ |
| 年龄选择页 | `/pages/age-select/age-select` | ✅ |
| 首页 | `/pages/index/index` | ✅ |
| 分类页 | `/pages/category/category` | ✅ |
| 故事详情页 | `/pages/story-detail/story-detail` | ✅ |
| 阅读页 | `/pages/reading/reading` | ✅ |
| 答题页 | `/pages/quiz/quiz` | ✅ |
| 配音页 | `/pages/dubbing/dubbing` | ✅ |
| 用户中心页 | `/pages/user/user` | ✅ |
| 家长中心页 | `/pages/parent/parent` | ✅ |
| 积分商城页 | `/pages/points-mall/points-mall` | ✅ |
| 我的成就页 | `/pages/achievement/achievement` | ✅ |
| 设置页 | `/pages/settings/settings` | ✅ |
| 用户协议页 | `/pages/user-agreement/user-agreement` | ✅ |
| 隐私政策页 | `/pages/privacy-policy/privacy-policy` | ✅ |

---

## API 接口设计

### 核心接口

| 模块 | 方法 | 路径 | 说明 |
|------|------|------|------|
| 用户 | POST | /api/user/login | 微信登录 |
| 用户 | GET | /api/user/profile | 获取用户信息 |
| 用户 | POST | /api/user/age-group | 设置年龄段 |
| 故事 | GET | /api/stories | 故事列表 |
| 故事 | GET | /api/stories/:id | 故事详情 |
| 故事 | GET | /api/stories/:id/chapters | 章节列表 |
| 阅读 | GET/POST | /api/reading/record | 获取/上报阅读进度 |
| 答题 | GET | /api/quiz/questions | 获取题目 |
| 答题 | POST | /api/quiz/submit | 提交答案 |
| 配音 | POST | /api/dubbing/upload | 上传配音 |
| 积分 | GET | /api/points | 获取积分 |
| 家长 | GET/POST | /api/parent/settings | 获取/保存设置 |

### 响应格式

```json
{
  "code": 0,
  "message": "success",
  "data": {},
  "timestamp": 1709567890
}
```

---

## 数据库设计

### 核心表

- `users` - 用户表
- `stories` - 故事表
- `story_chapters` - 故事章节表
- `quiz_questions` - 问答题表
- `user_reading_records` - 阅读记录表
- `user_quiz_records` - 答题记录表
- `user_dubbing_records` - 配音记录表
- `user_points` / `user_point_logs` - 积分表/流水表
- `user_badges` - 勋章表
- `parent_settings` - 家长设置表

---

## 开发计划

### 版本规划

| 版本 | 内容 | 周期 |
|------|------|------|
| v0.1 | 技术选型 + 架构设计 | 1 周 |
| v0.5 | 核心功能开发 (故事库 + 阅读) | 3 周 |
| v0.8 | 互动功能 (答题 + 配音) | 2 周 |
| v1.0 | 家长控制 + 成长体系 | 2 周 |
| v1.1 | 测试 + 优化 + 上线 | 2 周 |

### 当前状态 (v0.8)

- ✅ 前端页面开发：100% (17 个页面)
- ✅ 前端组件开发：核心组件已完成
- ✅ 状态管理：Pinia 已集成
- ✅ 音频播放：基础功能已完成
- ⏳ 后端 API 开发：待开始
- ⏳ API 对接：待开始

### 下一步计划

1. **高优先级 (P0)**: 后端 API 服务开发、API 接口对接、数据库设计、微信登录对接
2. **中优先级 (P1)**: Lottie 动画集成、背景音效功能、消息通知系统
3. **低优先级 (P2/P3)**: 夜间模式完善、AR 扫描功能、家长社区 UGC