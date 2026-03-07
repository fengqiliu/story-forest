# QWEN.md - 故事森林项目指南

## 项目概述

**故事森林 (Story Forest)** 是一款面向 3-10 岁儿童的互动式故事阅读小程序，通过 AI 技术提供个性化阅读体验。

### 核心功能
- 📚 **故事库** - 按年龄/主题分类，每日推荐
- 🎯 **互动答题** - 故事中嵌入问题，答对解锁下一章
- 🎤 **角色扮演** - 为孩子喜欢的角色配音
- 👨‍👩‍👧 **家长控制** - 时长管理、内容过滤、成长报告
- 🏆 **成长体系** - 积分、勋章、商城激励

### 目标用户
| 用户群 | 年龄段 | 核心需求 |
|--------|--------|----------|
| 儿童 | 3-6 岁 | 听故事、看动画、简单互动 |
| 儿童 | 7-10 岁 | 自主阅读、角色扮演、挑战答题 |
| 家长 | 25-40 岁 | 控制时长、内容筛选、成长记录 |

---

## 技术栈

### 前端
| 技术 | 版本 | 用途 |
|------|------|------|
| **框架** | uni-app 3.x (Vue 3) | 跨平台开发 |
| **UI 库** | uView Plus 3.1.3 | 微信小程序组件 |
| **设计系统** | Material Design 3 | 纯 CSS 实现 |
| **状态管理** | Pinia 2.0.36 | 全局状态 |
| **构建工具** | Vite 2.9.15 | 快速构建 |
| **预处理器** | Sass 1.62.1 | 样式编写 |
| **目标平台** | 微信小程序 / H5 / App | 多端发布 |

### 后端
| 技术 | 版本 | 用途 |
|------|------|------|
| **JDK** | JDK 21 (LTS) | Java 运行时 |
| **框架** | Spring Boot 3.2.0 | Web 框架 |
| **安全** | Spring Security + JWT | 认证授权 |
| **ORM** | Spring Data JPA | 数据访问 |
| **数据库** | MySQL 8.0+ | 主数据库 |
| **缓存** | Redis 7.0+ | 缓存/验证码 |
| **API 文档** | OpenAPI 3.0 / Swagger | API 文档 |
| **构建工具** | Maven | 项目构建 |

---

## 项目结构

```
app/
├── frontend/src/                # uni-app 前端项目
│   ├── pages/                   # 18 个页面
│   │   ├── splash/              # 启动页
│   │   ├── login/               # 登录页
│   │   ├── register/            # 注册页
│   │   ├── age-select/          # 年龄选择页
│   │   ├── index/               # 首页
│   │   ├── category/            # 分类页
│   │   ├── story-detail/        # 故事详情页
│   │   ├── reading/             # 阅读页
│   │   ├── quiz/                # 答题页
│   │   ├── dubbing/             # 配音页
│   │   ├── user/                # 用户中心
│   │   ├── parent/              # 家长中心
│   │   ├── settings/            # 设置页
│   │   ├── points-mall/         # 积分商城页
│   │   ├── achievement/         # 成就页
│   │   ├── user-agreement/      # 用户协议
│   │   ├── privacy-policy/      # 隐私政策
│   │   └── material-demo/       # Material 演示
│   ├── components/              # 公共组件
│   │   ├── md3-button/          # Material 3 按钮
│   │   ├── md3-input/           # Material 3 输入框
│   │   ├── md3-card/            # Material 3 卡片
│   │   ├── StoryCard.vue        # 故事卡片
│   │   ├── ChapterItem.vue      # 章节项
│   │   └── AudioPlayer.vue      # 音频播放器
│   ├── styles/                  # 全局样式
│   │   ├── md3-theme.scss       # Material 3 设计令牌
│   │   ├── md3-global.scss      # Material 3 全局样式
│   │   └── variables.scss       # 品牌色彩变量
│   ├── store/                   # Pinia 状态管理
│   │   ├── index.js             # Pinia 入口
│   │   ├── user.js              # 用户状态
│   │   ├── story.js             # 故事数据
│   │   └── audio.js             # 音频播放
│   ├── api/                     # API 服务
│   │   └── auth.js              # 认证 API
│   ├── utils/                   # 工具函数
│   │   ├── request.js           # HTTP 请求封装
│   │   └── storage.js           # 本地存储
│   ├── App.vue                  # 应用入口
│   ├── main.js                  # 入口文件
│   ├── pages.json               # 页面配置
│   ├── manifest.json            # 应用配置
│   ├── package.json             # 依赖配置
│   └── vite.config.js           # Vite 配置
├── backend/                     # 后端项目 (待开发)
├── docs/                        # 产品文档
│   ├── 产品 PRD.md              # 产品需求文档
│   ├── 原型设计.md              # 页面线框图
│   ├── 用户流程.md              # 用户旅程地图
│   ├── UI 组件库规范.md         # 设计规范
│   ├── 登录注册功能说明.md
│   ├── 项目开发进度报告.md
│   └── 演示页面完成报告.md
├── CLAUDE.md                    # Claude 开发指南
├── QWEN.md                      # 本文件
└── README.md                    # 项目说明
```

