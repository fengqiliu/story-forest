# 故事森林 (Story Forest)

> 让孩子爱上阅读的魔法世界 🌲🦊📖

面向 3-10 岁儿童的互动式故事阅读平台，通过 AI 技术提供个性化阅读体验。

---

## 📱 项目简介

**故事森林** 是一款专为儿童设计的互动故事小程序，包含海量优质故事、AI 个性化推荐、互动答题和角色扮演配音功能，让孩子在快乐中培养阅读习惯。

### 核心功能

- 📚 **故事库** - 按年龄/主题分类，每日推荐
- 🎯 **互动答题** - 故事中嵌入问题，答对解锁下一章
- 🎤 **角色扮演** - 为孩子喜欢的角色配音
- 👨‍👩‍👧 **家长控制** - 时长管理、内容过滤、成长报告
- 🏆 **成长体系** - 积分、勋章、商城激励

---

## 🚀 快速开始

### 前端开发

```bash
cd frontend

# 安装依赖
npm install

# 开发模式 - 微信小程序
npm run dev:mp-weixin

# 开发模式 - H5 (支持 Material Design)
npm run dev:h5

# 构建生产版本 - 微信小程序
npm run build:mp-weixin

# 构建生产版本 - H5
npm run build:h5
```

### 后端开发

```bash
cd backend

# 使用 Docker Compose 启动 (MySQL + Redis + App)
docker-compose up -d

# 本地 Maven 开发
mvn spring-boot:run

# 运行测试
mvn test

# 构建 JAR
mvn clean package
```

---

## 📁 项目结构

```
app/
├── frontend/                    # uni-app 前端
│   ├── src/
│   │   ├── pages/              # 18 个页面
│   │   ├── components/         # 可复用组件
│   │   ├── store/              # Pinia 状态管理
│   │   ├── api/                # API 服务模块
│   │   ├── utils/              # 工具函数
│   │   └── styles/             # 全局样式
│   ├── vite.config.js
│   └── package.json
├── backend/                     # Spring Boot 后端
│   ├── src/main/java/
│   │   ├── config/             # 配置类
│   │   ├── controller/         # REST 控制器
│   │   ├── dto/                # 数据传输对象
│   │   ├── entity/             # JPA 实体
│   │   ├── repository/         # 数据访问层
│   │   ├── security/           # 安全配置
│   │   └── service/            # 业务逻辑层
│   ├── src/main/resources/
│   │   ├── application.yml
│   │   └── db/init.sql
│   ├── pom.xml
│   └── docker-compose.yml
├── docs/                        # 产品文档
│   ├── 产品 PRD.md              # 完整产品需求文档
│   ├── 原型设计.md              # 11 个页面线框图
│   ├── 用户流程.md              # 用户旅程、状态机
│   ├── UI 组件库规范.md         # 设计系统规范
│   ├── 小程序产品要求.md        # 产品要求摘要
│   ├── 登录注册功能说明.md      # 登录注册文档
│   ├── 项目开发进度报告.md      # 开发进度跟踪
│   └── 演示页面完成报告.md      # 演示页面报告
├── CLAUDE.md                    # 开发指南
└── README.md                    # 项目说明
```

---

## 🎯 目标用户

| 用户群 | 年龄段 | 核心需求 |
|--------|--------|----------|
| 儿童 | 3-6 岁 | 听故事、看动画、简单互动 |
| 儿童 | 7-10 岁 | 自主阅读、角色扮演、挑战答题 |
| 家长 | 25-40 岁 | 控制时长、内容筛选、成长记录 |

---

## 🛠 技术栈

### 前端
- **框架**: uni-app 3.x (Vue 3 Composition API)
- **UI 库**: uView Plus (小程序) / 自定义 Material Design 3 (H5)
- **状态管理**: Pinia 2.x
- **构建工具**: Vite 4.5
- **动画**: Lottie
- **音频**: uni.createInnerAudioContext

### 后端
- **JDK**: JDK 17
- **框架**: Spring Boot 3.2.0
- **数据库**: MySQL 8.0 (JPA) / Redis 7.0
- **认证**: JWT + Spring Security
- **API 文档**: SpringDoc OpenAPI 3.0 (Swagger)
- **构建**: Maven

---

## 📊 产品文档

| 文档 | 说明 |
|------|------|
| [产品 PRD](./docs/产品 PRD.md) | 完整产品需求文档，含功能设计、数据库设计、API 接口 |
| [原型设计](./docs/原型设计.md) | 11 个核心页面线框图和交互规范 |
| [用户流程](./docs/用户流程.md) | 用户旅程地图、状态流转、数据流、权限控制 |
| [UI 组件库规范](./docs/UI 组件库规范.md) | 基于 uView Plus 的 Material Design 3 定制规范 |
| [登录注册说明](./docs/登录注册功能说明.md) | 登录注册功能详细文档 |
| [开发进度报告](./docs/项目开发进度报告.md) | 项目进度跟踪和待办清单 |

---

## 📈 开发计划

| 版本 | 内容 | 周期 |
|------|------|------|
| v0.1 | 技术选型 + 架构设计 | 1 周 |
| v0.5 | 核心功能 (故事库 + 阅读) | 3 周 |
| v0.8 | 互动功能 (答题 + 配音) | 2 周 |
| v1.0 | 家长控制 + 成长体系 | 2 周 |
| v1.1 | 测试 + 优化 + 上线 | 2 周 |

---

## ✅ 当前状态 (v0.8)

### 已完成
- ✅ 前端页面：18 个页面 100% 完成
- ✅ 前端组件：StoryCard、ChapterItem、AudioPlayer
- ✅ 状态管理：Pinia (user/story/audio 三个 Store)
- ✅ 音频播放：播放控制、倍速、定时关闭
- ✅ 登录注册：微信登录、手机号登录、年龄选择

### 待完成
- ⏳ 后端 API 服务开发
- ⏳ API 接口联调对接
- ⏳ Lottie 动画集成
- ⏳ 微信登录对接

---

## 📐 设计规范

### 色彩系统
```
主色调：森林绿 #2D6A4F
强调色：暖橙色 #FF8C42
辅助色：天空蓝 #4ECDC4
背景色：奶油白 #FFFBF0
```

### 字体系统
| 场景 | 大小 | 字重 |
|------|------|------|
| 页面标题 | 24px | 600 |
| 卡片标题 | 18px | 600 |
| 正文 | 16px | 400 |
| 辅助文字 | 14px | 400 |

---

## 📌 关键指标

| 指标 | 目标值 |
|------|--------|
| DAU | 10,000 |
| 次日留存 | 50% |
| 7 日留存 | 30% |
| 人均时长 | 20 分钟 |
| 完读率 | 60% |
| 付费转化率 | 5% |

---

## 🔗 相关链接

- [CLAUDE.md](./CLAUDE.md) - 开发指南和快速开始

---

## 📄 许可证

Copyright © 2026

---

## 📞 联系方式

项目答疑或建议，请通过 Issue 反馈。