---

## 快速开始

### 环境要求
- Node.js >= 14.x
- npm >= 6.x
- 微信开发者工具 (小程序开发)

### 安装依赖
```bash
cd frontend/src
npm install
```

### 开发模式
```bash
# 微信小程序
npm run dev:mp-weixin

# H5 (支持 Material Design)
npm run dev:h5

# App
npm run dev:app
```

### 构建生产版本
```bash
# 微信小程序
npm run build:mp-weixin

# H5
npm run build:h5

# App
npm run build:app
```

---

## 设计系统

### 色彩系统
```scss
// 主色调
$md-primary: #2D6A4F;           // 森林绿
$md-on-primary: #FFFFFF;
$md-primary-container: #A5D6A7;

// 强调色
$md-secondary: #FF8C42;         // 暖橙色
$md-on-secondary: #FFFFFF;
$md-secondary-container: #FFE0B2;

// 辅助色
$md-tertiary: #4ECDC4;          // 天空蓝
$md-on-tertiary: #FFFFFF;
$md-tertiary-container: #B2EBF2;

// 背景色
$md-surface: #FFFBF0;           // 奶油白
$md-on-surface: #1B4332;
```

### 圆角系统
```scss
$md-radius-xs: 4px;
$md-radius-sm: 8px;
$md-radius-md: 12px;
$md-radius-lg: 16px;
$md-radius-xl: 24px;
$md-radius-xxl: 28px;
$md-radius-full: 9999px;
```

### 阴影系统
```scss
$md-elevation-1: 0 1px 2px rgba(0, 0, 0, 0.05);
$md-elevation-2: 0 1px 3px 1px rgba(0, 0, 0, 0.05);
$md-elevation-3: 0 4px 8px 3px rgba(0, 0, 0, 0.05);
$md-elevation-4: 0 6px 12px 4px rgba(0, 0, 0, 0.05);
$md-elevation-5: 0 8px 16px 6px rgba(0, 0, 0, 0.05);
```

### 字体系统
```scss
$md-font-family: 'PingFang SC', 'Helvetica Neue', sans-serif;

// 显示字体
$md-display-large-size: 57px;
$md-display-medium-size: 45px;
$md-display-small-size: 36px;

// 标题字体
$md-headline-large-size: 32px;
$md-headline-medium-size: 28px;
$md-headline-small-size: 24px;

// 正文字体
$md-body-large-size: 16px;
$md-body-medium-size: 14px;
$md-body-small-size: 12px;
```

---

## MD3 组件库

### Md3Button 按钮组件

```vue
<template>
  <!-- 实心按钮 -->
  <md3-button variant="filled">确定</md3-button>

  <!-- 边框按钮 -->
  <md3-button variant="outlined">取消</md3-button>

  <!-- 文字按钮 -->
  <md3-button variant="text">查看更多</md3-button>

  <!-- 带图标 -->
  <md3-button variant="filled" :icon="true">
    <template #icon><svg>...</svg></template>
    添加
  </md3-button>

  <!-- 禁用/加载状态 -->
  <md3-button variant="filled" disabled>禁用</md3-button>
  <md3-button variant="filled" loading>加载中</md3-button>

  <!-- 尺寸 -->
  <md3-button variant="filled" size="small">小</md3-button>
  <md3-button variant="filled" size="large">大</md3-button>

  <!-- 全宽 -->
  <md3-button variant="filled" full-width>全宽按钮</md3-button>
</template>
```

**Props:**
| Prop | 类型 | 默认值 | 说明 |
|------|------|--------|------|
| `variant` | string | 'filled' | filled/outlined/text/contained |
| `size` | string | 'medium' | small/medium/large |
| `disabled` | boolean | false | 禁用状态 |
| `loading` | boolean | false | 加载状态 |
| `icon` | boolean | false | 前置图标 |
| `iconAfter` | boolean | false | 后置图标 |
| `fullWidth` | boolean | false | 全宽模式 |

### Md3Input 输入框组件

```vue
<template>
  <!-- 轮廓式 -->
  <md3-input v-model="value" label="用户名" variant="outlined" />

  <!-- 带图标 -->
  <md3-input v-model="search" label="搜索">
    <template #prefix-icon><svg>...</svg></template>
  </md3-input>

  <!-- 错误状态 -->
  <md3-input
    v-model="email"
    label="邮箱"
    :error="true"
    error-message="请输入有效的邮箱"
  />

  <!-- 字符计数 -->
  <md3-input v-model="message" label="留言" :counter="100" type="textarea" />

  <!-- 可清除 -->
  <md3-input v-model="value" :clearable="true" />
</template>
```

**Props:**
| Prop | 类型 | 默认值 | 说明 |
|------|------|--------|------|
| `modelValue` | string | - | v-model 绑定值 |
| `type` | string | 'text' | text/password/email/textarea |
| `label` | string | - | 标签文本 |
| `variant` | string | 'outlined' | outlined/filled |
| `size` | string | 'medium' | small/medium/large |
| `disabled` | boolean | false | 禁用 |
| `error` | boolean | false | 错误状态 |
| `errorMessage` | string | - | 错误提示 |
| `clearable` | boolean | false | 可清除 |

### Md3Card 卡片组件

```vue
<template>
  <!-- 轮廓式卡片 -->
  <md3-card variant="outlined" title="标题" subtitle="副标题">
    卡片内容...
    <template #actions>
      <md3-button variant="text">收藏</md3-button>
      <md3-button variant="filled">操作</md3-button>
    </template>
  </md3-card>

  <!-- 带媒体 -->
  <md3-card variant="outlined" media-aspect-ratio="wide">
    <template #media><image src="..." /></template>
    卡片内容...
  </md3-card>

  <!-- 可点击 -->
  <md3-card variant="elevated" clickable @click="handleClick">
    可点击卡片...
  </md3-card>
</template>
```

**Props:**
| Prop | 类型 | 默认值 | 说明 |
|------|------|--------|------|
| `variant` | string | 'outlined' | outlined/elevated/filled/tonal |
| `clickable` | boolean | false | 可点击 |
| `selectable` | boolean | false | 可选择 |
| `title` | string | - | 标题 |
| `subtitle` | string | - | 副标题 |
| `mediaAspectRatio` | string | - | square/wide/tall |

---

## 状态管理 (Pinia)

### useUserStore - 用户状态
```javascript
import { useUserStore } from '@/store/user'

const userStore = useUserStore()

// State
userStore.isLogin        // 是否已登录
userStore.userInfo       // 用户信息
userStore.token          // JWT Token
userStore.ageGroup       // 年龄段

// Getters
userStore.hasAgeGroup    // 是否已设置年龄段
userStore.isVip          // 是否 VIP
userStore.nickname       // 昵称
userStore.avatar         // 头像

// Actions
userStore.setUserInfo(userInfo, token)  // 设置用户信息
userStore.updateUserInfo(partialInfo)   // 更新用户信息
userStore.setAgeGroup(ageGroup)         // 设置年龄段
userStore.logout()                      // 退出登录
```

### useStoryStore - 故事状态
```javascript
import { useStoryStore } from '@/store/story'

const storyStore = useStoryStore()

// State
storyStore.currentStory       // 当前故事
storyStore.favorites          // 收藏列表
storyStore.readingHistory     // 阅读历史

// Actions
storyStore.toggleFavorite(id)       // 切换收藏
storyStore.addToRecentPlayed(story) // 添加到最近播放
```

### useAudioStore - 音频状态
```javascript
import { useAudioStore } from '@/store/audio'

const audioStore = useAudioStore()

// State
audioStore.isPlaying        // 是否播放中
audioStore.currentTime      // 当前时间
audioStore.progress         // 进度
audioStore.timerMinutes     // 定时关闭分钟数

// Actions
audioStore.play()           // 播放
audioStore.pause()          // 暂停
audioStore.seek(time)       // 跳转
audioStore.setTimer(min)    // 设置定时
```

---

## 页面路由

| 页面 | 路径 | 功能 |
|------|------|------|
| 启动页 | `/pages/splash/splash` | 品牌展示 + 自动登录 |
| 登录页 | `/pages/login/login` | 微信一键登录 |
| 注册页 | `/pages/register/register` | 手机号 + 验证码注册 |
| 年龄选择 | `/pages/age-select/age-select` | 首次登录选择年龄段 |
| 首页 | `/pages/index/index` | 今日精选、轮播卡片 |
| 分类页 | `/pages/category/category` | 年龄/主题/时长筛选 |
| 故事详情 | `/pages/story-detail/story-detail` | 封面、章节列表 |
| 阅读页 | `/pages/reading/reading` | Lottie 动画、音频播放 |
| 答题页 | `/pages/quiz/quiz` | 关卡进度、生命值 |
| 配音页 | `/pages/dubbing/dubbing` | 角色选择、录音波形 |
| 用户中心 | `/pages/user/user` | 阅读记录、勋章墙 |
| 家长中心 | `/pages/parent/parent` | 数学验证、时长管理 |
| 设置页 | `/pages/settings/settings` | 账号管理、通知设置 |
| 积分商城 | `/pages/points-mall/points-mall` | 积分余额、商品列表 |
| 成就页 | `/pages/achievement/achievement` | 勋章墙、成就进度 |
| 用户协议 | `/pages/user-agreement/user-agreement` | 用户协议内容 |
| 隐私政策 | `/pages/privacy-policy/privacy-policy` | 隐私政策内容 |
| Material 演示 | `/pages/material-demo/material-demo` | MD3 组件演示 |

---

## 开发规范

### 代码风格
- **Vue 3 Composition API** 优先
- **组件命名**: PascalCase (如 `Md3Button`)
- **文件命名**: kebab-case (如 `md3-button.vue`)
- **样式**: SCSS + Material 3 design tokens

### 平台条件编译
```javascript
// #ifdef MP-WEIXIN
// 微信小程序专属代码
// #endif

// #ifndef MP-WEIXIN
// 非微信小程序代码
// #endif
```

### uni-app API 使用
```javascript
// 使用 uni. 前缀调用平台 API
uni.getStorageSync('token')
uni.setStorageSync('userInfo', data)
uni.navigateTo({ url: '/pages/detail/detail' })
uni.showToast({ title: '成功', icon: 'success' })
```

---

## API 设计（规划）

### 基础配置
```javascript
// Base URL
const BASE_URL = 'https://api.example.com'

// 响应格式
{
  code: 0,              // 0 表示成功
  message: "success",   // 响应消息
  data: {}              // 响应数据
}

// 认证方式
Authorization: Bearer <JWT_TOKEN>
```

### 核心接口（规划）
| 接口 | 方法 | 说明 |
|------|------|------|
| `/api/auth/login` | POST | 登录 |
| `/api/auth/register` | POST | 注册 |
| `/api/stories` | GET | 故事列表 |
| `/api/stories/:id` | GET | 故事详情 |
| `/api/stories/:id/chapters` | GET | 章节列表 |
| `/api/quiz/submit` | POST | 提交答案 |
| `/api/user/profile` | GET | 用户信息 |
| `/api/user/favorites` | GET/POST | 收藏管理 |

---

## 开发计划

| 阶段 | 内容 | 周期 |
|------|------|------|
| v0.1 | 技术选型 + 架构设计 | W1 |
| v0.5 | 核心功能 (故事库 + 阅读) | W2-4 |
| v0.8 | 互动功能 (答题 + 配音) | W5-6 |
| v1.0 | 家长控制 + 成长体系 | W7-8 |
| v1.1 | 测试 + 优化 + 上线 | W9-10 |

---

## 当前状态

### 已完成 (v0.8)
- ✅ Pinia 状态管理 (3 stores)
- ✅ 18 个页面 UI 完成
- ✅ 3 个自定义组件 (StoryCard, ChapterItem, AudioPlayer)
- ✅ Material Design 3 设计系统
- ✅ MD3 组件库 (Button, Input, Card)
- ✅ 音频播放功能
- ✅ 登录/注册系统

### 待开发
- [ ] 后端 API 开发 (Spring Boot + JDK 21)
- [ ] 数据库实现 (MySQL 8.0)
- [ ] API 对接
- [ ] 微信登录集成
- [ ] Lottie 动画集成

---

## 文档索引

| 文档 | 路径 | 说明 |
|------|------|------|
| README | `README.md` | 项目概述 |
| PRD | `docs/产品 PRD.md` | 产品需求文档 |
| 原型设计 | `docs/原型设计.md` | 11 个页面线框图 |
| 用户流程 | `docs/用户流程.md` | 用户旅程、状态机 |
| UI 规范 | `docs/UI 组件库规范.md` | uView Plus 定制规范 |
| CLAUDE | `CLAUDE.md` | Claude 开发指南 |

---

## 关键指标

| 指标 | 目标值 |
|------|--------|
| DAU | 10,000 |
| 次日留存 | 50% |
| 7 日留存 | 30% |
| 人均时长 | 20 分钟 |
| 完读率 | 60% |
| 付费转化率 | 5% |
